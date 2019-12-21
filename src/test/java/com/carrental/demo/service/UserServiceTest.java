package com.carrental.demo.service;

import com.carrental.demo.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.*;

import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.carrental.demo.domain.User;
import com.carrental.demo.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceTest {

    @Autowired
	private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void addUser() {
    	User user = new User("testuser", "testuser", "USER");
    	assertNull(user.getId());
    	userRepository.save(user);
    	assertNotNull(user.getId());
    }
    
}
