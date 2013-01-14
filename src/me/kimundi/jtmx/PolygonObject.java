package me.kimundi.jtmx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.kimundi.util.IntPoint;
import me.kimundi.util.Properties;

import com.google.common.collect.ImmutableList;

public class PolygonObject extends LayerObject {
	private final ImmutableList<IntPoint> points;

	public PolygonObject(String name, String type, int x, int y,
			boolean visible, Map<String, String> properties, 
			List<IntPoint> points) {
		super(name, type, x, y, visible, properties);
		this.points = ImmutableList.copyOf(points); 
	}

	public ImmutableList<IntPoint> getPoints() {
		return points;
	}

	@Override
	public LayerObjectTypes getInstanceType() {
		return LayerObjectTypes.POLYGON;
	}

	@Override
	public boolean isRectObject() {
		return false;
	}

	@Override
	public boolean isPolygonObject() {
		return true;
	}

	@Override
	public boolean isPolylineObject() {
		return false;
	}

	@Override
	public boolean isTileObject() {
		return false;
	}

	@Override
	public RectObject asRectObject() {
		throw new UnsupportedOperationException("Object is not a RectObject!");
	}

	@Override
	public PolygonObject asPolygonObject() {
		return this;
	}

	@Override
	public PolylineObject asPolylineObject() {
		throw new UnsupportedOperationException("Object is not a PolylineObject!");
	}

	@Override
	public TileObject asTileObject() {
		throw new UnsupportedOperationException("Object is not a TileObject!");
	}
	
	
}
