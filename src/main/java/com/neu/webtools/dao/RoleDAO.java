package com.neu.webtools.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.neu.webtools.pojo.Role;

public class RoleDAO extends DAO {
	
	public Role get(String role_name) throws Exception {
		try {
			begin();
			Query query = getSession().createQuery("from Role where role_name = :role_name");
			query.setString("role_name", role_name);
			Role role = (Role) query.uniqueResult();
			System.out.println("User belongs to the ROLE"+role);
			return role;
		}catch(HibernateException e){
			rollback();
			throw new Exception("Role not found");
		}
	}
	
	/*//Inserting a role record during registration
	public Role registerRole(Role role) throws Exception {
		try {
			begin();
			System.out.println("Inside the registerRole method");
			getSession().save(role);
			commit();
			return role;
		}catch(HibernateException e){
			rollback();
			throw new Exception("Registration unsuccessful");
		}
	}*/
}
