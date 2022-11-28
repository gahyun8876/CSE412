package application;

public class Food {
	private String RestaurantID;
	private String MenuID;
	private String FoodID;
	private String FoodName;
	private double Price;
	
	//constructor
	public Food() {
		this.RestaurantID = "?";
		this.MenuID = "?";
		this.FoodID = "?";
		this.FoodName = "?";
		this.Price = -1;
	}
	
	//getters
	public String getRestaurantID() {
		return this.RestaurantID;
	}
	
	public String getMenuID() {
		return this.MenuID;
	}
	
	public String getFoodID() {
		return this.FoodID;
	}
	
	public String getFoodName() {
		return this.FoodName;
	}
	
	public double getPrice() {
		return this.Price;
	}
	
	//setters
	public void setRestaurantID(String rid) {
		this.RestaurantID = rid;
	}
	
	public void setMenuID(String mid) {
		this.MenuID = mid;
	}
	
	public void setFoodID(String fid) {
		this.FoodID = fid;
	}
	
	public void setFoodName(String foodName) {
		this.FoodName = foodName;
	}
	
	public void setPrice(double price) {
		this.Price = price;
	}
	
	public String toString() {
		String foodInfo = this.FoodName + ", price:  $" + this.Price + "\n";
		return foodInfo;
	}
}
