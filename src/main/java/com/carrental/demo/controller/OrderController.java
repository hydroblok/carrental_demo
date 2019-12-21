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

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository repository;

    @RequestMapping(value = "/order/{carId}")
    public String reserveCar(Principal principal, @PathVariable("carId") Long carId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate rentDate,  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate, Model model){
        Order order = orderService.createOrder(carId, rentDate, returnDate, principal.getName());
        repository.save(order);
        model.addAttribute("orderDetails", order);
        return "order";
    }

}
