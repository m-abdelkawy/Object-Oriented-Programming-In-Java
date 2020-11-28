package myDemos;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Iterator;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

public class EarthquakeCityMap extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = true;
	private UnfoldingMap map;
	/**
	 * This is where to find the local tiles, for working without an Internet
	 * connection
	 */
	public static String mbTilesString = "blankLight-1-3.mbtiles";

	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_day.atom";

	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
			// earthquakesURL = "2.5_week.atom"; // Same feed, saved Aug 7, 2015, for
			// working offline
		} else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			// earthquakesURL = "2.5_week.atom";
		}
		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map);

		// Earthquake

		List<PointFeature> lstFeature = ParseFeed.parseEarthquake(this, earthquakesURL);

//		Location locChile = new Location(-38.14f, -73.03f);
//		PointFeature eqChile = new PointFeature(locChile);
//		eqChile.addProperty("title", "Valdivia, Chile");
//		eqChile.addProperty("magnitude", "9.5");
//		eqChile.addProperty("date", "May 22, 1960");
//		eqChile.addProperty("year", "1960");
//		lstFeature.add(eqChile);
//
//		Location locAlaska = new Location(60.91f, -147.34f);
//		PointFeature eqAlaska = new PointFeature(locAlaska);
//		eqAlaska.addProperty("title", "Alaska, USA");
//		eqAlaska.addProperty("magnitude", "9.2");
//		eqAlaska.addProperty("date", "March 28, 1964");
//		eqAlaska.addProperty("year", "1964");
//		lstFeature.add(eqAlaska);
//
//		Location locSumatra = new Location(3.30f, 95.98f);
//		PointFeature eqSumatra = new PointFeature(locSumatra);
//		eqSumatra.addProperty("title", "Sumatra, Indonesia");
//		eqSumatra.addProperty("magnitude", "9.1");
//		eqSumatra.addProperty("date", "December 26, 2004");
//		eqSumatra.addProperty("year", "2004");
//		lstFeature.add(eqSumatra);
//
//		Location locJapan = new Location(38.30f, 142.37f);
//		PointFeature eqJapan = new PointFeature(locJapan);
//		eqJapan.addProperty("title", "Tohoku, Japan");
//		eqJapan.addProperty("magnitude", "9.1");
//		eqJapan.addProperty("date", "March 11, 2011");
//		eqJapan.addProperty("year", "2011");
//		lstFeature.add(eqJapan);
//
//		Location locKamtchatka = new Location(52.62f, 159.78f);
//		PointFeature eqKamtchatka = new PointFeature(locKamtchatka);
//		eqKamtchatka.addProperty("title", "Kamchatka, Russia");
//		eqKamtchatka.addProperty("magnitude", "9.0");
//		eqKamtchatka.addProperty("date", "November 04, 1952");
//		eqKamtchatka.addProperty("year", "1952");
//		lstFeature.add(eqKamtchatka);

		List<SimplePointMarker> lstMarker = new ArrayList<SimplePointMarker>();
		for (PointFeature eq : lstFeature) {
			lstMarker.add(new SimplePointMarker(eq.getLocation(), eq.getProperties()));

//			Iterator it = eq.getProperties().entrySet().iterator();
//			while (it.hasNext()) {
//				Map.Entry pair = (Map.Entry) it.next();
//				System.out.println(pair.getKey() + " = " + pair.getValue());
//				it.remove(); // avoids a ConcurrentModificationException
			// }
			// System.out.println();
		}

		formatMarkers(lstMarker);

		for (Marker mk : lstMarker) {

			map.addMarker(mk);
		}

	}

	public void draw() {
		background(10);
		map.draw();
		addkey();
	}

	public void addkey() {
		int xStart = 0;
		int yStart = 50;
		int vlSpace = 20;
		int hlSpace = 20;

		fill(255, 255, 255);
		rect(xStart, yStart, 150, 200);

		fill(0, 0, 0);
		text("Earthquake key", xStart + hlSpace, yStart + vlSpace);

		fill(255, 0, 0);
		ellipse(xStart + hlSpace, yStart + 4 * vlSpace, 2 * (THRESHOLD_MODERATE + 1), 2 * (THRESHOLD_MODERATE + 1));

		fill(0, 0, 0);
		textAlign(LEFT, CENTER);
		//line(xStart + hlSpace, yStart + 2 * vlSpace, xStart + 5 * hlSpace, yStart + 2 * vlSpace);
		text("5.0 + magnitude", xStart + 2 * hlSpace, yStart + 4 * vlSpace);
		
		
		fill(255, 255, 0);
		ellipse(xStart + hlSpace, yStart + 6 * vlSpace, 2 * THRESHOLD_LIGHT, 2 * THRESHOLD_LIGHT);
		
		fill(0, 0, 0);
		textAlign(LEFT, CENTER);
		text("4.0 + magnitude", xStart + 2 * hlSpace, yStart + 6 * vlSpace);
		
		
		fill(0, 255, 0);
		ellipse(xStart + hlSpace, yStart + 8 * vlSpace, 2 * (THRESHOLD_LIGHT-1), 2 * (THRESHOLD_LIGHT-1));
		
		fill(0, 0, 0);
		textAlign(LEFT, CENTER);
		text("Below 4.0", xStart + 2 * hlSpace, yStart + 8 * vlSpace);
	}

	public void formatMarkers(List<SimplePointMarker> lstMarker) {
		int red = color(255, 0, 0);
		int yellow = color(255, 255, 0);
		int green = color(0, 255, 0);

		for (SimplePointMarker mk : lstMarker) {
			float magnitude = Float.parseFloat(mk.getProperty("magnitude").toString());
			if (magnitude >= THRESHOLD_MODERATE) {
				mk.setColor(red);
				mk.setRadius(2 * (THRESHOLD_MODERATE + 1));
			} else if (magnitude >= THRESHOLD_LIGHT) {
				mk.setColor(yellow);
				mk.setRadius(2 * THRESHOLD_LIGHT);
			} else {
				mk.setColor(green);
				mk.setRadius(2 * (THRESHOLD_LIGHT - 1));
			}
			// int magnitude = (int)mk.getProperty("magnitude");
			// mk.setRadius(magnitude);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
