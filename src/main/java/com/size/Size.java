package com.size;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.category.Category;
import com.product.Product;

@Table(name="size")
@Entity
public class Size {
	@Column(name = "id", nullable = false, unique = true)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name="productid")
	private Product p;
	
	private String size;
	private int amount;
	public int getId() {
		return id;
	}
	
	public String getSize() {
		return size;
	}
	public int getAmount() {
		return amount;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Product getP() {
		return p;
	}

	public void setP(Product p) {
		this.p = p;
	}
	
	
	
	
}