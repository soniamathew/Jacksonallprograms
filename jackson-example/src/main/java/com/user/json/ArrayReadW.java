package com.user.json;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ArrayReadW {
	public static void main(String[] args) throws JsonParseException, IOException {
		//JsonFactory jsonFactory = new JsonFactory();
		JsonParser jp = new JsonFactory().createParser(new File("arrayinput.json"));
		jp.setCodec(new ObjectMapper());
		JsonNode jsonNode = jp.readValueAsTree();
		//System.out.println("Checking array : " +  jsonNode.isArray());
		readJsonData(jsonNode);
		writeJsonData(jsonNode);
	}
	
	static void readJsonData(JsonNode jsonNode) {
		Iterator<JsonNode> ite = jsonNode.elements();
		
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
	
	static void writeJsonData(JsonNode jsonNode) throws IOException{
		Iterator<JsonNode> ite = jsonNode.elements();
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(new FileOutputStream("arrayoutput.json"));
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
    }