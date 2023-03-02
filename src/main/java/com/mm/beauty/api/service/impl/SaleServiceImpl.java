package com.mm.beauty.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liqpay.LiqPay;
import com.mm.beauty.api.entity.Courses;
import com.mm.beauty.api.entity.Sales;
import com.mm.beauty.api.entity.User;
import com.mm.beauty.api.entity.enums.OStatus;
import com.mm.beauty.api.repository.CoursesRepository;
import com.mm.beauty.api.repository.SaleRepository;
import com.mm.beauty.api.repository.UserRepository;
import com.mm.beauty.api.service.SaleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SaleServiceImpl implements SaleService {


    @Value("${key.public}")
    private String PUBLIC_KEY;

    @Value("${key.private}")
    private String PRIVATE_KEY;

    @Value("${api.version}")
    private int apiVersion;


    private final CoursesRepository coursesRepository;
    private final UserRepository userRepository;
    private final SaleRepository saleRepository;

    public SaleServiceImpl(CoursesRepository coursesRepository, UserRepository userRepository, SaleRepository saleRepository) {
        this.coursesRepository = coursesRepository;
        this.userRepository = userRepository;
        this.saleRepository = saleRepository;
    }

    @Override
    public String newOrder(Long courseId, Principal principal) throws JsonProcessingException {
        User user = userRepository.findUserByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("Такого користувача не зареэстровано."));
        Courses course = coursesRepository.findCoursesById(courseId);

        HashMap<String, String> outSignObject = new HashMap<>();


        if (saleRepository.findByUserAndCourseAndStatusLike(user,course, OStatus.ORDER_WAIT) != null) {
            outSignObject.put("signature", "COURSE_WAIT");
            return new ObjectMapper().writeValueAsString(outSignObject);
        }

        if (saleRepository.findByUserAndCourseAndStatusLike(user,course, OStatus.ORDER_COMPLETED) != null) {
            outSignObject.put("signature", "ORDER_COMPLETED");
            return new ObjectMapper().writeValueAsString(outSignObject);
        }

        UUID uuid = UUID.randomUUID();
        String orderId = uuid.toString();
        String orderAmount = String.valueOf(course.getPrice()*course.getDiscount());
        String orderDescript = "Оплата курсу " + course.getUniqueCode() + " - " + course.getName() + ". ";
        orderDescript += "Платник - " + user.getFullName();

        System.out.println(orderDescript);


        HashMap<String, String> params = new HashMap<>();
        params.put("action", "pay");
        params.put("amount", orderAmount);
        params.put("currency", "UAH");
        params.put("language", "uk");
        params.put("order_id", orderId);
        params.put("description", orderDescript);
        params.put("version", String.valueOf(apiVersion));
        params.put("public_key", PUBLIC_KEY);

        String json = new ObjectMapper().writeValueAsString(params);
        String jsonBase64Encode = Base64.getEncoder().encodeToString(json.getBytes());
        String sign_data = PRIVATE_KEY + jsonBase64Encode + PRIVATE_KEY;
        String signature = base64_encode(toSHA1(sign_data));
        outSignObject.put("data", jsonBase64Encode);
        outSignObject.put("signature", signature);

        Sales order = new Sales();
        order.setUser(user);
        order.setCourse(course);
        order.setOrderAmount(Double.parseDouble(orderAmount));
        order.setOrderId(orderId);
        saleRepository.save(order);
        return new ObjectMapper().writeValueAsString(outSignObject);
    }

    @Override
    @Scheduled(cron = "*/5 * * * * *")
    public void checkOrders() throws Exception {
        LiqPay liqPay = new LiqPay(PUBLIC_KEY, PRIVATE_KEY);
        Collection<Sales> listWaitingOrder = saleRepository.findByStatus(OStatus.ORDER_WAIT);
        HashMap<String, String> params = new HashMap<>();
        HashMap<String, Object> resultRequest = new HashMap<>();
        params.put("action", "status");
        params.put("version", String.valueOf(apiVersion));
        LocalDateTime payDate = LocalDateTime.now();
        for (Sales order : listWaitingOrder) {
            params.put("order_id", order.getOrderId());
            resultRequest = (HashMap<String, Object>) liqPay.api("request", params);
            if (resultRequest.get("status").equals("success")) {
                order.setStatus(OStatus.ORDER_COMPLETED);
                payDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(resultRequest.get("end_date").toString())),
                        TimeZone.getDefault().toZoneId());
                order.setDatePayment(payDate);
                Courses course = coursesRepository.findCoursesById(order.getCourse().getId());

                order.setExpireDate(payDate.plusDays(course.getDayAccess()));
                order.setPayCheck(true);
                order.setCheckCode(resultRequest.get("payment_id").toString());
                saleRepository.save(order);
            }
        }

    }

    @Override
    public List<Sales> getAllOrders() {
        return saleRepository.findAll();
    }

    @Override
    public List<Sales> getAllOrdersPerUser(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User" + username + " not found"));
        return saleRepository.findAllByUser(user);
    }


    public static byte[] toSHA1(String hexstr) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update(hexstr.getBytes("utf-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return md.digest();
    }

    public static String base64_encode(byte[] bytes) {
        return DatatypeConverter.printBase64Binary(bytes);
    }

    public static String base64_encode(String str) {
        return base64_encode(str.getBytes());
    }

}
