package com.retailpos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retailpos.dao.UserDao;
import com.retailpos.model.User;

@Service
public class UserService {
	@Autowired
	UserDao userDao; 
	
	public int saveNewUser(User user){
		return userDao.saveNewUser(user);
		
	}
	
	

}
