package me.kimundi.jtmx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.kimundi.util.RGBColor;

import com.google.common.collect.ImmutableList;
import com.sun.org.apache.bcel.internal.generic.CASTORE;

public class ObjectLayer extends Layer {
	protected final RGBColor color;
	protected final ImmutableList<LayerObject> objects;
	
	public ObjectLayer(int width, int height, String name,
			Map<String, String> properties, boolean visible, float opacity,
			RGBColor color, List<LayerObject> objects) {
		super(width, height, name, properties, visible, opacity);
		this.color = color;
		this.objects = ImmutableList.copyOf(objects);
	}
	
	public RGBColor getColor() {
		return color;
	}

	public ImmutableList<LayerObject> getObjects() {
		return objects;
	}

	@Override
	public boolean isTileLayer() {
		return false;
	}

	@Override
	public boolean isObjectLayer() {
		return true;
	}

	@Override
	public TileLayer asTileLayer() {
		throw new UnsupportedOperationException("Object is not a TileLayer!");
	}

	@Override
	public ObjectLayer asObjectLayer() {
		return this;
	}

	@Override
	public LayerTypes getInstanceType() {
		return LayerTypes.OBJECTLAYER;
	}
	
}
