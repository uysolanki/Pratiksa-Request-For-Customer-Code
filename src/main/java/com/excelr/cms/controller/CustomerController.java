package com.excelr.cms.controller;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.excelr.cms.entity.Customer;
import com.excelr.cms.entity.User;
import com.excelr.cms.repository.UserRepository;
import com.excelr.cms.service.CustomerService;

@Controller
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	UserRepository userRepository;
	
	private static final Logger logger=Logger.getLogger(CustomerController.class);
	
	@RequestMapping("/registerCustomer")
	public String registerCustomer(Model model)
	{
		logger.info("New Customer Registration form called");    
		Customer c1=new Customer();
		model.addAttribute("cust", c1);
		return "customer-registration-form";
	}
	
	@RequestMapping("/registerUser")
	public String registerUser(Model model)
	{
		logger.info("New User Registration form called");    
		User u1=new User();
		model.addAttribute("user", u1);
		return "user-registration-form";
	}
	
	@RequestMapping("/")
	public String customerList(Model model)
	{
		logger.info("List of Customer shown");
		List<Customer> allCustomers =customerService.customerList();
		model.addAttribute("customers",allCustomers);
		return "customer-list";
	}
	
	@PostMapping("/addcustomer")
	public String addcustomer(@ModelAttribute Customer customer)
	{
		customerService.addcustomer(customer);
		return "redirect:/";
	}
	
	@PostMapping("/addUser")
	public String addUser(@ModelAttribute User user)
	{
		PasswordEncoder encoder= PasswordEncoderFactories.createDelegatingPasswordEncoder();
		user.setAccountEnabledStatus(1);
		user.setAccountLockedStatus(1);
		String encodedPassword=encoder.encode(user.getPassword());
		user.setPassword(encodedPassword.substring(8));
		userRepository.save(user);
		return "redirect:/";
	}
	
	@RequestMapping("/deletecustomer/{id}")
	public String deletecustomer(@PathVariable("id") int cno)
	{
		customerService.deletecustomer(cno);
		return "redirect:/";
	}
	
	@RequestMapping("/updatecustomerform/{id}")
	public String updateCustomerForm(@PathVariable("id") int cno, Model model)
	{
		Customer customer=customerService.getCustomerById(cno);
		model.addAttribute("customer",customer);
		return "customer-update-form";
	}
	
	@PostMapping("/updatecustomer/{id}")
	public String updatecustomer(@PathVariable("id") int cno,@ModelAttribute Customer customer)
	{
		customerService.updateCustomer(cno,customer);
		return "redirect:/";
	}
	
	@RequestMapping("/403")
	public ModelAndView accesssDenied(Principal user) {

		ModelAndView model = new ModelAndView();

		if (user != null) {
			model.addObject("msg", "Hi " + user.getName() 
			+ ", you do not have permission to access this page!");
		} else {
			model.addObject("msg", 
			    "you do not have permission to access this page!");
		}

		model.setViewName("403");
		return model;

	}


}
