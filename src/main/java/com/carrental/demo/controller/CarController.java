package com.carrental.demo.controller;

import com.carrental.demo.domain.Car;
import com.carrental.demo.domain.SearchRequest;
import com.carrental.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Controller
public class CarController {

	@Autowired
	private CarService carService;

    @RequestMapping("/search")
    public String index(Model model) {
        model.addAttribute("searchRequest", new SearchRequest());
        return "search";
    }

    @RequestMapping("/doSearch")
    public String searchCars(@Valid @ModelAttribute("searchRequest") SearchRequest searchRequest, BindingResult bindingResult, Model model) throws ExecutionException, InterruptedException {
        if (!bindingResult.hasErrors()) { // validation errors
            LocalDate rentDate = searchRequest.getRentDate();
            LocalDate returnDate = searchRequest.getReturnDate();
            if(rentDate.isAfter(returnDate)) {
                bindingResult.rejectValue("returnDate", "error.returnDate", "return date must be greater than rent date");
                return "search";
            }
            Future<List<Car>> carsFuture = carService.search(rentDate, returnDate);
            model.addAttribute("cars", carsFuture.get());
        }

        return "cars";
    }

}
