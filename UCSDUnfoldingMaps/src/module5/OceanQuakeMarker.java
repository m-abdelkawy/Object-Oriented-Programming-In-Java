package module5;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import processing.core.PGraphics;

/**
 * Implements a visual marker for ocean earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Mohammed Abdelkawy
 *
 */
public class OceanQuakeMarker extends EarthquakeMarker {
	private UnfoldingMap map;
	private List<Marker> lstCityMarker;

	public void setMap(UnfoldingMap _map) {
		this.map = _map;
	}

	public void setCityMarkers(List<Marker> _lstCityMarker) {
		lstCityMarker = _lstCityMarker;
	}

	public OceanQuakeMarker(PointFeature quake) {
		super(quake);

		// setting field in earthquake marker
		isOnLand = false;
	}

	/** Draw the earthquake as a square */
	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) {
		pg.rect(x - radius, y - radius, 2 * radius, 2 * radius);

		if (clicked) {
			float thisX = this.getScreenPosition(map).x;
			float thisY = this.getScreenPosition(map).y;
			
			for (int i = 0; i < lstCityMarker.size(); i++) {
				if(!((CityMarker)lstCityMarker.get(i)).isHidden()) {
					
					float cx = ((CityMarker)lstCityMarker.get(i)).getScreenPosition(map).x;
					float cy = ((CityMarker)lstCityMarker.get(i)).getScreenPosition(map).y;
					
					pg.line(thisX-200, thisY-50, cx-200, cy-50);
				}
			}
		}
	}

}
