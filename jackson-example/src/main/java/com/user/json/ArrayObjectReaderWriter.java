package com.user.json;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.model.Address;
import com.user.model.Person;

public class ArrayObjectReaderWriter {
	public static void main(String[] args) throws JsonParseException, IOException {
		int c = 0;
		//JsonFactory jsonFactory = new JsonFactory();
		JsonParser jp = new JsonFactory().createParser(new File("modifiedobjectinput.json")); // modifiedobjectinput files can be used
		jp.setCodec(new ObjectMapper());
		JsonNode jsonNode = jp.readValueAsTree();
		//System.out.println("Checking array : " +  jsonNode.isArray());
		System.out.println("\n Object - Array serializer, deserializer :- \n ");
		if(jsonNode.isArray())
		   c = 1;
		else 
			c = 2;
		
		switch(c){
		case 1 :
			System.out.println("JSON ARRAY .. ");
			
			JsonParser jpar = new JsonFactory().createParser(new File("modifiedarrayinput.json"));
			jpar.setCodec(new ObjectMapper());
			JsonNode jsonNodes = jpar.readValueAsTree();
			
			readarray(jsonNodes);
			writearray(jsonNodes);
			break;
		
		case 2 :
			System.out.println("JSON OBJECT .. ");
			
			
			readobject();
			writeobject();
			 break;
		}
		
	}
	
	

	private static void writeobject() throws FileNotFoundException, IOException {
		JsonParser jsonParser = new JsonFactory().createParser(new File("modifiedobjectinput.json"));
		Person pers = new Person();
		Address address = new Address();
		pers.setAddress(address);
		parseJSON(jsonParser, pers);
		jsonParser.close();
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(new FileOutputStream("modifiedoutput.json"));
		jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
	
		jsonGenerator.writeStartObject(); 
		jsonGenerator.writeStringField("Name", pers.getFirstName()+""+pers.getLastName());
		jsonGenerator.writeStringField("Display Name", pers.getMiddleName());
		
		jsonGenerator.writeObjectFieldStart("Address"); 
			jsonGenerator.writeStringField("City", pers.getAddress().getCity());
			jsonGenerator.writeStringField("State", pers.getAddress().getState());
			jsonGenerator.writeNumberField("Pincode", pers.getAddress().getPincode());
		jsonGenerator.writeEndObject();
		jsonGenerator.flush();
		jsonGenerator.close();
	}

	
	

	private static void readobject() throws JsonParseException, IOException {
		JsonParser jsonParser = new JsonFactory().createParser(new File("modifiedobjectinput.json"));
		Person pers = new Person();
		Address address = new Address();
		pers.setAddress(address);
		parseJSON(jsonParser, pers);
		jsonParser.close();
		System.out.println(" * Person Object :  \n\n"+pers);
	}

	
	private static void writearray(JsonNode jsonNodes) throws IOException, IOException {
		
		//System.out.println("array");
		
		Iterator<JsonNode> ite = jsonNodes.elements();
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(new FileOutputStream("modifiedarrayoutput.json"));
		jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
		jsonGenerator.writeStartArray();
		jsonGenerator.writeStartObject(); 
		while(ite.hasNext()) {
			JsonNode person = ite.next();
			
		    String n = person.path("FirstName").asText();
		    String m = " ";
		    String p = person.path("LastName").asText();
		    n = n+m+p;
		    
		    //System.out.println(n);
		    
			jsonGenerator.writeStringField("Name", n);
			jsonGenerator.writeStringField("Display Name", person.path("MiddleName").asText());
			JsonNode Addressnode = person.path("Address");
			jsonGenerator.writeObjectFieldStart("Address"); 
				jsonGenerator.writeStringField("City", Addressnode.path("City").asText());
				jsonGenerator.writeStringField("State", Addressnode.path("State").asText());
				jsonGenerator.writeNumberField("Pincode", Addressnode.path("Pincode").asLong());
				jsonGenerator.writeEndObject();
		}
		jsonGenerator.flush();
		jsonGenerator.close();
      }
		
	
	


	private static void readarray(JsonNode jsonNodes) throws JsonGenerationException, IOException {
		// System.out.println("array");
      Iterator<JsonNode> ite = jsonNodes.elements();
		
		while (ite.hasNext()) {
			JsonNode person = ite.next();
			
			System.out.println("First Name :"+ person.path("FirstName").asText());
			System.out.println("Last Name : " + person.path("LastName").asText());
			System.out.println("Middle Name : " + person.path("MiddleName").asText());
			System.out.println("Address : " + person.path("Address").asText());
			
			JsonNode Addressnode = person.path("Address");
			  System.out.println("City : " +Addressnode.path("City").asText());
			  System.out.println("State : " +Addressnode.path("State").asText());
			  System.out.println("Pincode : " +Addressnode.path("Pincode").asLong());
		}	
		
	}
		
	
	
	

private static void parseJSON(JsonParser jsonParser, Person pers) throws JsonParseException, IOException {
	while(jsonParser.nextToken() != JsonToken.END_OBJECT){
		String name = jsonParser.getCurrentName();

		if("FirstName".equals(name)){
			jsonParser.nextToken();
			pers.setFirstName(jsonParser.getText());
		}else if("MiddleName".equals(name)) {
			jsonParser.nextToken();
			pers.setMiddleName(jsonParser.getText());
		}else if("LastName".equals(name)) {
			jsonParser.nextToken();
			pers.setLastName(jsonParser.getText());
		}else if("address".equals(name)){
			jsonParser.nextToken();
			//nested object, recursive call
			parseJSON(jsonParser, pers);
		}else if("State".equals(name)){
			jsonParser.nextToken();
			pers.getAddress().setState(jsonParser.getText());
		}else if("City".equals(name)){
			jsonParser.nextToken();
			pers.getAddress().setCity(jsonParser.getText());
		}else if("Pincode".equals(name)){
			jsonParser.nextToken();
			pers.getAddress().setPincode(jsonParser.getIntValue());
		}
	}
}
}