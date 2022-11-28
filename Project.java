package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;

public class Project extends Application {
  private TabPane tabPane;
  private SearchPane SearchPane;
  private ReviewPane reviewPane;
  private ArrayList<Restaurant> RestaurantList;
  public static DatabaseController dbc;

  public void start(Stage stage) {
    StackPane root = new StackPane();
    
    RestaurantList = new ArrayList<Restaurant>();
    dbc = new DatabaseController(RestaurantList);
    
    reviewPane = new ReviewPane(RestaurantList, dbc);
    SearchPane = new SearchPane(RestaurantList, reviewPane, dbc);

    tabPane = new TabPane();

    Tab tab1 = new Tab();
    tab1.setText("Restaurant Search");
    tab1.setContent(SearchPane);

    Tab tab2 = new Tab();
    tab2.setText("Restaurant Review");
    tab2.setContent(reviewPane);

    tabPane.getSelectionModel().select(0);
    tabPane.getTabs().addAll(tab1, tab2);

    root.getChildren().add(tabPane);

    Scene scene = new Scene(root, 700, 400);
    
    stage.setTitle("Restaurant Search & Review Apps");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
