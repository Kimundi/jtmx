package me.kimundi.jtmx;

import java.util.List;
import java.util.Map;

import me.kimundi.util.IntPoint;
import com.google.common.collect.ImmutableList;

public class PolylineObject extends LayerObject {
	private final ImmutableList<IntPoint> points;

	public PolylineObject(String name, String type, int x, int y,
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
		return LayerObjectTypes.POLYLINE;
	}

	@Override
	public boolean isRectObject() {
		return false;
	}

	@Override
	public boolean isPolygonObject() {
		return false;
	}

	@Override
	public boolean isPolylineObject() {
		return true;
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
		throw new UnsupportedOperationException("Object is not a PolygonObject!");
	}

	@Override
	public PolylineObject asPolylineObject() {
		return this;
	}

	@Override
	public TileObject asTileObject() {
		throw new UnsupportedOperationException("Object is not a TileObject!");
	}
	
	
	
}
