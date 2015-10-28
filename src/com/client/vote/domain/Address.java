package com.client.vote.domain;



public class Address {
	String addressInfo;
	String username;
	String address1;
	String address2;
	String city;
	String state;
	String country;
	String pinCode;
	String addressId;
	public Address( String address1, String address2, String city, String state, String country, String pinCode,String addressId ){
		
		this.address1=address1;
		this.address2=address2;
		this.city=city;
		this.state=state;
		this.country=country;
		this.pinCode=pinCode;
		this.addressId=addressId;
	}
	public String getAddressInfo() {
		return addressInfo;
	}
	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	@Override
	public String toString() {
		return "Address [addressInfo=" + addressInfo + ", username=" + username
				+ ", address1=" + address1 + ", address2=" + address2
				+ ", city=" + city + ", state=" + state + ", country="
				+ country + ", pinCode=" + pinCode + ", addressId=" + addressId
				+ "]";
	}
	
	
	
}