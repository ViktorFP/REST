package by.epamlab.beans.ifaces;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

import by.epamlab.beans.reservations.Reservation;
import by.epamlab.beans.reservations.customer.Customer;

public interface IRepositoryDAO {
	Reservation getReservation(String fileName) throws IOException, SAXException;

	List<Customer> getCustomers() throws IOException, SAXException;

	Customer getCustomer(String id) throws IOException, SAXException;

	Customer addCustomer(Customer customer) throws IOException, SAXException;
	Customer updateCustomer(Customer customer) throws IOException, SAXException;
	boolean deleteCustomer(String id);
}
