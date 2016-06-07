package by.epamlab;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.xml.sax.SAXException;

import by.epamlab.beans.ifaces.IRepositoryDAO;
import by.epamlab.beans.reservations.customer.Customer;
import by.epamlab.factories.RepositoryFactory;

@Path("CustomerInfoService")
public class CustomerInfo {
	private static IRepositoryDAO repositoryDAO = RepositoryFactory.getRepository();;

	private final String CUSTOMER = "/customer/";

	// http://localhost:8080/Rest/rest/CustomerInfoService/customer/[reservationFileName]
	@GET
	@Path(CUSTOMER + "{reservationFileName}")
	@Produces(MediaType.TEXT_PLAIN)
	public String customer(@PathParam("reservationFileName") String reservationFileName) {
		Customer customer = null;
		try {
			customer = repositoryDAO.getReservation(reservationFileName).getCustomer();
		} catch (IOException | SAXException e) {
			return "No such reservation!";
		}
		return customer.getFirstName() + " " + customer.getLastName() + "\nEmail: "
				+ customer.getEmail().getEmailAddress() + "\nPhone: " + customer.getPhone().getPhoneNumber();
	}

	// http://localhost:8080/Rest/rest/CustomerInfoService/customer/list/json
	@GET
	@Path(CUSTOMER + "list/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String getListJson() {
		try {
			return toJson(repositoryDAO.getCustomers());
		} catch (IOException | SAXException e) {
			return e.getMessage();
		}
	}

	// http://localhost:8080/Rest/rest/CustomerInfoService/customer/id?id=RESV_RETRIEVE_Customer1850432
	@GET
	@Path(CUSTOMER + "id")
	@Produces({ MediaType.APPLICATION_JSON })
	public String getCustomerById(@QueryParam("id") String id) {
		try {
			return toJson(repositoryDAO.getCustomer(id));
		} catch (IOException | SAXException e) {
			return e.getMessage();
		}
	}

	// -------------------
	private String toJson(Object object) throws JsonGenerationException, JsonMappingException, IOException {
		return new ObjectMapper().writeValueAsString(object);
	}
}