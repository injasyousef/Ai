package application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
	static ArrayList<City> cities = new ArrayList<City>();
	static HashMap<String, City> citiesMap = new HashMap<>();
	Pane root = new Pane();
	City source;
	City destination;
	ChoiceBox<String> sourceBox = new ChoiceBox<String>();
	ChoiceBox<String> destinationBox = new ChoiceBox<String>();
	Group lines = new Group();

	@Override
	public void start(Stage arg0) throws Exception {
		Stage primaryStage = new Stage();

		Scene scene = new Scene(root, 1080, 720);
		primaryStage.setTitle("the main menu");// set stage title
		root.setStyle("-fx-background-color: 			#778899	;\r\n");// set the pane color using css
		initialize();

		sourceBox.setTranslateX(400);
		sourceBox.setTranslateY(150);
		sourceBox.setPrefSize(150, 50);
		sourceBox.getItems().add("source");
		sourceBox.setValue("source");
		for (int i = 0; i < cities.size(); i++) {// add all countries in the Choose boxes
			sourceBox.getItems().add(cities.get(i).getName());
		}
		sourceBox.setOnAction(e -> {
			if (sourceBox.getValue().compareTo("source") != 0) {// the default value is not a country
				source = citiesMap.get(sourceBox.getValue());
				source.getButton()
						.setStyle("-fx-background-color: #000000;\r\n" + "        -fx-background-radius:100;\r\n");// if
																													// pressed
																													// color
																													// it
																													// by
																													// black
			}
		});

		destinationBox.setTranslateX(600);
		destinationBox.setTranslateY(150);
		destinationBox.setPrefSize(150, 50);
		destinationBox.getItems().add("destination");
		destinationBox.setValue("destination");
		for (int i = 0; i < cities.size(); i++) {// add all countries in the Choose boxes
			destinationBox.getItems().add(cities.get(i).getName());
		}
		destinationBox.setOnAction(e -> {
			if (destinationBox.getValue().compareTo("destination") != 0) {// the default value is not a country
				destination = citiesMap.get(destinationBox.getValue());
				destination.getButton()
						.setStyle("-fx-background-color: #000000;\r\n" + "        -fx-background-radius:100;\r\n");
			}
		});
		ToggleGroup toggleGroup = new ToggleGroup();
		RadioButton bfs = new RadioButton("BFS");
		bfs.setMinWidth(50);
		bfs.setMinHeight(50);
		bfs.setTextFill(Color.DARKBLUE);
		bfs.setFont(new Font("Arial", 20));
		bfs.setTranslateX(450);
		bfs.setTranslateY(50);
		bfs.setToggleGroup(toggleGroup);

		RadioButton aStar = new RadioButton("A*");
		aStar.setMinWidth(50);
		aStar.setMinHeight(50);
		aStar.setTextFill(Color.DARKBLUE);
		aStar.setFont(new Font("Arial", 20));
		aStar.setTranslateX(450);
		aStar.setTranslateY(100);
		aStar.setToggleGroup(toggleGroup);

		Label lb = new Label("Path--->");
		lb.setFont(new Font("Arial", 25));
		lb.setTextFill(Color.DARKBLUE);
		lb.setTranslateX(400);
		lb.setTranslateY(300);
		root.getChildren().add(lb);

		TextArea pathArea = new TextArea();// to show the path on it
		pathArea.setFont(new Font("Arial", 20));
		pathArea.setTranslateX(400);
		pathArea.setTranslateY(330);
		pathArea.setMinSize(350, 270);
		pathArea.setMaxSize(350, 270);
		pathArea.setEditable(false);

		Label lb1 = new Label("total cost--->");
		lb1.setFont(new Font("Arial", 25));
		lb1.setTextFill(Color.DARKBLUE);
		lb1.setTranslateX(400);
		lb1.setTranslateY(620);
		root.getChildren().add(lb1);

		TextField t1 = new TextField();// for total distance
		t1.setTranslateX(400);
		t1.setTranslateY(650);
		t1.setPrefSize(280, 50);
		t1.setFont(new Font("Arial", 25));
		t1.setStyle("-fx-background-color: #eeffff;\r\n" + "        -fx-background-radius:100;\r\n");
		root.getChildren().add(t1);

		Image myimage1 = new Image(new FileInputStream("path.png"));
		ImageView myview1 = new ImageView(myimage1);
		myview1.setFitHeight(60);
		myview1.setFitWidth(60);
		Button run = new Button("Run", myview1);// new button
		run.setMinWidth(160);
		run.setMinHeight(80);
		run.setTextFill(Color.DARKBLUE);
		run.setFont(new Font("Arial", 30));
		run.setTranslateX(400);
		run.setTranslateY(210);
		// set button colo and radius using css
		run.setStyle("-fx-background-color: #3cb371;\r\n" + "        -fx-background-radius:100;\r\n");
		run.setOnAction(e -> {
			if (source != destination && source != null && destination != null) {
				// if() {

				if (bfs.isSelected() == true || aStar.isSelected() == true) {
					if (root.getChildren().contains(lines) == false) {

						if (bfs.isSelected()) {
							LinkedList<City> path = new LinkedList<>();
							path = BFS(source, destination);
							if(path!=null) {
							double totalDistance = 0;
							
							
							for (int i = 0; i < path.size() - 1; i++) {// to draw the lines (number of lines =number of
																		// counties in the path -1)
								Line line = new Line();
								if (i != 0)// this to make all buttons in the path colored by blue expect first and last
											// one
									if (path.get(i).getName().compareTo(source.getName()) != 0) {
										path.get(i).getButton().setStyle("-fx-background-color: #0000FF;\r\n"
												+ "        -fx-background-radius:100;\r\n");
									}
								line.setStartX(path.get(i).getXCoordinate() + 55);
								line.setStartY(path.get(i).getYCoordinate() + 30);
								line.setEndX(path.get(i + 1).getXCoordinate() + 55);
								line.setEndY(path.get(i + 1).getYCoordinate() + 30);
								line.setFill(Color.BLACK);
								line.setStroke(Color.RED);
								line.setStrokeWidth(2);
								lines.getChildren().add(line);

							}
							lines.setVisible(true);
							root.getChildren().add(lines);
							String pathString = "";
							pathString = path.get(0).getName();
							for (int i = 1; i < path.size(); i++) {
								double distance = findDistanceBetween(path.get(i - 1), path.get(i));
								totalDistance = totalDistance + distance;
								if (i != path.size() - 1) {
									pathString = pathString + "---->" + path.get(i).getName() + " " + distance + "KM \n"
											+ path.get(i).getName();
								} else {
									pathString = pathString + "---->" + path.get(i).getName() + " " + distance + "KM ";
								}
							}
							pathArea.setText(pathString);
							t1.setText(totalDistance + "KM");

							}
							else {
								Alert errorAlert = new Alert(AlertType.INFORMATION);
								errorAlert.setHeaderText("no Path");
								errorAlert.setContentText("no path between "+source.getName()+" and "+ destination.getName());
								errorAlert.showAndWait();
							}
						} else if (aStar.isSelected()) {
							List<City> path = new LinkedList<>();
							path = AStar(source, destination);
							if(path!=null) {
							double totalDistance = 0;
							System.out.println(path.toString());
							for (int i = 0; i < path.size() - 1; i++) {// to draw the lines (number of lines =number of
																		// counties in the path -1)
								Line line = new Line();
								if (i != 0)// this to make all buttons in the path colored by blue expect first and last
											// one
									System.out.println("sadsa");
								if (path.get(i).getName().compareTo(source.getName()) != 0) {
									path.get(i).getButton().setStyle("-fx-background-color: #0000FF;\r\n"
											+ "        -fx-background-radius:100;\r\n");
								}
								line.setStartX(path.get(i).getXCoordinate() + 55);
								line.setStartY(path.get(i).getYCoordinate() + 30);
								line.setEndX(path.get(i + 1).getXCoordinate() + 55);
								line.setEndY(path.get(i + 1).getYCoordinate() + 30);
								line.setFill(Color.BLACK);
								line.setStroke(Color.RED);
								line.setStrokeWidth(2);
								lines.getChildren().add(line);

							}
							lines.setVisible(true);
							root.getChildren().add(lines);
							String pathString = "";
							pathString = path.get(0).getName();
							for (int i = 1; i < path.size(); i++) {
								double distance = findDistanceBetween(path.get(i - 1), path.get(i));
								totalDistance = totalDistance + distance;
								if (i != path.size() - 1) {
									pathString = pathString + "---->" + path.get(i).getName() + " " + distance + "KM \n"
											+ path.get(i).getName();
								} else {
									pathString = pathString + "---->" + path.get(i).getName() + " " + distance + "KM ";
								}
							}
							pathArea.setText(pathString);
							t1.setText(totalDistance + "KM");
							}
							else {
								Alert errorAlert = new Alert(AlertType.INFORMATION);
								errorAlert.setHeaderText("no Path");
								errorAlert.setContentText("no path between "+source.getName()+" and "+ destination.getName());
								errorAlert.showAndWait();
							}
						}
					} else {
						Alert errorAlert = new Alert(AlertType.ERROR);
						errorAlert.setHeaderText("Reset please");
						errorAlert.setContentText("you must reset before new run");
						errorAlert.showAndWait();
					}
				} else {
					Alert errorAlert = new Alert(AlertType.WARNING);
					errorAlert.setHeaderText("choose BFS or A*");
					errorAlert.setContentText("please choose the algorithm you wnat BFS or A*");
					errorAlert.showAndWait();
				}
			} else if (source == null || destination == null) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("choose source and destination");
				errorAlert.setContentText("the source city or  destination city or both is null");
				errorAlert.showAndWait();
				source = null;
				destination = null;
				sourceBox.setValue("source");
				destinationBox.setValue("destination");
				for (int i = 0; i < cities.size(); i++) {
					cities.get(i).button
							.setStyle("-fx-background-color: #FF0000;\r\n" + "        -fx-background-radius:100;\r\n");
				}
			}
			// }
			else {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Same city");
				errorAlert.setContentText("the source city and destination city are the same");
				errorAlert.showAndWait();
				source = null;
				destination = null;
				sourceBox.setValue("source");
				destinationBox.setValue("destination");
				for (int i = 0; i < cities.size(); i++) {
					cities.get(i).button
							.setStyle("-fx-background-color: #FF0000;\r\n" + "        -fx-background-radius:100;\r\n");
				}
			}

		});
		Image myimage3 = new Image(new FileInputStream("reset.png"));// add the image
		ImageView myview3 = new ImageView(myimage3);// show
		// set the size
		myview3.setFitHeight(50);
		myview3.setFitWidth(50);
		Button reset = new Button("reset   ", myview3);// create a button and add the image
		// set size and position
		reset.setTranslateX(600);
		reset.setTranslateY(210);
		reset.setMinWidth(160);
		reset.setMinHeight(80);
		reset.setMaxWidth(150);
		reset.setMaxHeight(60);
		// set the button color and radius using css
		reset.setStyle("-fx-background-color: #FFFFFF;\r\n" + "        -fx-background-radius:100;\r\n");
		reset.setTextFill(Color.DARKBLUE);// set text color
		reset.setFont(new Font("Arial", 20));// set font type
		reset.setOnAction(e -> {
			source = null;
			destination = null;
			lines.setVisible(false);
			lines.getChildren().clear();
			root.getChildren().remove(lines);
			sourceBox.setValue("source");
			bfs.setSelected(false);
			aStar.setSelected(false);
			destinationBox.setValue("destination");
			pathArea.setText("");
			t1.setText("");
			// pathArea.setText("");
			// nullCountryError.setVisible(false);
			// sameCountryError.setVisible(false);
			// t1.setText("");
			// dest.setValue("destination");
			for (int i = 0; i < cities.size(); i++) {
				cities.get(i).button
						.setStyle("-fx-background-color: #FF0000;\r\n" + "        -fx-background-radius:100;\r\n");
			}
		});

		root.getChildren().addAll(sourceBox, destinationBox, run, reset, bfs, aStar, pathArea);// add the controls to
																								// the pane
		primaryStage.setScene(scene);// set the scene
		primaryStage.show();

	}

	public static void main(String[] args) throws IOException {

		readCitiesFile();
		readEdgesFile();
		readAirDistancesFile();
		launch(args);

	}

	public static void readCitiesFile() throws NumberFormatException, IOException {
		String line = null;// to read each line

		BufferedReader reader = new BufferedReader(new FileReader("cities.csv"));
		while ((line = reader.readLine()) != null) {

			String[] strN = line.split(",");

			City newCity = new City(strN[0], Double.parseDouble(strN[1]), Double.parseDouble(strN[2]));
			cities.add(newCity);// add it to the array list
			citiesMap.putIfAbsent(strN[0], newCity);// add it to the hash map

		}

	}

	public static void readEdgesFile() throws NumberFormatException, IOException {
		String line = null;
		BufferedReader reader = new BufferedReader(new FileReader("roads.csv"));

		while ((line = reader.readLine()) != null) {
			String[] strN = line.split(",");
			City src = citiesMap.get(strN[0]);
			City dest = citiesMap.get(strN[1]);
			src.addEdge(dest, Double.parseDouble(strN[2]));
			dest.addEdge(src, Double.parseDouble(strN[2]));
		}

	}

	public static void readAirDistancesFile() throws NumberFormatException, IOException {
		String line = null;// to read each line
		BufferedReader reader = new BufferedReader(new FileReader("AirDistance.csv"));

		while ((line = reader.readLine()) != null) {
			String[] strN = line.split(",");
			City src = citiesMap.get(strN[0]);
			City dest = citiesMap.get(strN[1]);
			src.addAirDistance(dest, Double.parseDouble(strN[2]));
			// System.out.println(dest.getName() + " " + strN[2]);

		}

	}

	public double findDistanceBetween(City src, City dest) {
		for (int i = 0; i < src.getEdgesList().size(); i++) {
			if (dest.getName().compareTo(src.getEdgesList().get(i).getDestination().getName()) == 0) {
				return src.getEdgesList().get(i).getDistance();
			}
		}
		return 0;
	}

	public void initialize() {// this will be run one time at the bagging of the program
		Image image1 = new Image("palestine_map.png");
		ImageView imageView1 = new ImageView(image1);
		imageView1.setTranslateX(5);
		imageView1.setTranslateY(30);
		imageView1.setFitHeight(658);
		imageView1.setFitWidth(374);
		imageView1.setVisible(true);
		root.getChildren().add(imageView1);
		for (int i = 0; i < cities.size(); i++) {// for all countries
			Button b = new Button();

			b.setTranslateX(cities.get(i).getXCoordinate() + 50);
			b.setTranslateY(cities.get(i).getYCoordinate() + 25);
			b.setMinWidth(10);
			b.setMinHeight(10);
			b.setMaxWidth(10);
			b.setMaxHeight(10);
			// set the button color and radius using css
			b.setStyle("-fx-background-color: #FF0000;\r\n" + "        -fx-background-radius:100;\r\n");
			cities.get(i).setButton(b);
			b.setUserData(cities.get(i));
			b.setOnAction(event -> {

				if (source == null) {
					b.setStyle("-fx-background-color: #000000;\r\n" + "        -fx-background-radius:100;\r\n");// if
																												// pressed
																												// color
																												// it by
																												// black
					source = (City) b.getUserData();
					sourceBox.setValue(source.getName());

				} else if (source != null && destination == null) {
					b.setStyle("-fx-background-color: #000000;\r\n" + "        -fx-background-radius:100;\r\n");// if
																												// pressed
																												// color
																												// it by
																												// black
					destination = (City) b.getUserData();
					destinationBox.setValue(destination.getName());
				}
			});

			Label lb = new Label(cities.get(i).getName());
			lb.setFont(new Font("Arial", 10));
			lb.setTextFill(Color.DARKBLUE);
			lb.setTranslateX(cities.get(i).getXCoordinate() + 50);
			lb.setTranslateY(cities.get(i).getYCoordinate() + 15);

			root.getChildren().add(b);
			root.getChildren().add(lb);
		}

	}

	static double cost = 0;

	public static LinkedList<City> BFS(City startCity, City targetCity) {
		Queue<City> queue = new LinkedList<>();
		Set<City> visited = new HashSet<>();

		queue.add(startCity);
		visited.add(startCity);

		while (!queue.isEmpty()) {
			City currentCity = queue.poll();

			if (currentCity.equals(targetCity)) {
				// Build the path from startCity to targetCity
				LinkedList<City> path = new LinkedList<>();
				City city = targetCity;
				while (city != null) {
					path.addFirst(city);
					city = city.getPreviousCity();
				}
				return path;
			}

			for (Edge neighbor : currentCity.getEdgesList()) {
				City neighborCity = neighbor.getDestination();
				if (!visited.contains(neighborCity)) {
					visited.add(neighborCity);
					neighborCity.setPreviousCity(currentCity);
					queue.add(neighborCity);
				}
			}
		}

		return null; // No path found
	}

	public static List<City> AStar(City initialState, City goalState) {
		PriorityQueue<City> openSet = new PriorityQueue<>();
		HashMap<City, Double> gScore = new HashMap<>();
		HashMap<City, Double> fScore = new HashMap<>();
		HashMap<City, City> cameFrom = new HashMap<>();
		// System.out.println(cities.size());
		for (City city : cities) {
			gScore.put(city, Double.POSITIVE_INFINITY);
			fScore.put(city, Double.POSITIVE_INFINITY);
		}

		gScore.put(initialState, 0.0);
		fScore.put(initialState, hx(initialState, goalState));
		openSet.add(initialState);

		while (!openSet.isEmpty()) {
			City current = openSet.poll();

			if (current.equals(goalState)) {
				return reconstructPath(cameFrom, current);
			}

			for (Edge edge : current.getEdgesList()) {
				City neighbor = edge.getDestination();
				double tentativeGScore = gScore.get(current) + edge.getDistance();
				if (tentativeGScore < gScore.get(neighbor)) {
					cameFrom.put(neighbor, current);
					gScore.put(neighbor, tentativeGScore);
					fScore.put(neighbor, gScore.get(neighbor) + hx(neighbor, goalState));
					if (!openSet.contains(neighbor)) {
						openSet.add(neighbor);
					}
				}
			}
		}

		return null;
	}

	public static List<City> reconstructPath(HashMap<City, City> cameFrom, City current) {
		LinkedList<City> path = new LinkedList<>();
		path.addFirst(current);
		while (cameFrom.containsKey(current)) {
			current = cameFrom.get(current);
			path.addFirst(current);
		}
		return path;
	}

	public static double calculatePathCost(List<City> path) {
		double cost = 0.0;
		for (int i = 0; i < path.size() - 1; i++) {
			City current = path.get(i);
			City next = path.get(i + 1);
			for (Edge edge : current.getEdgesList()) {
				if (edge.getDestination().equals(next)) {
					cost += edge.getDistance();
					break;
				}
			}
		}
		return cost;
	}

	public static double hx(City src, City dest) {
		for (int i = 0; i < src.getAirDistancesList().size(); i++) {
			if (dest.getName().compareTo(src.getAirDistancesList().get(i).getDestination().getName()) == 0) {
				return src.getAirDistancesList().get(i).getAirDistance();
			}
		}
		return 0;
	}

}