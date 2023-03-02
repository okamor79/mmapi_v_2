package com.mm.beauty.api.facade;

import com.mm.beauty.api.dto.SalesDTO;
import com.mm.beauty.api.entity.Sales;
import org.springframework.stereotype.Component;

@Component
public class SalesFacade {

    public SalesDTO SalesTOSalesDTO(Sales sales) {
        SalesDTO salesDTO = new SalesDTO();
        salesDTO.setId(sales.getId());
        salesDTO.setOrderId(sales.getOrderId());
        salesDTO.setCourseId(sales.getCourse().getId());
        salesDTO.setUserId(sales.getUser().getId());
        salesDTO.setDatePayment(sales.getDatePayment());
        salesDTO.setCheckCode(sales.getCheckCode());
        switch (sales.getStatus()) {
            case ORDER_COMPLETED: {
                salesDTO.setStatus("Замовлення оплачено");
                break;
            }
            case ORDER_WAIT: {
                salesDTO.setStatus("Очікує оплати/підтвредження");
                break;
            }
            case ORDER_CANCELED: {
                salesDTO.setStatus("Замовлення скасовано");
                break;
            }
        }
        salesDTO.setOrderAmount(sales.getOrderAmount());
        salesDTO.setCourseName(sales.getCourse().getName());
        salesDTO.setFullUsername(sales.getUser().getFullName());
        salesDTO.setExpireDate(sales.getExpireDate());
        return salesDTO;
    }
}
