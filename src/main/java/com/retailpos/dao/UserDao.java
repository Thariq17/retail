package com.retailpos.dao;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.retailpos.model.User;

@Repository
public class UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public int saveNewUser(User user) {
		int i=(Integer) sessionFactory.getCurrentSession().save(user);
		return i;
	}

}
