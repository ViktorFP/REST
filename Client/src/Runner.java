import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import by.epamlab.beans.reservations.customer.Customer;

public class Runner {
	private static final String BASE_URI = "http://localhost:8080/Rest";
	private static final String PATH_CUSTOMER = "/CustomerInfoService/customer/";
	private static final String PATTERN = "rest";
	private final static String CUSTOMERS = "list/json";
	private final static String CUSTOMER_ID = "id";
	private final static String CUSTOMER_ADD = "add";
	private final static String CUSTOMER_UPDATE = "update";
	private final static String CUSTOMER_DELETE = "delete";

	public static void main(String[] args) {
		String repFile = "1";// "2";

		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource resource = client.resource(BASE_URI);

			WebResource customerResource = resource.path(PATTERN).path(PATH_CUSTOMER + repFile);
			System.out.println("Client Response \n" + getClientResponse(customerResource));
			System.out.println("Response \n" + getResponse(customerResource));

			customerResource = client.resource(BASE_URI + "/" + PATTERN + PATH_CUSTOMER + CUSTOMERS);
			ClientResponse response = customerResource.get(ClientResponse.class);
			System.out.println("\nAll customers: " + response.getEntity(String.class));

			String id = "RESV_RETRIEVE_Customer1850432";
			customerResource = client.resource(BASE_URI + "/" + PATTERN + PATH_CUSTOMER + CUSTOMER_ID).queryParam("id",
					id);
			response = customerResource.get(ClientResponse.class);
			System.out.println("\nCustomer with ID " + id + ": " + response.getEntity(String.class));

			ObjectMapper mapper = new ObjectMapper();

			customerResource = client.resource(BASE_URI + "/" + PATTERN + PATH_CUSTOMER + CUSTOMER_ADD);
			Customer customer = new Customer();
			customer.setFirstName("NewName");
			customer.setLastName("NewLastName");
			response = customerResource.type("application/json").post(ClientResponse.class,
					mapper.writeValueAsString(customer));
			String responseEntity = response.getEntity(String.class);
			System.out.println("\nAdded customer: " + responseEntity);
			// Added customer:
			// {"customerDocID":3,"firstName":"NewName","lastName":"NewLastName","sequence":0,"email":null,"phone":null,"payments":null}
			// change it
			customer = mapper.readValue(responseEntity, Customer.class);
			customer.setFirstName("ChangedName");
			customerResource = client.resource(BASE_URI + "/" + PATTERN + PATH_CUSTOMER + CUSTOMER_UPDATE);
			response = customerResource.type("application/json").put(ClientResponse.class,
					mapper.writeValueAsString(customer));
			System.out.println("\nAfter change: " + response.getEntity(String.class));
			
			customerResource = client.resource(BASE_URI + "/" + PATTERN + PATH_CUSTOMER + CUSTOMER_DELETE);
			response = customerResource.type("application/json").delete(ClientResponse.class,
					mapper.writeValueAsString(customer));
			System.out.println("\nCustomer deleted: " + response.getEntity(String.class));			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getClientResponse(WebResource resource) {
		return resource.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class).toString();
	}

	private static String getResponse(WebResource resource) {
		return resource.accept(MediaType.TEXT_PLAIN).get(String.class);
	}
}
