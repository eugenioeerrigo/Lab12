package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.List;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	private RiversDAO rdao;
	
	public Model() {
		rdao = new RiversDAO();
	}

	public List<River> getAllRivers(){
		return rdao.getAllRivers();
	}
	
	public LocalDate getDateFirstMisuration(River r) {
		return rdao.getDateFirstMisuration(r);
	}
	
	public LocalDate getDateLastMisuration(River r) {
		return rdao.getDateAllMisuration(r).get(rdao.getDateAllMisuration(r).size()-1);
	}
	
	public int getNumMisurations(River r) {
		return rdao.getDateAllMisuration(r).size();
	}
	
	public float getAvgMisurations(River r) {
		return rdao.getAvgFlow(r);
	}
	
	public SimulationResults simula(double k, River r) {
		Simulatore s = new Simulatore(k, r);
		
		return s.run();
	}
}
