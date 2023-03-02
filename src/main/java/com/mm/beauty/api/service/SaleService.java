package com.mm.beauty.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mm.beauty.api.entity.Sales;
import com.mm.beauty.api.entity.User;

import java.security.Principal;
import java.util.List;

public interface SaleService {

    String newOrder(Long courseId, Principal principal) throws JsonProcessingException;

    void checkOrders() throws Exception;

    List<Sales> getAllOrders();

    List<Sales> getAllOrdersPerUser(Principal principal);

}
