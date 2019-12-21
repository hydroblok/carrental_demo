package com.carrental.demo.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

    private Long carId;

    private String city;

    private String color;

	private String carModel;

	@Column(name = "rentDate", columnDefinition = "DATE")
	private LocalDate rentDate;

	@Column(name = "returnDate", columnDefinition = "DATE")
	private LocalDate returnDate;

	private Double price;

	public Car() {

	}

	public Car(Long carId, String carModel, String city, String color, LocalDate rentDate, LocalDate returnDate, Double price){
		this.carId = carId;
		this.carModel = carModel;
		this.city = city;
		this.color = color;
		this.rentDate = rentDate;
		this.returnDate = returnDate;
		this.price = price;
	}
}
