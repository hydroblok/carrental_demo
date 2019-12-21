package com.carrental.demo.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class SearchRequest {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDate rentDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate returnDate;
}
