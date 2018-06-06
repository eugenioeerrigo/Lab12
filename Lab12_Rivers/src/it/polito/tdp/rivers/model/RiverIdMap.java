package it.polito.tdp.rivers.model;

import java.util.HashMap;
import java.util.Map;

public class RiverIdMap {

	private Map<Integer, River> map;
	
	public RiverIdMap() {
		map = new HashMap<>();
	}
	
	public River get(River r) {
		River old = map.get(r.getId());
		if(old==null) {
			map.put(r.getId(), r);
			return r;
		}
		return old;
	}
	
	
}
