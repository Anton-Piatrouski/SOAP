package by.epam.model.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import by.epam.exception.custom.DaoException;
import by.epam.model.iface.ReservationDAO;
import by.epam.model.bean.Customer;

public class ReservationImplXml implements ReservationDAO {
	private static final String XML_LOCATION = "C:/Users/Customers.xml";
	private Document document;
	
	public ReservationImplXml() {
		
	}
	
	public List<Customer> getCustomers() throws DaoException {
		init(); // Create XML document object
		
		Element root = document.getDocumentElement();
		NodeList nodes = root.getElementsByTagName("Customer");
		List<Customer> customers = buildCustomers(nodes);
		
		return customers;
	}
	
	public Customer getCustomerById(int id) throws DaoException {
		init(); // Create XML document object
		
		Element root = document.getDocumentElement();
		NodeList nodes = root.getElementsByTagName("Customer");
		Customer customer = new Customer();
		
		for (int i = 0; i < nodes.getLength(); i++) {
			Element customerElement = (Element) nodes.item(i);
			String customerId = customerElement.getAttribute("CustomerID");
			
			if (customerId.equals(String.valueOf(id))) {
				customer = buildCustomer(customerElement);
				break;
			}
		}
		return customer;
	}
	
	public void addCustomer(Customer customer) throws DaoException {
		init(); // Create XML document object
		
		Element root = document.getDocumentElement();
		Element customerElement = createCustomerElement(customer);
		root.appendChild(customerElement);
		// Write into XML
		try {
			write(XML_LOCATION);
		} catch (TransformerException ex) {
			throw new DaoException("Add customer process failure", ex);
		}
	}
	
	public void deleteCustomer(int id) throws DaoException {
		init(); // Create XML document object
		
		Element root = document.getDocumentElement();
		NodeList nodes = root.getElementsByTagName("Customer");
		
		for (int i = 0; i < nodes.getLength(); i++) {
			Element customerElement = (Element) nodes.item(i);
			String customerId = customerElement.getAttribute("CustomerID");
			
			if (customerId.equals(String.valueOf(id))) {
				root.removeChild(customerElement);
				break;
			}
		}
		// Write into XML
		try {
			write(XML_LOCATION);
		} catch (TransformerException ex) {
			throw new DaoException("Delete customer process failure", ex);
		}
	}
	
	public void updateCustomer(Customer customer) throws DaoException {
		init(); // Create XML document object
		
		Element root = document.getDocumentElement();
		NodeList nodes = root.getElementsByTagName("Customer");
		
		for (int i = 0; i < nodes.getLength(); i++) {
			Element customerElement = (Element) nodes.item(i);
			String customerId = customerElement.getAttribute("CustomerID");
			
			if (customerId.equals(customer.getId())) {
				updateCustomerElement(customer, customerElement);
				break;
			}
		}
		// Write into XML
		try {
			write(XML_LOCATION);
		} catch (TransformerException ex) {
			throw new DaoException("Update customer process failure", ex);
		}
	}
	
	private void init() throws DaoException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			this.document = docBuilder.parse(XML_LOCATION);
			
		} catch (ParserConfigurationException ex) {
			throw new DaoException("Parser configuration error", ex);
			
		} catch (IOException | SAXException ex) {
			throw new DaoException("File processing error", ex);
		}
	}
	
	private List<Customer> buildCustomers(NodeList nodes) {
		List<Customer> customers = new ArrayList<>();
		
		for (int i = 0; i < nodes.getLength(); i++) {
			Element customerElement = (Element) nodes.item(i);
			Customer customer = buildCustomer(customerElement);
			customers.add(customer);
		}
		return customers;
	}
	
	private Customer buildCustomer(Element customerElement) {
		String id = customerElement.getAttribute("CustomerID");
		String firstName = customerElement.getAttribute("FirstName");
		String lastName = customerElement.getAttribute("LastName");
		
		Element emailElement = (Element) customerElement.getElementsByTagName("Email").item(0);
		String emailAddress = emailElement.getAttribute("EmailAddress");
		
		Element phoneElement = (Element) customerElement.getElementsByTagName("Phone").item(0);
		String phoneNumber = phoneElement.getAttribute("PhoneNumber");
		
		Customer customer = new Customer();
		customer.setId(id);
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setEmailAddress(emailAddress);
		customer.setPhoneNumber(phoneNumber);
		
		return customer;
	}
	
	private Element createCustomerElement(Customer customer) {
		Element customerElement = document.createElement("Customer");
		// Set attributes
		Attr customerIdAttr = document.createAttribute("CustomerID");
		Attr firstNameAttr = document.createAttribute("FirstName");
		Attr lastNameAttr = document.createAttribute("LastName");
		
		customerIdAttr.setValue(customer.getId());
		firstNameAttr.setValue(customer.getFirstName());
		lastNameAttr.setValue(customer.getLastName());
		
		customerElement.setAttributeNode(customerIdAttr);
		customerElement.setAttributeNode(firstNameAttr);
		customerElement.setAttributeNode(lastNameAttr);
		
		Element emailElement = document.createElement("Email");
		// Set attributes
		Attr emailAddressAttr = document.createAttribute("EmailAddress");
		emailAddressAttr.setValue(customer.getEmailAddress());
		emailElement.setAttributeNode(emailAddressAttr);
		
		Element phoneElement = document.createElement("Phone");
		// Set attributes
		Attr phoneNumberAttr = document.createAttribute("PhoneNumber");
		phoneNumberAttr.setValue(customer.getPhoneNumber());
		phoneElement.setAttributeNode(phoneNumberAttr);
		
		customerElement.appendChild(emailElement);
		customerElement.appendChild(phoneElement);
		
		return customerElement;
	}
	
	private void updateCustomerElement(Customer customer, Element customerElement) {
		// Update attributes
		Attr firstNameAttr = customerElement.getAttributeNode("FirstName");
		Attr lastNameAttr = customerElement.getAttributeNode("LastName");
		
		firstNameAttr.setValue(customer.getFirstName());
		lastNameAttr.setValue(customer.getLastName());
		
		customerElement.setAttributeNode(firstNameAttr);
		customerElement.setAttributeNode(lastNameAttr);
		
		Element emailElement = (Element) customerElement.getElementsByTagName("Email").item(0);
		// Update attributes
		Attr emailAddressAttr = emailElement.getAttributeNode("EmailAddress");
		emailAddressAttr.setValue(customer.getEmailAddress());
		emailElement.setAttributeNode(emailAddressAttr);
		
		Element phoneElement = (Element) customerElement.getElementsByTagName("Phone").item(0);
		// Update attributes
		Attr phoneNumberAttr = phoneElement.getAttributeNode("PhoneNumber");
		phoneNumberAttr.setValue(customer.getPhoneNumber());
		phoneElement.setAttributeNode(phoneNumberAttr);
	}
	
	private void write(String fileName) throws TransformerException {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new File(fileName));
		transformer.transform(source, result);
	}
}
