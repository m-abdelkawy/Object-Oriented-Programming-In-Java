package myDemos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class LifeExpectancy extends PApplet {
	private UnfoldingMap map;
	Map<String, Float> lifeExpByCountry;
	List<Feature> lstCountry;
	List<Marker> lstCountryMarker;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;

	/**
	 * This is where to find the local tiles, for working without an Internet
	 * connection
	 */
	public static String mbTilesString = "blankLight-1-3.mbtiles";

	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		} else {
			map = new UnfoldingMap(this, 50, 50, 700, 500, new Google.GoogleMapProvider());
		}
		MapUtils.createDefaultEventDispatcher(this, map);
		// load life expectency data
		lifeExpByCountry = loadLifeExpectancyFromCSV("LifeExpectancyWorldBankModule3.csv");
		println("Loaded " + lifeExpByCountry.size() + " data entries!");

		// Load country polygons and adds them as markers
		lstCountry = GeoJSONReader.loadData(this, "countries.geo.json");
		lstCountryMarker = MapUtils.createSimpleMarkers(lstCountry);
		map.addMarkers(lstCountryMarker);
		
		shadeCountries();
	}

	public void draw() {
		map.draw();
	}

	// Helper method to color each country based on life expectancy
	// Red-orange indicates low (near 40)
	// Blue indicates high (near 100)
	public void shadeCountries() {
		for (Marker marker : this.lstCountryMarker) {
			String countryId = marker.getId();
			if(lifeExpByCountry.containsKey(countryId)) {
				float lifeExp = lifeExpByCountry.get(countryId);
				int colorLvl = (int)map(lifeExp, 40,90,10,255);
				marker.setColor(color(255-colorLvl,100,colorLvl));
			}else {
				marker.setColor(color(150,150,150));
			}
		}
	}

	// Helper method to load life expectancy data from file
	private Map<String, Float> loadLifeExpectancyFromCSV(String fileName) {
		Map<String, Float> lifeExpMap = new HashMap<String, Float>();
		String[] rows = loadStrings(fileName);
		for (String row : rows) {
			String[] lstCol = row.split(",");
			if (lstCol.length == 6 && !lstCol[5].equals("..")) {
				lifeExpMap.put(lstCol[4], Float.parseFloat(lstCol[5]));
				// println("0: " + lstCol[0] + ", 1: " + lstCol[1]);

				// println("Country: " + lstCol[4] + ", Life expectency: " +
				// Float.parseFloat(lstCol[5]));
			}
		}
		return lifeExpMap;
	}

}
