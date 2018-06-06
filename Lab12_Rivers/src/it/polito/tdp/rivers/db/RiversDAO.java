package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}

	public LocalDate getDateFirstMisuration(River r) {
		
		final String sql = "SELECT day FROM river, flow WHERE river.id=flow.river AND flow.river = ?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			if (res.next()) {
				LocalDate lc = res.getDate("day").toLocalDate();
				return lc;
			}

			conn.close();
			return null;
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		
	}
	
	public List<LocalDate> getDateAllMisuration(River r) {
		
		final String sql = "SELECT day FROM river, flow WHERE river.id=flow.river AND flow.river = ?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			List<LocalDate> mis = new ArrayList<>();
			while (res.next()) {
				mis.add(res.getDate("day").toLocalDate());
			}

			conn.close();
			return mis;
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

	}
	
	public float getAvgFlow(River r) {
		
		final String sql = "SELECT AVG(flow) as media FROM river, flow WHERE river.id=flow.river AND flow.river = ?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			if (res.next()) {
				float result = res.getFloat("media");
				return result;
			}

			conn.close();
			return 0;
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		
	}
	
public void getAllMisuration(River r) {
		
		final String sql = "SELECT day, flow FROM flow WHERE flow.river = ?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Flow f = new Flow(res.getDate("day").toLocalDate(), res.getDouble("flow"), r);
				r.getFlows().add(f);
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

	}

	public List<Flow> getFlowsFromRiver(River r){
		
		final String sql = "SELECT day, flow FROM flow WHERE flow.river = ?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			List<Flow> flows = new ArrayList<>();
			while (res.next()) {
				Flow f = new Flow(res.getDate("day").toLocalDate(), res.getDouble("flow"), r);
				flows.add(f);			
			}

			conn.close();
			return flows;
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}
}
