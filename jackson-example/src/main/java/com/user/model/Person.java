package com.user.model;
import com.user.model.Address;

public class Person {
	    private String FirstName;
	    private String MiddleName;
	    private String LastName;
	    private Address address;
	    
	    public String getFirstName()
	    {
	        return FirstName;
	    }
	    public void setFirstName(String FirstName)
	    {
	        this.FirstName = FirstName;
	    }
	    
	    public String getLastName()
	    {
	        return LastName;
	    }
	    public void setLastName(String LastName)
	    {
	        this.LastName = LastName;
	    }
	    
	    public String getMiddleName()
	    {
	        return MiddleName;
	    }
	    public void setMiddleName(String MiddleName)
	    {
	        	this.MiddleName = MiddleName;
	    } 
	    
	    
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("***** Person Details *****\n\n");
		sb.append("First Name : "+getFirstName()+"\n");
		sb.append("Middle Name : "+getMiddleName()+"\n");
		sb.append("Last Name : "+getLastName()+"\n");
		sb.append("Address : "+getAddress()+"\n");
		return sb.toString();
	}
}
