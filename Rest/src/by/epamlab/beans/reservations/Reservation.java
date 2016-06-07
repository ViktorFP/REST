package by.epamlab.beans.reservations;

import java.util.ArrayList;
import java.util.List;

import by.epamlab.beans.reservations.customer.Customer;

public class Reservation {
	private String code;
	private String description;
	private List<ResComponent> resComponents;
	private Customer customer;
	private FareFamily fareFamily;

	public Reservation() {
	}

	public Reservation(String code, String description, List<ResComponent> resComponents) {
		this.code = code;
		this.description = description;
		this.resComponents = resComponents;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ResComponent> getResComponents() {
		return resComponents;
	}

	public List<ResComponent> getResComponents(String code, String componentTypeCode) {
		List<ResComponent> components = new ArrayList<ResComponent>();
		if (this.code.equals(code)) {
			for (ResComponent c : resComponents) {
				if (c.getComponentTypeCode().equals(componentTypeCode)) {
					components.add(c);
				}
			}
		}
		return components;
	}

	public void setResComponents(List<ResComponent> resComponents) {
		this.resComponents = resComponents;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public FareFamily getFareFamily() {
		return fareFamily;
	}

	public void setFareFamily(FareFamily fareFamily) {
		this.fareFamily = fareFamily;
	}
}
