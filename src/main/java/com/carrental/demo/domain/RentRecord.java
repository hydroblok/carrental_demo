package com.carrental.demo.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class RentRecord {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long carId;

    @Column(name = "rentDate", columnDefinition = "DATE")
    private LocalDate rentDate;

    @Column(name = "returnDate", columnDefinition = "DATE")
    private LocalDate returnDate;

    private String rentBy;

    public RentRecord(){

    }

    public RentRecord(Long carId, LocalDate rentDate, LocalDate returnDate, String rentBy){
        this.carId = carId;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
    }
}
