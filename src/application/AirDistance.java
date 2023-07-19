package application;

public class AirDistance {
	private City destination;
	private double airDistance;
	public AirDistance( City destination, double airDistance) {
	
		this.destination = destination;
		this.airDistance = airDistance;
	}



	public City getDestination() {
		return destination;
	}

	public void setDestination(City destination) {
		this.destination = destination;
	}

	public double getAirDistance() {
		return airDistance;
	}

	public void setAirDistance(double airDistance) {
		this.airDistance = airDistance;
	}

}
