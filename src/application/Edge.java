package application;

public class Edge {

	private City destination;
	private double distance;

	public Edge(City destination, double distance) {
		this.destination = destination;
		this.distance = distance;
	}

	public City getDestination() {
		return destination;
	}

	public void setDestination(City destination) {
		this.destination = destination;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

}
