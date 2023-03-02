package com.mm.beauty.api.controller;

import com.mm.beauty.api.dto.SalesDTO;
import com.mm.beauty.api.entity.Sales;
import com.mm.beauty.api.facade.SalesFacade;
import com.mm.beauty.api.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sale")
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private SalesFacade salesFacade;

    @GetMapping("/all")
    public ResponseEntity<List<SalesDTO>> getAllOrders() {
        List<SalesDTO> orderList = saleService.getAllOrders().stream()
                .map(salesFacade::SalesTOSalesDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<List<SalesDTO>>(orderList, HttpStatus.OK);
    }

    @GetMapping("/userorders")
    public ResponseEntity<List<SalesDTO>> getUserOrders(Principal principal) {
        List<SalesDTO> orderList = saleService.getAllOrdersPerUser(principal).stream()
                .map(salesFacade::SalesTOSalesDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<List<SalesDTO>>(orderList, HttpStatus.OK);
    }
}
