package application;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseController {
	Connection c;
	Statement stmt;
	private ArrayList<Restaurant> RestaurantList;

	// constructor
	public DatabaseController(ArrayList<Restaurant> list) {
		c = null;
		stmt = null;
		RestaurantList = list;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
					.getConnection("jdbc:postgresql://localhost:5432/proj",
							"postgres", "0816");
			// test the connection
			String sql = "SELECT * FROM restaurant;";
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("Try sql: " + sql);
			while (rs.next()) {
				String restaurantName = rs.getString("restaurant_name");
				System.out.println(restaurantName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}

	public int countRating(String rid) {
		ResultSet rs;
		int count = 0;
		try {
			stmt = c.createStatement();
			String sql = "SELECT COUNT(DISTINCT phone_number) FROM rating WHERE rating.restaurant_id = \'" + rid
					+ "\';";
			System.out.println("Count rating: " + sql);
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				count = Integer.parseInt(rs.getString("count"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return count;
	}

	public double getAverageRating(String rid) {
		ResultSet rs;
		double rating = 0;
		try {
			stmt = c.createStatement();
			String sql = "SELECT AVG(score) FROM rating WHERE rating.restaurant_id = \'" + rid + "\';";
			System.out.println("get average sql: " + sql);
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				rating = Double.parseDouble(rs.getString("avg"));
				System.out.println("AVG: " + rating);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return rating;
	}

	public boolean userExist(String phoneNumber) {
		ResultSet rs;
		boolean exist = false;
		try {
			stmt = c.createStatement();
			String sql = "SELECT * FROM users WHERE users.phone_number = \'" + phoneNumber + "\';";
			System.out.println("uesr exists: " + sql);
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				System.out.println("User exists.");
				exist = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return exist;
	}

	public boolean ratingExist(String phoneNumber, String rid) {
		ResultSet rs;
		boolean exist = false;
		try {
			stmt = c.createStatement();
			String sql = "SELECT * FROM rating WHERE rating.phone_number = \'" + phoneNumber
					+ "\' AND rating.restaurant_id = \'" + rid + "\';";
			System.out.println("rating exists: " + sql);
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				System.out.println("Rating exists.");
				exist = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return exist;
	}

	public void addUser(String phoneNumber) {
		try {
			stmt = c.createStatement();
			String sql = "INSERT INTO users(phone_number) VALUES(\'" + phoneNumber + "\');";
			System.out.println("add new user: " + sql);
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	public void addRating(String phoneNumber, String rid, double score) {
		try {
			stmt = c.createStatement();
			String sql = "INSERT INTO rating(phone_number, restaurant_id, score) "
					+ "VALUES(\'" + phoneNumber + "\', \'" + rid + "\', " + score + ");";
			System.out.println("add new rating: " + sql);
			stmt.executeUpdate(sql);
			sql = "WITH subquery AS "
					+ "(SELECT AVG(score) AS average FROM rating "
					+ "INNER JOIN restaurant ON rating.restaurant_id = restaurant.restaurant_id "
					+ "WHERE rating.restaurant_id = \'" + rid + "\')"
					+ "UPDATE restaurant SET customer_rating = subquery.average FROM subquery "
					+ "WHERE restaurant_id = \'" + rid + "\';";
			System.out.println("rating update: " + sql);
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	public void deleteRating(String phoneNumber, String rid) {
		try {
			stmt = c.createStatement();
			String sql = "DELETE FROM rating WHERE phone_number = \'" + phoneNumber + "\' AND restaurant_id = \'" + rid
					+ "\';";
			System.out.println("delete rating: " + sql);
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	public ArrayList<Restaurant> search(String restaurantName, String addr, String foodType, double rating, double cost,
			String foodName, String currentUser) {
		ResultSet rs;
		try {
			// Search by conditions
			stmt = c.createStatement();
			String sql = "SELECT DISTINCT restaurant.restaurant_id, restaurant.restaurant_name, restaurant.address, restaurant.average_cost, restaurant.customer_rating "
					+ "FROM restaurant INNER JOIN (menu INNER JOIN food ON menu.menu_id = food.menu_id AND menu.restaurant_id = food.restaurant_id) "
					+ "ON restaurant.restaurant_id = menu.restaurant_id "
					+ "AND restaurant.restaurant_id = food.restaurant_id AND menu.menu_id = food.menu_id "
					+ "WHERE restaurant.restaurant_name LIKE \'%" + restaurantName + "%\' "
					+ "AND restaurant.address LIKE \'%" + addr + "%\' "
					+ "AND menu.food_type LIKE \'%" + foodType + "%\'"
					+ "AND restaurant.customer_rating > " + rating + " "
					+ "AND restaurant.average_cost < " + cost + " "
					+ "AND food.food_name LIKE \'%" + foodName + "%\';";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);

			// add restaurants to the RestaurantList
			while (rs.next()) {
				System.out.println("into while");
				Restaurant r = new Restaurant();
				String rid = rs.getString("restaurant_id");
				r.setRestaurantID(rid);
				r.setRestaurantName(rs.getString("restaurant_name"));
				r.setAddress(rs.getString("address"));
				r.setCustomerRating(Double.parseDouble(rs.getString("customer_rating")));
				r.setAverageCost(Double.parseDouble(rs.getString("average_cost")));
				r.setCurrentUser(currentUser);

				// get the menus from the restaurant
				stmt = c.createStatement();
				sql = "SELECT * FROM menu WHERE menu.restaurant_id = \'" + rid + "\';";
				System.out.println(sql);
				ResultSet temp1 = stmt.executeQuery(sql);

				// add menus to the restaurant
				while (temp1.next()) {
					Menu m = new Menu();
					m.setRestaurantID(rid);
					String mid = temp1.getString("menu_id");
					m.setMenuID(mid);
					m.setFoodType(temp1.getString("food_type"));

					// get foods from the menu
					stmt = c.createStatement();
					sql = "SELECT * FROM food WHERE food.restaurant_id = \'" + rid + "\' AND food.menu_id = \'" + mid
							+ "\';";
					System.out.println(sql);
					ResultSet temp2 = stmt.executeQuery(sql);

					// add foods to the menu
					while (temp2.next()) {
						Food f = new Food();
						f.setRestaurantID(rid);
						f.setMenuID(mid);
						f.setFoodID(temp2.getString("food_id"));
						f.setFoodName(temp2.getString("food_name"));
						f.setPrice(Double.parseDouble(temp2.getString("price")));
						m.addFood(f);
					}

					r.addMenu(m);
				}

				RestaurantList.add(r);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return RestaurantList;
	}

}