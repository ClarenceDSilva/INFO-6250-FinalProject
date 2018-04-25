package com.neu.webtools.dao;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.neu.webtools.pojo.AppUsers;

public class AppUsersDAO extends DAO {
	
	public AppUsersDAO() {
		
	}
	
	// Function for login
	public AppUsers get(String username, String password) throws Exception{
		try {
			begin();
			Query query = getSession().createQuery("from AppUsers where username = :username and password = :password");
			query.setString("username", username);
			query.setString("password", password);
			AppUsers user = (AppUsers) query.uniqueResult();
			close();
			
			if(user == null) {
				System.out.println("No User found in DAO Class");
				return null;
			}else {
				System.out.println("User "+user.getFname()+" has been logged in");
				return user;
			}
//			
		}catch(HibernateException e){
			rollback();
			throw new Exception("Invalid username or password");
		}
		
	}
	
	// Function for registration
	public AppUsers registerUser(AppUsers user) {
		try {
			begin();
			System.out.println("INSIDE THE registerUser METHOD");
			getSession().save(user);
			commit();
			close();
			return user;
		}catch(HibernateException e){
			rollback();
			e.printStackTrace();
			return null;
		}
		
	}
}

