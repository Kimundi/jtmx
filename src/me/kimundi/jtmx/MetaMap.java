package me.kimundi.jtmx;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import me.kimundi.util.BackingFile;
import me.kimundi.util.ImmutableProperties;
import me.kimundi.util.Properties;

public class MetaMap {
	private final MapVersion version;
	private final MapOrientation orientation;
	private final int tileWidth;
	private final int tileHeight;
	private final ImmutableProperties properties;
	private final ImmutableList<MetaLayer> metaLayers; 
	
	public MetaMap(MapVersion version, MapOrientation orientation,
			int tileWidth, int tileHeight, Map<String, String> properties,
			List<MetaLayer> metalayers) {
		this.version = version;
		this.orientation = orientation;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.properties = ImmutableProperties.copyOf(properties);
		this.metaLayers = ImmutableList.copyOf(metalayers);
	}

	public MapVersion getVersion() {
		return version;
	}
	
	public MapOrientation getOrientation() {
		return orientation;
	}
	
	public int getTileWidth() {
		return tileWidth;
	}
	
	public int getTileHeight() {
		return tileHeight;
	}
	
	public ImmutableProperties getProperties() {
		return properties;
	}

	public ImmutableList<MetaLayer> getMetaLayers() {
		return metaLayers;
	}


}
