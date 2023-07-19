package application;

import java.util.LinkedList;

import javafx.scene.control.Button;

public class City implements Comparable{
	private String name;
	private double XCoordinate;
	private double YCoordinate;
	private LinkedList<Edge> edgesList = new LinkedList<>();
	private LinkedList<AirDistance> airDistancesList = new LinkedList<>();
	double cost;
	Button button = new Button();
	City prev;
	public City(String name,  double xCoordinate, double yCoordinate) {
		this.name = name;
		XCoordinate = xCoordinate;
		YCoordinate = yCoordinate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}

	public double getXCoordinate() {
		return XCoordinate;
	}

	public void setXCoordinate(double xCoordinate) {
		XCoordinate = xCoordinate;
	}

	public double getYCoordinate() {
		return YCoordinate;
	}

	public void setYCoordinate(double yCoordinate) {
		YCoordinate = yCoordinate;
	}

	public LinkedList<Edge> getEdgesList() {
		return edgesList;
	}

	public void setEdgesList(LinkedList<Edge> edgesList) {
		this.edgesList = edgesList;
	}

	public LinkedList<AirDistance> getAirDistancesList() {
		return airDistancesList;
	}

	public void setAirDistancesList(LinkedList<AirDistance> airDistancesList) {
		this.airDistancesList = airDistancesList;
	}
	public void addEdge (City city ,double distance) {
		edgesList.add(new Edge(city,distance));
	}
	public void addAirDistance(City city ,double distance) {
		airDistancesList.add(new AirDistance(city,distance));
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return (int)(this.cost-((City)o).cost);
	}

	@Override
	public String toString() {
		return   name +" -> ";
	}

	public City getPreviousCity() {
		// TODO Auto-generated method stub
		return prev;
	}

	public void setPreviousCity(City currentCity) {
		prev=currentCity;
		
	}
	

}
