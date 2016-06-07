package by.epamlab;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("CustomerInfoService")
public class CustomerInfo {

	/*http://localhost:8080/Rest/rest/CustomerInfoService/customer/[reservation]*/
	@GET
	@Path("/customer/{reservation}")
	@Produces(MediaType.TEXT_HTML)
	public String customer(@PathParam("reservation") String reservation) {
		/*
		 * jar модуля; получение через jndi объекта (краткое. все на JBoss);
		 * getFile(....) ...
		 */
		return "testCustomer";
	}
}