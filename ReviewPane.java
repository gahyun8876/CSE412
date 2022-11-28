package application;

import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import java.util.ArrayList;
import javafx.scene.layout.HBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.geometry.Insets;

public class ReviewPane extends VBox {
  private ArrayList<Restaurant> RestaurantList;
  private ListView<Restaurant> RestaurantListView;
  private ObservableList<Restaurant> convArrList;

  ToggleGroup ratingGrp;
  RadioButton poor, fair, average, good, wonderful;
  Button submit;
  int selectedIndex;

  //constructor
  public ReviewPane(ArrayList<Restaurant> list, DatabaseController dbc) {
    //initialize variables
    this.RestaurantList = list;
    this.selectedIndex = -1;

    this.ratingGrp = new ToggleGroup();
    
    this.poor = new RadioButton("1 Poor");
    this.poor.setToggleGroup(ratingGrp);

    this.fair = new RadioButton("2 Fair");
    this.fair.setToggleGroup(ratingGrp);

    this.average = new RadioButton("3 Average");
    this.average.setToggleGroup(ratingGrp);

    this.good = new RadioButton("4 Good");
    this.good.setToggleGroup(ratingGrp);

    this.wonderful = new RadioButton("5 Wonderful");
    this.wonderful.setToggleGroup(ratingGrp);

    submit = new Button("Submit Review");

    //set up the layout
    convArrList = FXCollections.observableArrayList(RestaurantList);
    RestaurantListView = new ListView<Restaurant>(convArrList);
    
    VBox listPane = new VBox(RestaurantListView);
    listPane.setMaxSize(700, 270);

    HBox reviewPane = new HBox();
    reviewPane.setAlignment(Pos.CENTER);
    reviewPane.setSpacing(10);
    reviewPane.getChildren().addAll(poor, fair, average, good, wonderful);

    StackPane btnPane = new StackPane();
    btnPane.setPadding(new Insets(10, 0, 10, 0));
    btnPane.getChildren().add(submit);

    //Set up the pane
    this.setSpacing(10);
    this.setMinSize(700, 400);
    this.getChildren().addAll(listPane, reviewPane, btnPane);

    //Register the button with handler class
    RatingHandler rHandler = new RatingHandler();
    submit.setOnAction(rHandler);
    RestaurantListView.setOnMouseClicked(new RestaurantListHandler());

  }

  public void updateRestaurantList(Restaurant newRestaurant) {
    convArrList.add(newRestaurant);
    System.out.println("done");
  }
  
  //clear pane when start a new search
  public void clearPane() {
	  RestaurantListView.getItems().clear();
  }
  
  //empty the ArrayList of restaurant when start a new search
  public void resetRestaurantList() {
	  RestaurantList.clear();
  }

  private class RatingHandler implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      RadioButton selectedRadioBtn = (RadioButton) ratingGrp.getSelectedToggle();
      //if the user didn't select a score, then they cannot rate
      if (selectedRadioBtn == null) {
    	  System.out.println("Please select a score!");
    	  return;
      }
      System.out.println("Score selected.");
      double radioBtnVal = Double.parseDouble(selectedRadioBtn.getText().substring(0, 1));
      
      //if the user didn't select a restaurant, then they cannot rate
      if (ratingGrp.getSelectedToggle() != null && selectedIndex >= 0) {
        RestaurantList.get(selectedIndex).addRating(radioBtnVal);
        convArrList.set(selectedIndex, RestaurantList.get(selectedIndex));
        selectedIndex = -1;
      } else {
    	System.out.println("Please select a restaurant.");
      }
    }
  }

  private class RestaurantListHandler implements EventHandler<MouseEvent> {
    public void handle(MouseEvent e) {
      selectedIndex = RestaurantListView.getSelectionModel().getSelectedIndex();
    }
  }
}
