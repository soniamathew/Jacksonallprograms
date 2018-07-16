package com.user.json;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class Mapper1 {

	public static void main(String[] args) throws JsonProcessingException, IOException {
			
		// 
		    ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(new File("arrayinput.json"));
			String data = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
			System.out.println("*********** Input File input.json ************ \n"+ data);
			ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
			writer.writeValue(new File("arrayoutput.json"), data);
			
			
	}

}
