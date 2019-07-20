package com.construction.simulator.bean;

public class CostPerQuantity {
	
	private int quantity;
	private int cost;
	
	
	public CostPerQuantity(int quantity, int cost) {
		super();
		this.quantity = quantity;
		this.cost = cost;
	}
	
	public CostPerQuantity(int cost) {
		super();
		this.cost = cost;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}


}
