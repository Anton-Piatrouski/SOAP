package by.epam.service.impl;

import java.util.List;

import javax.jws.WebService;

import by.epam.exception.custom.DaoException;
import by.epam.model.bean.Customer;
import by.epam.model.bean.Customers;
import by.epam.model.factory.ReservationImplFactory;
import by.epam.model.iface.ReservationDAO;
import by.epam.service.CustomerService;

@WebService(endpointInterface = "by.epam.service.CustomerService")
public class CustomerServiceImpl implements CustomerService {
	private ReservationDAO dao = ReservationImplFactory.getImplementation();
	
	@Override
	public Customers getCustomers() {
		Customers customers = new Customers();
		try {
			List<Customer> list = dao.getCustomers();
			customers.setCustomers(list);
		} catch (DaoException ex) {
			System.out.println(ex);
		}
		return customers;
	}

	@Override
	public Customer getCustomerById(int id) {
		Customer customer;
		try {
			customer = dao.getCustomerById(id);
		} catch (DaoException ex) {
			customer = new Customer();
			System.out.println(ex);
		}
		return customer;
	}

	@Override
	public void addCustomer(Customer customer) {
		try {
			dao.addCustomer(customer);
		} catch (DaoException ex) {
			System.out.println(ex);
		}
	}

	@Override
	public void deleteCustomer(int id) {
		try {
			dao.deleteCustomer(id);
		} catch (DaoException ex) {
			System.out.println(ex);
		}
	}

	@Override
	public void updateCustomer(Customer customer) {
		try {
			dao.updateCustomer(customer);
		} catch (DaoException ex) {
			System.out.println(ex);
		}
	}
}
