package org.itstep.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Product extends Entity {
	public static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy");

	private Category category;
	private String name;
	private Long price;
	private Integer amount;
	private Date date;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
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
		return category + " / "
				+ "[" + getId() + "] "
				+ name + ", $"
				+ (price/100.0) + ", "
				+ amount + " шт., "
				+ FORMAT.format(date);
	}
}
