package me.kimundi.jtmx;

import java.util.Map;

import me.kimundi.util.ImmutableProperties;

public class TilesetTile {
	private final ImmutableProperties properties;

	public ImmutableProperties getProperties() {
		return properties;
	}

	public TilesetTile(Map<String, String> properties) {
		this.properties = ImmutableProperties.copyOf(properties);
	}

}
