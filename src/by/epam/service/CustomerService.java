package by.epam.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import by.epam.model.bean.Customer;
import by.epam.model.bean.Customers;

@WebService
public interface CustomerService {
	@WebMethod
	Customers getCustomers();
	
	@WebMethod
	Customer getCustomerById(int id);
	
	@WebMethod
	void addCustomer(Customer customer);
	
	@WebMethod
	void deleteCustomer(int id);
	
	@WebMethod
	void updateCustomer(Customer customer);
}
