package org.itstep.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Product {
	public final SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy");

	private Long id;
	private String category;
	private String name;
	private Long price;
	private Integer amount;
	private Date date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "[" + id + "] "
				+ category + " / "
				+ name + ", $"
				+ (price/100.0) + ", "
				+ amount + " шт., "
				+ FORMAT.format(date);
	}
}
