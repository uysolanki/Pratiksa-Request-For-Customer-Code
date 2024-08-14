package com.excelr.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excelr.cms.entity.Customer;
import com.excelr.cms.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository customerRepository;
	

	public void addcustomer(Customer customer) {
		customerRepository.save(customer);
	}


	public List<Customer> customerList() {
		return customerRepository.findAll();
	}


	public void deletecustomer(int cno) {
		customerRepository.deleteById(cno);
	}


	public Customer getCustomerById(int cno) {
		return customerRepository.findById(cno).get();
	}


	public void updateCustomer(int cno, Customer customer) {
		Customer customerFromDB=customerRepository.findById(cno).get();
		customerFromDB.setEmail(customer.getEmail());
		customerFromDB.setFirstName(customer.getFirstName());
		customerFromDB.setLastName(customer.getLastName());
		customerFromDB.setPhone(customer.getPhone());
		customerRepository.save(customerFromDB);
		
	}

}
