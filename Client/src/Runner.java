import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class Runner {
	private static final String BASE_URI = "http://localhost:8080/Rest";
	private static final String PATH_CUSTOMER = "/CustomerInfoService/customer/";

	public static void main(String[] args) {
		String repFile = "1";

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource resource = client.resource(BASE_URI);

		WebResource customerResource = resource.path("rest").path(PATH_CUSTOMER + repFile);
		System.out.println("Client Response \n" + getClientResponse(customerResource));
		System.out.println("Response \n" + getResponse(customerResource));
	}

	private static String getClientResponse(WebResource resource) {
		return resource.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class).toString();
	}

	private static String getResponse(WebResource resource) {
		return resource.accept(MediaType.TEXT_PLAIN).get(String.class);
	}
}
