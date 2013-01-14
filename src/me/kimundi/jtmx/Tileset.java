package me.kimundi.jtmx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.collect.ImmutableMap;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import me.kimundi.util.DataCache;
import me.kimundi.util.ImmutableProperties;
import me.kimundi.util.Properties;
import me.kimundi.util.RGBColor;
import me.kimundi.util.Utils;

public class Tileset {
	private final String name;
	private final int tileWidth;
	private final int tileHeight;
	private final int spacing;
	private final int margin;
	
	private final int tileOffsetX;
	private final int tileOffsetY;
	
	private final ImmutableProperties properties;
	
	private final BufferedImage image;
	
	private final RGBColor imageTransparentColor;
	private final boolean  imageTransparentColorEnabled;
	private final int      imageWidth;
	private final int      imageHeight;
	
	private final ImmutableMap<Integer, TilesetTile> tiles;

	public Tileset(String name, int tileWidth, int tileHeight, int spacing,
			int margin, int tileOffsetX, int tileOffsetY,
			Map<String, String> properties, BufferedImage image,
			RGBColor imageTransparentColor, boolean imageUseTransparentColor,
			int imageWidth, int imageHeight,
			Map<Integer, TilesetTile> tiles) {
		if (tileWidth < 0 || tileHeight < 0) {
			throw new IllegalArgumentException(
					"tileWidth and tileHeight must be positive");
		}else if (imageWidth < 0 || imageHeight < 0) {
			throw new IllegalArgumentException(
					"imageWidth and imageHeight must be positive");
		}else if (spacing < 0 || margin < 0) {
			throw new IllegalArgumentException(
					"spacing and margin must be positive");
		}
		this.name = name;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.spacing = spacing;
		this.margin = margin;
		this.tileOffsetX = tileOffsetX;
		this.tileOffsetY = tileOffsetY;
		this.properties = ImmutableProperties.copyOf(properties);
		this.image = Utils.deepCopy(image);
		this.imageTransparentColor = imageTransparentColor;
		this.imageTransparentColorEnabled = imageUseTransparentColor;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.tiles = ImmutableMap.copyOf(tiles);
	}

	public String getName() {
		return name;
	}

	public int getSpacing() {
		return spacing;
	}

	public int getMargin() {
		return margin;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public int getTileOffsetX() {
		return tileOffsetX;
	}

	public int getTileOffsetY() {
		return tileOffsetY;
	}

	public ImmutableProperties getProperties() {
		return properties;
	}

	public ImmutableMap<Integer, TilesetTile> getTiles() {
		return tiles;
	}

	public int calcTileCount() {
		int xcount = 
				(imageWidth - margin + spacing) / (tileWidth + spacing);
		int ycount =
				(imageHeight - margin + spacing) / (tileHeight + spacing);
		return xcount * ycount;
		
	}
	
	public BufferedImage getImage() {
		return Utils.deepCopy(image);
	}

	public RGBColor getImageTransparentColor() {
		return imageTransparentColor;
	}

	public boolean isImageTransparentColorEnabled() {
		return imageTransparentColorEnabled;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}
	
}
