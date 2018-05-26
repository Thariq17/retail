package com.retailpos.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.retailpos.dao.UserDao;
import com.retailpos.model.User;

@Service
public class UserService {

	@Autowired
	UserDao userDao;

	public int saveNewUser(User user) throws Exception {
		String query = "select * from users where user_name = '" + user.getUsername() + "' and location =  '"
				+ user.getLocation() + "' and role_id = "+user.getRole().getId();
		System.out.println(query);
		Boolean duplicateUser = userDao.checkDuplicateUser(query);
		if (duplicateUser) {
			throw new Exception("User already exist");
		} else {
			String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
			user.setPassword(hashedPassword);
			return userDao.saveNewUser(user);
		}
	}

	public User getUserById(int id) {
		return userDao.getUserById(id);
	}

	public void deleteUserById(int id) throws Exception {
		userDao.deleteUserById(id);
	}

	public User updateUserById(User user, int id) throws Exception {
		return userDao.updateUserById(user, id);
	}

}
