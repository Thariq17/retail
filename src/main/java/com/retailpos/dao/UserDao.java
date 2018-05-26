package com.retailpos.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.retailpos.controller.Users;
import com.retailpos.model.User;

@Repository
public class UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public int saveNewUser(User user) {
		user.setCreatedAt(new Date().getTime());
		int i = (Integer) sessionFactory.getCurrentSession().save(user);
		return i;

	}

	@Transactional
	public User getUserById(int id) {
		User user = (User) sessionFactory.getCurrentSession().get(User.class, id);
		return user;
	}

	@Transactional
	public void deleteUserById(int id) throws Exception {
		User user = getUserById(id);
		if (user != null)
			sessionFactory.getCurrentSession().delete(user);
		else
			throw new Exception("requested resource not found");
	}

	@Transactional
	public User updateUserById(User newUser, int id) throws Exception {
		User user = getUserById(id);
		if (user != null) {
			user.merge(user, newUser);
			user.setUpdatedAt(new Date().getTime());
			sessionFactory.getCurrentSession().update(user);
		} else
			throw new Exception("requested resource not found");
		return user;
	}

	@Transactional
	public Boolean checkDuplicateUser(String query) {
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(query);
		List l = sqlQuery.list();
		if (l.size() > 0 && !l.isEmpty())
			return true;
		else
			return false;

	}

	@Transactional
	public User authenticate(String username, String password, String location) throws Exception {
		String query = "select * from users where user_name = '" + username + "'" + " and location = '" + location
				+ "'";
		SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(query);
		sqlQuery.addEntity(User.class);
		List l = sqlQuery.list();
		if(l.isEmpty()){
			throw new Exception("no such user exist in this location");
		}
		User user=(User) l.get(0);
		return user;
	}

}
