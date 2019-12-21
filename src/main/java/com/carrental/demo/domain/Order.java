package com.carrental.demo.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "order_table")
public class Order {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String city;

	private String carModel;

	private Long carId;

	private String carColor;

	private LocalDate rentDate;

	private LocalDate returnDate;

	private int TotalDays;

	private Double unitPrice;

	private Double totalPrice;

	private String ownedBy;
}
