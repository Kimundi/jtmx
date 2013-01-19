package me.kimundi.jtmx;

import java.util.List;
import java.util.Map;
import org.javatuples.Pair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import me.kimundi.util.ImmutableProperties;

public class TiledMap {
	private final MapVersion version;
	private final MapOrientation orientation;
	private final int width;
	private final int height;
	private final int tilewidth;
	private final int tileheight;
	private final ImmutableProperties properties;
	private final ImmutableMap<Integer, TilesetRef> tilesets;
	private final ImmutableList<Layer> layers;
	
	public TiledMap(MapVersion version, MapOrientation orientation,
			int width, int height, int tilewidth, int tileheight, 
			Map<String, String> properties,
			Map<Integer, TilesetRef> tilesets,
			List<Layer> layers) {
		if (width < 0 || height < 0) throw new IllegalArgumentException(
				"width and height have to be positive");
		if (tilewidth < 0 || tileheight < 0) throw new IllegalArgumentException(
				"tilewidth and tileheight have to be positive");
		this.version     = version;
		this.orientation = orientation;
		this.width       = width;
		this.height      = height;
		this.tilewidth   = tilewidth;
		this.tileheight  = tileheight;
		this.properties  = ImmutableProperties.copyOf(properties);
		this.tilesets    = ImmutableMap.copyOf(tilesets);
		this.layers      = ImmutableList.copyOf(layers);
	}

	public MapVersion getVersion() {
		return version;
	}

	public MapOrientation getOrientation() {
		return orientation;
	}

	public int getWidth() {
		return width;
	}
	
	public int getPixelWidth() {
		return width * tilewidth;
	}

	public int getHeight() {
		return height;
	}
	
	public int getPixelHeight() {
		return height * tileheight;
	}

	public int getTileWidth() {
		return tilewidth;
	}

	public int getTileHeight() {
		return tileheight;
	}

	public ImmutableProperties getProperties() {
		return properties;
	}

	public ImmutableMap<Integer, TilesetRef> getTilesets() {
		return tilesets;
	}		
	
	public ImmutableList<Layer> getLayers() {
		return layers;
	}
	
	public Pair<Integer, TilesetRef> mapGidToFirstGidTilesetPair(int gid) {
		if (gid == 0) {
			return new Pair<Integer, TilesetRef>(0, null);
		} else if (tilesets.containsKey(gid)) {
			return new Pair<Integer, TilesetRef>(gid, tilesets.get(gid));
		} else {
			int last_key_gid = 0;
			for(int current_key_gid : tilesets.keySet()) {
				if (current_key_gid > last_key_gid && current_key_gid <= gid) {
					last_key_gid = current_key_gid;
				}
			}
			TilesetRef ret = tilesets.get(last_key_gid);
			int offset = gid - last_key_gid;
			if (offset < ret.getTileset().calcTileCount()) {
				return new Pair<Integer, TilesetRef>(last_key_gid, ret);
			} else {
				return new Pair<Integer, TilesetRef>(0, null);
			}
		}
	}

}
