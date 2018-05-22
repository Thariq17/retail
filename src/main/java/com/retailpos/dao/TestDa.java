package com.retailpos.dao;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.retailpos.model.User;

@Repository
public class TestDa {

	 @Autowired
	    private SessionFactory sessionFactory;
	 
	 @Transactional
	 public void addUser(User user){
		 sessionFactory.getCurrentSession().save(user);
	 }
	
}
