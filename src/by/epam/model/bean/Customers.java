package by.epam.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customers implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<Customer> customers;
	
	public Customers() {
		customers = new ArrayList<Customer>();
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (Customer customer : customers) {
			sb.append(customer.toString());
			sb.append('\n');
		}
		
		return sb.toString();
	}
}
