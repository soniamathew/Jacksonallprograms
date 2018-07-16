package com.user.json;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.user.model.Address;
import com.user.model.Person;

public class Objectreaderwriter {

	public static void main(String[] args) throws JsonParseException, IOException {
		
		JsonParser jsonParser = new JsonFactory().createParser(new File("objectinput.json"));
	
		Person pers = new Person();
		Address address = new Address();
		pers.setAddress(address);
	
		parseJSON(jsonParser, pers);
		jsonParser.close();
		
		System.out.println(" * Person Object :  \n\n"+pers);
		
		
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(new FileOutputStream("objectoutput.json"));
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
