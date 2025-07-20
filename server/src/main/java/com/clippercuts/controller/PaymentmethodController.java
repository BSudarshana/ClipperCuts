package com.clippercuts.controller;

import com.clippercuts.dao.PaymentmethodDao;
import com.clippercuts.entity.Paymentmethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/paymentmethods")
public class PaymentmethodController {

    @Autowired
    private PaymentmethodDao paymentmethodDao;

    @GetMapping(path = "/list", produces = "application/json")
    public List<Paymentmethod> get() {
        List<Paymentmethod> paymentmethods = this.paymentmethodDao.findAll();

        return paymentmethods.stream().map(paymeth -> {
            Paymentmethod pm = new Paymentmethod();
            pm.setId(paymeth.getId());
            pm.setName(paymeth.getName());
            return pm;
        }).collect(Collectors.toList());
    }
}