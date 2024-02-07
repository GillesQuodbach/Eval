package fr.fms.entities;

public class Article {
	private int id;
	private String name;
	private String description;
	private int duration;
	private String format;
	private double price;
	private int category;
	private int quantity = 1;

	public static final int MAX_STRING_LENGTH = 20;

	public Article(int id, String name, String description, int duration, String format, double price, int category) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.duration = duration;
		this.format = format;
		this.price = price;
		this.category = category;
	}
	
	public Article(String name, String description, int duration, String format, double price, int category) {
		this.name = name;
		this.description = description;
		this.duration = duration;
		this.format = format;
		this.price = price;
		this.category = category;
	}

	public Article(int id, String name, String description, int duration, String format, double price){
		this.id = id;
		this.name = name;
		this.description = description;
		this.duration = duration;
		this.format = format;
		this.price = price;
	}

	public Article(String name, String description, int duration, String format, double price) {
		this.name = name;
		this.description = description;
		this.duration = duration;
		this.format = format;
		this.price = price;
	}

	@Override
	public String toString() {
		return centerString(String.valueOf(id)) + centerString(name) + centerString(description)
		+ centerString(String.valueOf(duration))+ centerString(format) +
				 centerString(String.valueOf(price));
	}

	public static String centerString(String str) {
		if (str.length() >= MAX_STRING_LENGTH)
			return str;
		String dest = "                    ";
		int deb = (MAX_STRING_LENGTH - str.length()) / 2;
		String data = new StringBuilder(dest).replace(deb, deb + str.length(), str).toString();
		return data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public static int getMaxStringLength() {
		return MAX_STRING_LENGTH;
	}

	
}
