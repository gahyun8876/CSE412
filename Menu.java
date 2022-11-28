package application;

import java.util.ArrayList;

public class Menu {
	private String RestaurantID;
	private String MenuID;
	private String FoodType;
	private ArrayList<Food> FoodList;
	
	//constructor
	public Menu() {
		this.RestaurantID = "?";
		this.MenuID = "?";
		this.FoodType = "?";
		this.FoodList = new ArrayList<Food>();
	}
	
	//getters
	public String getRestaurantID() {
		return this.RestaurantID;
	}
	
	public String getMenuID() {
		return this.MenuID;
	}
	
	public String getFoodType() {
		return this.FoodType;
	}
	
	public ArrayList<Food> getFoodList() {
		return this.FoodList;
	}
	
	//setters
	public void setRestaurantID(String rid) {
		this.RestaurantID = rid;
	}
	
	public void setMenuID(String mid) {
		this.MenuID = mid;
	}
	
	public void setFoodType(String foodType) {
		this.FoodType = foodType;
	}
	
	public void setFoodList(ArrayList<Food> foodList) {
		this.FoodList = foodList;
	}
	
	public void addFood(Food f) {
		this.FoodList.add(f);
	}
	
	public String toString() {
		String menuStr = "\n" + this.FoodType + ":\n";
		for (int i = 0; i <  this.FoodList.size(); i++) {
			menuStr += this.FoodList.get(i).toString();
		}
		return menuStr;
	}
}
