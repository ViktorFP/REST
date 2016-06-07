package by.epamlab;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.xml.sax.SAXException;

import by.epamlab.beans.ifaces.IReservationDAO;
import by.epamlab.beans.reservations.Reservation;
import by.epamlab.beans.reservations.customer.Customer;
import by.epamlab.factories.ReservationFactory;

@Path("CustomerInfoService")
public class CustomerInfo {

	// http://localhost:8080/Rest/rest/CustomerInfoService/customer/[reservationFileName]

	@GET
	@Path("/customer/{reservationFileName}")
	@Produces(MediaType.TEXT_PLAIN)
	public String customer(@PathParam("reservationFileName") String reservationFileName) {
		Reservation reservation = null;
		IReservationDAO reservationDAO = ReservationFactory.getClassFromFactory();
		try {
			reservation = reservationDAO.getReservation(reservationFileName);
		} catch (IOException | SAXException e) {
			return e.getMessage();
		}
		Customer customer = reservation.getCustomer();
		return customer.getFirstName() + " " + customer.getLastName();
	}
}