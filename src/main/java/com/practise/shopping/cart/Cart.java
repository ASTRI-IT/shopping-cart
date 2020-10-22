package com.practise.shopping.cart;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="User_cart")
public class Cart {

	@Id
	@Column(name = "Cart_id")
	private int cartID;
	
	@Column(name = "Total_no_Items")
	private int numberOfProd;
	
	@Column(name = "Cart_creation_date")
	
	private String creationDate;
	
	@OneToOne
	
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "cart")
	private List<Product> prod;
		
	/**
	 * @return the cartID
	 */
	public int getCartID() {
		return cartID;
	}

	/**
	 * @param cartID the cartID to set
	 */
	public void setCartID(int cartID) {
		this.cartID = cartID;
	}

	/**
	 * @return the numberOfProd
	 */
	public int getNumberOfProd() {
		return numberOfProd;
	}

	/**
	 * @param numberOfProd the numberOfProd to set
	 */
	public void setNumberOfProd(int numberOfProd) {
		this.numberOfProd = numberOfProd;
	}

	/**
	 * @return the creationDate
	 */
	public String getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	public List<Product> getProd() {
		return prod;
	}

	public void setProd(List<Product> prod) {
		this.prod = prod;
	}




}
