package me.kimundi.jtmx;

import java.util.Map;

import me.kimundi.util.ImmutableProperties;

public abstract class Layer {
	protected final String name;
	protected final float opacity;
	protected final boolean visible;
	protected final ImmutableProperties properties;
	
	protected final int width;
	protected final int height;
	
	public abstract boolean isTileLayer();
	public abstract boolean isObjectLayer();
	
	public abstract TileLayer asTileLayer();
	public abstract ObjectLayer asObjectLayer();
	
	public abstract LayerTypes getInstanceType();
	
	protected Layer(int width, int height, String name, Map<String, String> properties, boolean visible, float opacity) {
		if (opacity < 0 || opacity > 1) {
			throw new IllegalArgumentException(
					"Opacity must be in the range [0,1]");
		}
		this.width = width;
		this.height = height;
		this.name = name;
		this.properties = ImmutableProperties.copyOf(properties);
		this.visible = visible;
		this.opacity = opacity;	
	}
	public String getName() {
		return name;
	}
	public float getOpacity() {
		return opacity;
	}
	public boolean isVisible() {
		return visible;
	}
	public ImmutableProperties getProperties() {
		return properties;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	
	
	
	
	
}
