package com.neu.webtools.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.webtools.dao.AppUsersDAO;
import com.neu.webtools.dao.RoleDAO;
import com.neu.webtools.pojo.AppUsers;
import com.neu.webtools.pojo.Role;


@Controller
@RequestMapping(value = "/")
public class HomePageController {
	
	@RequestMapping(value = "register.htm", method = RequestMethod.POST)
	public String register(HttpServletRequest request, AppUsersDAO appUsersDao, ModelMap map) {
		System.out.println("Into register controller");
		
		String firstname = request.getParameter("fname");
		String lastname = request.getParameter("lname");
		String email = request.getParameter("email");
		String username = request.getParameter("uname");
		String password = request.getParameter("pwd");
		String r= request.getParameter("role");
		
		Role role = new Role();
		role.setRole_name(r);
		
		AppUsers user = new AppUsers();
		user.setFname(firstname);
		user.setLname(lastname);
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(password);
		
		//Getting first the list of users and then adding the user(or many in other cases) to it
		//role.getUser().add(user);
		user.setRole(role);
		
		try {
			user = appUsersDao.registerUser(user);
			//u = appUsersDao.registerUser(user);
			map.addAttribute("successMessage", "Registration Successful!");
			return "home";
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	@RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
	protected String logout(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		session.invalidate();
		System.out.println("User successfully logged out");
		return "home";
		
	}
	
	@RequestMapping(value = "/login.htm", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView loginRequest(HttpServletRequest request, HttpServletResponse response, AppUsersDAO appUsersDao, RoleDAO roleDao, ModelMap map){
		System.out.println("Into loginRequest controller");
		
		HttpSession httpSession = request.getSession(true);
		String username = request.getParameter("uname");
		System.out.println("USERNAME" + username);
		String password = request.getParameter("pwd");
		System.out.println("PASSWORD" + password);
		//String role = request.getParameter("role");
		try {
			AppUsers users = appUsersDao.get(username, password);
			if(users != null) {
				httpSession.setAttribute("name", users);
					
					// Checking for the appropriate role and rendering the appropriate page
					if(users.getRole().getRole_name().equalsIgnoreCase("employer")) {
						return new ModelAndView( "employeer-home", "user",users);
					}else if(users.getRole().getRole_name().equalsIgnoreCase("student")) {
						return new ModelAndView( "student-home", "name", users);
					}else {
						map.addAttribute("errorMessage", "Invalid username/password!");
						return new ModelAndView("home");
					}
					
				//}
				
			}else {
				map.addAttribute("errorMessage", "Invalid username/password!");
				return new ModelAndView("home");
			}
		}catch(Exception e) {			
			e.printStackTrace();
		}
		
		return null;
	}
}
