package com.carrental.demo.service;

import com.carrental.demo.domain.User;
import com.carrental.demo.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * This class is used by spring controller to authenticate and authorize user
 * @author Jimmy Luo
 * @date 20191221
 */
@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService  {
	private final UserRepository repository;

	@Autowired
	public UserDetailServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {   
    	User curruser = repository.findByUsername(username);
    	
        UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPassword(), true,
        		true, true, true, AuthorityUtils.createAuthorityList(curruser.getRole()));
        
        log.info("User Name:{}, ROLE: {}", username, curruser.getRole());
        return user;
    }

    public User getCurrentUser(String username) {
	    return repository.findByUsername(username);
    }

    public void updateUser(User user) {
	    repository.save(user);
    }
    
}