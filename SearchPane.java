package application;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SearchPane extends HBox {
  private ArrayList<Restaurant> RestaurantList;
  private ReviewPane reviewPane;
  private DatabaseController dbc;
  
  Label msg;
  Label restaurantName, address, foodType, rating, cost, number, foodName;
  TextField restaurantNameField, addressField, foodTypeField, ratingField, costField, numberField, foodNameField;
  TextArea RestaurantField;		//display search result
  Button btn;
  
  //constructor
  public SearchPane(ArrayList<Restaurant> list, ReviewPane rePane, DatabaseController dbc) {
	//initialize variables
    this.RestaurantList = list;
    this.reviewPane = rePane;
    this.dbc = dbc;
    
    msg = new Label("Phone number is necessary for rating a restaurant!");
    msg.setTextFill(Color.RED);

    restaurantName = new Label("Restaurant Name");
    address = new Label("Address");
    foodType = new Label("Food Type");
    rating = new Label("Rating greater than");
    cost = new Label("Cost lower than");
    foodName = new Label("Food Name");
    number = new Label("Phone Number");

    restaurantNameField = new TextField();
    addressField = new TextField();
    foodTypeField = new TextField();
    ratingField = new TextField();
    costField = new TextField();
    foodNameField = new TextField();
    numberField = new TextField();

    btn = new Button("Search a Restaurant");

    GridPane leftPane = new GridPane();
    leftPane.setPrefSize(400, 400);
    leftPane.setAlignment(Pos.TOP_CENTER);
    leftPane.setPadding(new Insets(30, 10, 10, 10));
    leftPane.setHgap(10);
    leftPane.setVgap(10);

    StackPane btnPane = new StackPane();
    btnPane.getChildren().add(btn);

    //set up the left half for the pane
    leftPane.add(msg, 0, 0, 4, 1);
    leftPane.add(restaurantName, 0, 1);
    leftPane.add(restaurantNameField, 1, 1);
    leftPane.add(address, 0, 2);
    leftPane.add(addressField, 1, 2);
    leftPane.add(foodType, 0, 3);
    leftPane.add(foodTypeField, 1, 3);
    leftPane.add(rating, 0, 4);
    leftPane.add(ratingField, 1, 4);
    leftPane.add(cost, 0, 5);
    leftPane.add(costField, 1, 5);
    leftPane.add(foodName, 0, 6);
    leftPane.add(foodNameField, 1, 6);
    leftPane.add(number, 0, 7);
    leftPane.add(numberField, 1, 7);
    leftPane.add(btnPane, 0, 8, 2, 1);

    //set up the right half of the pane
    RestaurantField = new TextArea("No Restaurant");
    RestaurantField.setPrefWidth(400);

    //set up the pane
    this.getChildren().addAll(leftPane, RestaurantField);

    //register button with event handler
    ButtonHandler btnHandler = new ButtonHandler();
    btn.setOnAction(btnHandler);
  }
  
  private class ButtonHandler implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      String restaurantName, address, foodType, foodName, number;
      double rating, cost;
      String RestaurantListStr = "";
      
	  try {
		  reviewPane.resetRestaurantList();
		  reviewPane.clearPane();
		  
	      restaurantName = restaurantNameField.getText();
	      address = addressField.getText();
	      foodType = foodTypeField.getText();
	      number = numberField.getText();
	      System.out.println("Current user phone number: " + number);
	      
	      if (ratingField.getText().trim().isEmpty()) {
	    	  rating = -1;
	      } else {
	    	  rating = Double.parseDouble(ratingField.getText());
	      }
	      
	      if (costField.getText().trim().isEmpty()) {
	    	  cost = Integer.MAX_VALUE;
	      } else {
	    	  cost = Double.parseDouble(costField.getText());
	      }
	      
	      foodName = foodNameField.getText();
	      
	      RestaurantList = dbc.search(restaurantName, address, foodType, rating, cost, foodName, number);
	      
	      for (int i = 0; i < RestaurantList.size(); i++) {
	        RestaurantListStr += RestaurantList.get(i).toString();
	    	reviewPane.updateRestaurantList(RestaurantList.get(i));
	      }
	      
	      if (RestaurantListStr.trim().isEmpty()) {
	    	  msg.setText("Restaurant not found");
	    	  RestaurantField.setText("No Restaurant");
	      } else {
	    	  RestaurantField.setText(RestaurantListStr);
	    	  msg.setText("Restaurant found");
	      }
	  } catch (NumberFormatException e) {
	      msg.setText("Incorrect data format");
	  }
    }
  }
}
