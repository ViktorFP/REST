package by.epamlab.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.xml.sax.SAXException;

import by.epamlab.Constants;
import by.epamlab.beans.ifaces.IRepositoryDAO;
import by.epamlab.beans.ifaces.IReservationDAO;
import by.epamlab.beans.reservations.Reservation;
import by.epamlab.beans.reservations.customer.Customer;
import by.epamlab.factories.ReservationFactory;

public class RepositoryImplHard implements IRepositoryDAO {
	private static Map<String, Reservation> repository = new HashMap<>();
	private static List<Customer> customers = new ArrayList<>();

	@Override
	public Reservation getReservation(String fileName) throws IOException, SAXException {
		Reservation reservation = null;
		if (repository.containsKey(fileName)) {
			reservation = repository.get(fileName);
		} else {
			IReservationDAO reservationDAO = ReservationFactory.getClassFromFactory();
			reservation = reservationDAO.getReservation(fileName);
			repository.put(fileName, reservation);
		}
		return reservation;
	}

	// ---------------------
	private void scan() throws IOException, SAXException {
		String path = this.getClass().getClassLoader().getResource("").getPath();
		path = path.replace("%20", " ");
		File dir = new File(path);
		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(Constants.EXTENSION);
			}
		});
		if (files != null) {
			int size = files.length;
			for (int i = 0; i < size; i++) {
				String name = files[i].getName();
				name = name.substring(0, name.lastIndexOf('.'));
				getReservation(name);
			}
		}
	}

	@Override
	public List<Customer> getCustomers() throws IOException, SAXException {
		scan();
		Set<Map.Entry<String, Reservation>> set = repository.entrySet();
		for (Entry<String, Reservation> e : set) {
			Customer customer = e.getValue().getCustomer();
			if (!customers.contains(customer)) {
				customers.add(customer);
			}
		}
		return customers;
	}

	@Override
	public Customer getCustomer(String id) throws IOException, SAXException {
		for (Customer c : getCustomers()) {
			if (c.getCustomerDocID().equals(id)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public Customer addCustomer(Customer customer) throws IOException, SAXException {
		customer.setCustomerDocID(String.valueOf(customers.size() + 1));
		synchronized (customers) {
			customers.add(customer);
		}
		return customer;
	}

	@Override
	public Customer updateCustomer(Customer customer) throws IOException, SAXException {
		for (Customer c : getCustomers()) {
			if (c.getCustomerDocID().equals(customer.getCustomerDocID())) {
				synchronized (customers) {
					c = customer;
				}
				return c;
			}
		}
		return null;
	}
}
