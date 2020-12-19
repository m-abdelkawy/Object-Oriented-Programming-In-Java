package myDemos;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class MyDefaultEventExample extends PApplet {
	private UnfoldingMap map;

	public static String mbTilesString = "blankLight-1-3.mbtiles";

	public void setup() {
		size(800, 600, OPENGL);
		// map = new UnfoldingMap(this, 50, 50, 700, 500, new
		// Google.GoogleMapProvider());
		map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		
		MapUtils.createDefaultEventDispatcher(this, map);
	}

	public void draw() {
		map.draw();
	}

	public void keyPressed() {
		if (key == 'w') {
			background(255, 255, 255);
			System.out.println("W pressed");
		} else if (key == 'r') {
			background(255, 0, 0);
			System.out.println("r pressed");			
		}
	}
}
