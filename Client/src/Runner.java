import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class Runner {
	private static final String BASE_URI = "http://localhost:8080/Rest";
	private static final String PATH_CUSTOMER = "/CustomerInfoService/customer/";
	private static final String PATTERN = "rest";
	private final static String CUSTOMERS = "list/json";
	private final static String CUSTOMER_ID = "id";

	public static void main(String[] args) {
		String repFile = "1";// "2";

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
		WebResource webResourceGet = client.resource(BASE_URI + "/" + PATTERN + PATH_CUSTOMER + CUSTOMER_ID)
				.queryParam("id", id);
		response = webResourceGet.get(ClientResponse.class);
		System.out.println("\nCustomer with ID " + id + ": " + response.getEntity(String.class));
	}

	private static String getClientResponse(WebResource resource) {
		return resource.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class).toString();
	}

	private static String getResponse(WebResource resource) {
		return resource.accept(MediaType.TEXT_PLAIN).get(String.class);
	}
}
