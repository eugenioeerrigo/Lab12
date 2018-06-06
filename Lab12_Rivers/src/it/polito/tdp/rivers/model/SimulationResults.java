package it.polito.tdp.rivers.model;

import java.util.List;

public class SimulationResults {

	private int noErogMin;
	private double avgOccup;
	
	public SimulationResults(int noErogMin, List<Double> dailyOccupaz) {
		this.noErogMin = noErogMin;
		
		double sum = 0;
		for(Double d : dailyOccupaz)
			sum += d;
		avgOccup = sum/dailyOccupaz.size();
	}

	public int getNoErogMin() {
		return noErogMin;
	}

	public double getAvgOccup() {
		return avgOccup;
	}

	public String toString() {
		return "Giorni in cui non e' stata garantita l'erogazione minima: "+this.noErogMin+"\nOccupazione media: "+this.avgOccup;
	}
	
	
}
