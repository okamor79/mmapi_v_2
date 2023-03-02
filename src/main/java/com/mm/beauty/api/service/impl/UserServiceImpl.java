package com.mm.beauty.api.service.impl;

import com.mm.beauty.api.dto.UserDTO;
import com.mm.beauty.api.entity.EmailModel;
import com.mm.beauty.api.entity.User;
import com.mm.beauty.api.entity.enums.URoles;
import com.mm.beauty.api.exceptions.UserExistException;
import com.mm.beauty.api.payload.request.SignupRequest;
import com.mm.beauty.api.repository.UserRepository;
import com.mm.beauty.api.service.EmailService;
import com.mm.beauty.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final EmailService emailService;

    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${password.length}")
    private int passwordLength;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EmailService emailService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignupRequest userIn) {
        User user = new User();
        user.setUsername(userIn.getUsername());
        user.setFullName(userIn.getFullName());
        user.setPhone(userIn.getPhone());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(URoles.ROLE_USER);
//        user.getUserStatus().add(UStatus.USER_ENABLE);
        try {
            LOG.info("User created {}", userIn.getUsername());
            return userRepository.save(user);
        } catch (Exception e) {
            LOG.error("Error user registration {}", e.getMessage());
            throw new UserExistException("User already exist");
        }
    }

    @Override
    public User updateUser(UserDTO userDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        user.setPhone(userDTO.getPhone());
        user.setFullName(userDTO.getFullName());
        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User" + username + " not found"));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findUserById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public Boolean resetUserPassword(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Такого користувача не зареэстровано."));
        String newPassword = passwordGenetator(passwordLength);
//        String encodePassword = Base64.getEncoder().encodeToString(newPassword.getBytes());
        user.setPassword(passwordEncoder.encode(newPassword));
//        user.setLastEdit(new Date());
        userRepository.save(user);
        EmailModel mail = new EmailModel();
        mail.setRecipient(user.getUsername());
        mail.setSubject("Ваш оновлений пароль M&M Beauty LAB");
        String mailText = "Доброго дня. \n\nВам було згенерований новий пароль для входу у свій обліковий запис. \nПрохання, після успішного входу змінити його у своєму профілі\n\nВаш логін:  "
                + user.getUsername() + "\nВаш новий пароль:  "
                + newPassword + "\n\nГарного Вам дня.";
        mail.setMsgBody(mailText);
        emailService.sendSimpleMail(mail);
        return true;

    }


    public static String passwordGenetator(int length) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int rndIndex = secureRandom.nextInt(chars.length());
            stringBuilder.append(chars.charAt(rndIndex));
        }
        return stringBuilder.toString();
    }
}
