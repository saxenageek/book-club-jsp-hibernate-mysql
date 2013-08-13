package com.google.library;

public class Books {
	private int id;
	private String b_name;
	private String b_author;
	private double b_price;
	
	public Books(){}
	
	public Books(String b_name, String b_author, double b_price) {
		super();
		this.b_name = b_name;
		this.b_author = b_author;
		this.b_price = b_price;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getB_name() {
		return b_name;
	}
	public void setB_name(String b_name) {
		this.b_name = b_name;
	}
	public String getB_author() {
		return b_author;
	}
	public void setB_author(String b_author) {
		this.b_author = b_author;
	}
	public double getB_price() {
		return b_price;
	}
	public void setB_price(double b_price) {
		this.b_price = b_price;
	}
}
