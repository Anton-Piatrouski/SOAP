package by.epam.model.iface;

import java.util.List;

import by.epam.exception.custom.DaoException;
import by.epam.model.bean.Customer;

public interface ReservationDAO {
	List<Customer> getCustomers() throws DaoException;
	Customer getCustomerById(int id) throws DaoException;
	void addCustomer(Customer customer) throws DaoException;
	void deleteCustomer(int id) throws DaoException;
	void updateCustomer(Customer customer) throws DaoException;
}
