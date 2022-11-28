package application;

public class Review {
  private String restaurantID;
  private String phoneNumber;
  private double average;	//updated average

  //constructor
  public Review() {
	this.restaurantID = "";
	this.phoneNumber = "";
    this.average = 0.0;
  }
  
  //getter
  public double getAverage() {
	return this.average;
  }
  
  //setters
  public void setPhoneNumber(String pn) {
	this.phoneNumber = pn;
  }
  
  public void setReviewRestaurant(String rid) {
	this.restaurantID = rid;
  }
  
  public void updateRating(double rating) {
	//the user cannot rate without entering a phone number
	if (this.phoneNumber.trim().isEmpty()) {
		System.out.println("Rating update failed, no phone number.");
		this.average = Project.dbc.getAverageRating(this.restaurantID);
		return;
	}
	
	//if the user doesn't exist in the users list, add them to the list
	if (!Project.dbc.userExist(this.phoneNumber)) {
		Project.dbc.addUser(this.phoneNumber);
	}
	
	//if the user already rated the selected restaurant, delete the old rating first
	if (Project.dbc.ratingExist(this.phoneNumber, this.restaurantID)) {
		Project.dbc.deleteRating(this.phoneNumber, this.restaurantID);
	}
	Project.dbc.addRating(this.phoneNumber, this.restaurantID, rating);
    
    this.average = Project.dbc.getAverageRating(this.restaurantID);
  }

  public String toString() {
    String reviewInfo = "User phone number: " + this.phoneNumber + ", Restaurant ID: " +  this.restaurantID;
    return reviewInfo;
  }
}
