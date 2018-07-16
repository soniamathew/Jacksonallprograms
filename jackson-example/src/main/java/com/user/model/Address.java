package com.user.model;

public class Address {
	
	private String State;
	private String City;
	private int Pincode;
	
	public String getState() {
		return State;
	}
	public void setState(String State) {
		this.State = State;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String City) {
		this.City = City;
	}
	public int getPincode() {
		return Pincode;
	}
	public void setPincode(int Pincode) {
		this.Pincode = Pincode;
	}
	
	@Override
	public String toString(){
		return "\n"+" City : " + getCity()  +","+ "\n " +  "State : " +getState()+", "+"\n" +"Pincode: "+getPincode() +".";
	}
}
