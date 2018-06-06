package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.rivers.db.RiversDAO;

public class Simulatore {
	
	private double capienzaTot;
	private double occupazione;
	private RiversDAO dao = new RiversDAO();
	private double flowOutMin;
	
	private int noErogMin = 0;
	private List<Double> dailyOccupaz = new ArrayList<>();
	
	private PriorityQueue<Event> queue = new PriorityQueue<>();

	public enum EventType{
		IN;
	}
	
	
	public Simulatore(double k, River r) {
		capienzaTot = k*60*60*24*dao.getAvgFlow(r)*30;
		occupazione = capienzaTot/2;
		flowOutMin = 0.8*dao.getAvgFlow(r);

		
		for(Flow f : dao.getFlowsFromRiver(r)) {
			double flowOut = flowOutMin;
			if(Math.random() > 0.95) 
				flowOut = 10*flowOutMin;
		
			queue.add(new Event(EventType.IN, f.getDay(), f.getFlow(), flowOut));
		}
	}
	
	public SimulationResults run() {
		Event e;
		while ((e = queue.poll()) != null) {
			processEvent(e);
		}
		return new SimulationResults(noErogMin, dailyOccupaz);
	}
	
	
	private void processEvent(Event e) {
		switch(e.getTipo()) {
		
		case IN:
			occupazione += e.flowIn;
			
			if(occupazione>capienzaTot) {
				System.out.println("here1");
				occupazione = capienzaTot;
			}
			
			if(occupazione<e.flowOut) {
				System.out.println("here2");
				occupazione = 0;
				noErogMin++;
			}else
				occupazione -= e.flowOut;
				System.out.println("here3");
			
//			if(e.flowIn>e.flowOut) {
//				occupazione += e.flowIn - e.flowOut;
//			}else
	
			dailyOccupaz.add(occupazione);
			System.out.println("fine");
			break;
		}
		
	}







	public class Event implements Comparable<Event>{
		private EventType tipo;
		private LocalDate day;
		private double flowIn;
		private double flowOut;
		
		public Event(EventType tipo, LocalDate day, double in, double out) {

			this.tipo = tipo;
			this.day = day;
			this.flowIn = in;
			this.flowOut = out;
		}

		public EventType getTipo() {
			return tipo;
		}

		public void setTipo(EventType tipo) {
			this.tipo = tipo;
		}

		public LocalDate getDay() {
			return day;
		}

		public void setDay(LocalDate day) {
			this.day = day;
		}

		@Override
		public int compareTo(Event arg0) {
			return this.day.compareTo(arg0.day);
		}

	
		
	}
	
	
}
