package com.carrental.demo.repository;

import com.carrental.demo.domain.Car;
import com.carrental.demo.domain.RentRecord;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface RentRecordRepository extends CrudRepository<RentRecord, Long>  {

	List<RentRecord> findRentRecordByCarId(Long carId);

}

