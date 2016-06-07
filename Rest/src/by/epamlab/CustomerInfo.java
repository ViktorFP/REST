package by.epamlab;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.xml.sax.SAXException;

import by.epamlab.beans.ifaces.IRepositoryDAO;
import by.epamlab.beans.reservations.customer.Customer;
import by.epamlab.factories.RepositoryFactory;

@Path("CustomerInfoService")
public class CustomerInfo {

	// http://localhost:8080/Rest/rest/CustomerInfoService/customer/[reservationFileName]
	@GET
	@Path("/customer/{reservationFileName}")
	@Produces(MediaType.TEXT_PLAIN)
	public String customer(@PathParam("reservationFileName") String reservationFileName) {
		Customer customer = null;
		try {
			IRepositoryDAO IRepositoryDAO = RepositoryFactory.getRepository();
			customer = IRepositoryDAO.getReservation(reservationFileName).getCustomer();
		} catch (IOException | SAXException e) {
			return "No such reservation!";
		}
		return customer.getFirstName() + " " + customer.getLastName() + "\nEmail: "
				+ customer.getEmail().getEmailAddress() + "\nPhone: " + customer.getPhone().getPhoneNumber();
	}
}