package app;

import util.HashMap;
import util.Map;

public class Main {

	public static void main(String[] args) {
		
		Map<String, Integer> mapa = new HashMap<>();
		
		mapa.put("one", 6);
		mapa.put(null, 66);

		System.out.println(mapa.get("one"));
		System.out.println(mapa.get(null));

		
		mapa.remove("one");
		
		System.out.println(mapa.get("one"));
		System.out.println(mapa.get(null));

	}

}
