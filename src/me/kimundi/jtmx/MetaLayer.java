package me.kimundi.jtmx;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import me.kimundi.util.ImmutableProperties;

public class MetaLayer {
	private final ImmutableList<TiledMapRef> maps;
	private final ImmutableProperties properties;
	private final String name;
	
	public MetaLayer(String name, List<TiledMapRef> maps,
			Map<String, String> properties) {
		this.name = name;
		this.maps = ImmutableList.copyOf(maps);
		this.properties = ImmutableProperties.copyOf(properties);
	}
		
	public ImmutableList<TiledMapRef> getMaps() {
		return maps;
	}
	
	public ImmutableProperties getProperties() {
		return properties;
	}
	
	public String getName() {
		return name;
	}


}
