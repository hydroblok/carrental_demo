package com.carrental.demo.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AvailableCar extends Car{
    private LocalDate requestRentDate;
    private LocalDate requestReturnDate;

    public AvailableCar(Long carId, String carModel, String city, String color, Double price, LocalDate requestRentDate, LocalDate requestReturnDate){
        super(carId,carModel,city,color,price);
        this.requestRentDate = requestRentDate;
        this.requestReturnDate = requestReturnDate;
    }
}
