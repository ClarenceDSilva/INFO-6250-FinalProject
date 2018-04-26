package com.neu.webtools.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.neu.webtools.dao.StudentDAO;
import com.neu.webtools.exception.StudentClassException;
import com.neu.webtools.pojo.AppUsers;
import com.neu.webtools.pojo.JobDetails;

@Controller
@RequestMapping(value = "/")
public class StudentController {

	@RequestMapping(value = "student/viewalljobs.htm", method = RequestMethod.GET)
	public ModelAndView viewAllJobs(HttpServletRequest request, StudentDAO studentDao) throws Exception {
		AppUsers appUsers = (AppUsers) request.getSession().getAttribute("name");
		try {
			request.getSession().setAttribute("name", appUsers);
			List<JobDetails> jobDetails = studentDao.listAllJobs();
			return new ModelAndView("view-jobs", "allJobs",jobDetails);
		}catch(StudentClassException e) {
			System.out.println(e.getMessage());
			return new ModelAndView("errors", "errorMessage", "Error occured while retrieving jobs");
		}
		
	}
}
