package com.clippercuts.controller;

import com.clippercuts.dao.PaymentStatusDao;
import com.clippercuts.entity.Paymentstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/paymentstatuses")
public class PaymentstatusController {

    @Autowired
    private PaymentStatusDao paymentStatusDao;

    @GetMapping(path ="/list", produces = "application/json")
    public List<Paymentstatus> get() {

        List<Paymentstatus> paymentstatuses = this.paymentStatusDao.findAll();

        paymentstatuses = paymentstatuses.stream().map(
                paymentstatus -> { Paymentstatus paystat= new Paymentstatus();
                  paystat.setId(paymentstatus.getId());
                    paystat.setName(paymentstatus.getName());
                    return paystat; }
        ).collect(Collectors.toList());

        return paymentstatuses;

    }

}


