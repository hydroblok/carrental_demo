package com.carrental.demo.service;

import com.carrental.demo.domain.AvailableCar;
import com.carrental.demo.domain.Car;
import com.carrental.demo.domain.RentRecord;
import com.carrental.demo.repository.CarRepository;
import com.carrental.demo.repository.RentRecordRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private RentRecordRepository rentRecordRepository;

    @Test
    public void searchByDateInEmptyPeriodBetweenTwoPeriod() {
        Car car1 = new Car(1001L, "Toyota Camry", "GZ", "Red", 100D );
        Car car2 = new Car(1002L, "Toyota Camry", "GZ", "Red", 100D );
        when(carRepository.findAll()).thenReturn(Arrays.asList(car1, car2));
        LocalDate rentDate1 = LocalDate.parse("2019-12-10");
        LocalDate returnDate1 = LocalDate.parse("2019-12-15");
        LocalDate rentDate2 = LocalDate.parse("2019-12-20");
        LocalDate returnDate2 = LocalDate.parse("2019-12-25");
        LocalDate rentDate3 = LocalDate.parse("2019-12-16");
        LocalDate returnDate3 = LocalDate.parse("2019-12-17");
        LocalDate rentDate4 = LocalDate.parse("2019-12-18");
        LocalDate returnDate4 = LocalDate.parse("2019-12-19");
        RentRecord rentRecord1 = new RentRecord(1001L, rentDate1, returnDate1, "JUNIT");
        RentRecord rentRecord2 = new RentRecord(1001L, rentDate2, returnDate2, "JUNIT");
        RentRecord rentRecord3 = new RentRecord(1001L, rentDate3, returnDate3, "JUNIT");
        RentRecord rentRecord4 = new RentRecord(1002L, rentDate1, returnDate1, "JUNIT");
        RentRecord rentRecord5 = new RentRecord(1002L, rentDate2, returnDate2, "JUNIT");
        when(rentRecordRepository.findRentRecordByCarId(1001L)).thenReturn(Arrays.asList(rentRecord1, rentRecord3, rentRecord2));
        when(rentRecordRepository.findRentRecordByCarId(1002L)).thenReturn(Arrays.asList(rentRecord4, rentRecord5));
        List<AvailableCar> cars = carService.search(rentDate3, returnDate4);
        assertEquals(1, cars.size());
        List<AvailableCar> cars1 = carService.search(rentDate4, returnDate4);
        assertEquals(2, cars1.size());
    }

    @Test
    public void searchByDateLaterThanTheLatestReturnDate() {
        Car car1 = new Car(1001L, "Toyota Camry", "GZ", "Red", 100D );
        Car car2 = new Car(1002L, "Toyota Camry", "GZ", "Red", 100D );
        when(carRepository.findAll()).thenReturn(Arrays.asList(car1, car2));
        LocalDate rentDate1 = LocalDate.parse("2019-12-10");
        LocalDate returnDate1 = LocalDate.parse("2019-12-15");
        LocalDate rentDate2 = LocalDate.parse("2019-12-20");
        LocalDate returnDate2 = LocalDate.parse("2019-12-25");
        RentRecord rentRecord1 = new RentRecord(1001L, rentDate1, returnDate1, "JUNIT");
        RentRecord rentRecord2 = new RentRecord(1001L, rentDate2, returnDate2, "JUNIT");
        RentRecord rentRecord3 = new RentRecord(1002L, rentDate1, returnDate1, "JUNIT");
        RentRecord rentRecord4 = new RentRecord(1002L, rentDate2, returnDate2, "JUNIT");
        when(rentRecordRepository.findRentRecordByCarId(1001L)).thenReturn(Arrays.asList(rentRecord1, rentRecord2));
        when(rentRecordRepository.findRentRecordByCarId(1002L)).thenReturn(Arrays.asList(rentRecord3, rentRecord4));
        LocalDate rentDate3 = LocalDate.parse("2019-12-26");
        LocalDate returnDate3 = LocalDate.parse("2019-12-27");
        List<AvailableCar> cars = carService.search(rentDate3, returnDate3);
        assertEquals(2, cars.size());
    }

}