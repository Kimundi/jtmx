package me.kimundi.jtmx;

import java.util.Map;

public class RectObject extends LayerObject {
	private final int width;
	private final int height;
	
	public RectObject(String name, String type, int x, int y, boolean visible,
			Map<String, String> properties, int width, int height) {
		super(name, type, x, y, visible, properties);
		if (width < 0 || height < 0) throw new IllegalArgumentException(
				"width and height must be positve!");
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	@Override
	public LayerObjectTypes getInstanceType() {
		return LayerObjectTypes.RECTANGLE;
	}
	@Override
	public boolean isRectObject() {
		return true;
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
		return false;
	}
	@Override
	public RectObject asRectObject() {
		return this;
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
		throw new UnsupportedOperationException("Object is not a TileObject!");
	}
	
	
}
