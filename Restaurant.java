package application;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class Restaurant {
  private String RestaurantID;
  private String RestaurantName;
  private String Address;
  private double CustomerRating;
  private double AverageCost;
  private Review RestaurantReview;
  private ArrayList<Menu> MenuList;
  private String CurrentUser;	//the user who's doing the search

  // Constructor
  public Restaurant() {
	this.RestaurantID = "?";
    this.RestaurantName = "?";
    this.Address = "";
    this.CustomerRating = 0;
    this.RestaurantReview = new Review();
    this.MenuList = new ArrayList<Menu>();
    this.CurrentUser = "";
  }
  
  //getters
  public String getRestaurantID() {
	return RestaurantID;
  }
  
  public String getRestaurantName() {
    return RestaurantName;
  }

  public String getAddress() {
    return Address;
  }
  
  public double getCustomerRating() {
	return this.CustomerRating;
  }
  
  public double getAverageCost() {
	return this.AverageCost;
  }

  public Review getReview() {
    return RestaurantReview;
  }
  
  public ArrayList<Menu> getMenuList() {
	  return this.MenuList;
  }
  
  public String getCurrentUser() {
	  return this.CurrentUser;
  }

  //setters
  public void setRestaurantID(String id) {
	  RestaurantID = id;
  }
  
  public void setRestaurantName(String name) {
    RestaurantName = name;
  }

  public void setAddress(String address) {
    Address = address;
  }
  
  public void setCustomerRating(double cr) {
	this.CustomerRating = cr;
  }
  
  public void setAverageCost(double ac) {
	this.AverageCost = ac;
  }
  
  public void setMenuList(ArrayList<Menu> menuList) {
	this.MenuList = menuList;
  }
  
  public void setCurrentUser(String phoneNumber) {
	  this.CurrentUser = phoneNumber;
	  System.out.println("Phone number set: " + phoneNumber);
  }
  
  public void addMenu(Menu m) {
	this.MenuList.add(m);
  }

  public void addRating(double rate) {
	System.out.println("Adding rating from user: " + this.CurrentUser);
	this.RestaurantReview.setPhoneNumber(this.CurrentUser);
	this.RestaurantReview.setReviewRestaurant(this.RestaurantID);
    this.RestaurantReview.updateRating(rate);
    this.CustomerRating = this.RestaurantReview.getAverage();
  }
  
  public String toString() {
	DecimalFormat fmt = new DecimalFormat("0.00");
    String result = "\nRestaurant Name:\t\t" + this.RestaurantName
    		+ "\nRestaurant Address:\t\t" + this.Address
    		+ "\nCustomer Rating:\t\t" + fmt.format(this.CustomerRating)
    		+ "\nNumber of Reviews:\t\t" + Project.dbc.countRating(this.RestaurantID)
    		+ "\nAverage Cost:\t\t" + fmt.format(this.AverageCost)
    		+ "\n";
    
    for (int i = 0; i < this.MenuList.size(); i++) {
    	result += this.MenuList.get(i).toString();
    }
    
    result += "\n-------------------------\n";
    
    return result;
  }
}
