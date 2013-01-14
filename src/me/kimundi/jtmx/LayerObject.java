package me.kimundi.jtmx;

import java.util.Map;

import me.kimundi.util.ImmutableProperties;
import me.kimundi.util.Properties;

public abstract class LayerObject {
	protected final String name;
	protected final String type;
	protected final int x;
	protected final int y;
	protected final boolean visible;
	protected final ImmutableProperties properties;
	
	protected LayerObject(String name, String type, int x, int y, 
			boolean visible, Map<String, String> properties) {
		this.name = name;
		this.type = type;
		this.x = x;
		this.y = y;
		this.visible = visible;
		this.properties = ImmutableProperties.copyOf(properties);
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isVisible() {
		return visible;
	}

	public ImmutableProperties getProperties() {
		return properties;
	}

	public abstract LayerObjectTypes getInstanceType();
	
	public abstract boolean isRectObject();
	public abstract boolean isPolygonObject();
	public abstract boolean isPolylineObject();
	public abstract boolean isTileObject();

	public abstract RectObject asRectObject();
	public abstract PolygonObject asPolygonObject();
	public abstract PolylineObject asPolylineObject();
	public abstract TileObject asTileObject();

	
}
