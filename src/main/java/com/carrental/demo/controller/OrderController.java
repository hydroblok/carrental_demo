package com.carrental.demo.controller;

import com.carrental.demo.domain.Order;
import com.carrental.demo.repository.OrderRepository;
import com.carrental.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

/**
 * order' API
 * @author Jimmy Luo
 * @date 20191221
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository repository;

    @RequestMapping(value = "/order/{carId}")
    public String reserveCar(Principal principal, @PathVariable("carId") Long carId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate rentDate,  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate, Model model){
        model.addAttribute("orderDetails", orderService.createOrder(carId, rentDate, returnDate, principal.getName()));
        return "order";
    }

}
