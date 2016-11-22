package transportDienstAdapterREST;



import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import mps.core.auftragsUndAngebotsVerwaltung.EKunde;
import mps.core.fertigung.EBauteil;

public class RESTConnector {
	
	
	public static String SCENARIO_URI = "http://localhost:8080/uppsRESTApi/";
	public static String SCENARIO_PATH = "transport/request";
	
	//resources
	private WebTarget target;
	private Client client;
	
	//Data
	private String uri; //supply the uri of the web service you want to connect to 
	
	public RESTConnector() {
		this(SCENARIO_URI);
	}
	
	public RESTConnector(String uri) {
		this.uri = uri;
		this.client = ClientBuilder.newClient();
		this.client.register(new org.glassfish.jersey.moxy.json.MoxyJsonFeature());
		
		this.target = this.client.target(this.uri);
	}
	
	
	public long submitTransportRequest(EKunde k,  EBauteil b) {
		//convert data to json (our message body)
		JsonObject sendData = toJson(k,b);
		
		Entity<JsonObject> payload = Entity.json(sendData);
		
		//send message via POST method
		Response response = target.path(SCENARIO_PATH).request().post(payload);
		
		//transport request submitted to web service.
		
		//read id from the server response
		long transportRequestId = response.readEntity(Long.class);
		
		return transportRequestId;
	}

	private JsonObject toJson(EKunde k, EBauteil b) {
		JsonObject deliveryItemObj = Json.createObjectBuilder()
				.add("id", b.getNr())
				.add("itemName", b.getName())
				.build();
		
		JsonObject rootObject = Json.createObjectBuilder()
				.add("name", k.getName())
				.add("adress", k.getAdresse())
				.add("deliveryItem", deliveryItemObj)
				.build();
		
		return rootObject;
		
	}


}
