package me.kimundi.jtmx;

import java.util.Map;

import me.kimundi.util.Properties;

public class TileObject extends LayerObject {
	private final int gid;

	public TileObject(String name, String type, int x, int y, boolean visible,
			Map<String, String> properties, int gid) {
		super(name, type, x, y, visible, properties);
		this.gid = gid;
	}

	public int getGid() {
		return gid;
	}

	@Override
	public LayerObjectTypes getInstanceType() {
		return LayerObjectTypes.TILE;
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
		return false;
	}

	@Override
	public boolean isTileObject() {
		return true;
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
		throw new UnsupportedOperationException("Object is not a PolylineObject!");
	}

	@Override
	public TileObject asTileObject() {
		return this;
	}
	
	
}
