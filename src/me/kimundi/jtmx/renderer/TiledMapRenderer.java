package me.kimundi.jtmx.renderer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import me.kimundi.jtmx.Layer;
import me.kimundi.jtmx.TiledMap;
import me.kimundi.jtmx.TileFlip;
import me.kimundi.jtmx.TileLayer;
import me.kimundi.jtmx.Tileset;
import me.kimundi.jtmx.TilesetRef;

public abstract class TiledMapRenderer {
	protected final TiledMap map;
	protected final ImmutableList<Layer> layers;
	protected final ImmutableMap<Integer, TilesetRef> tilesets;
	protected final BufferedImage[] tileimages;

	public TiledMap getMap() {
		return map;
	}

	public TiledMapRenderer(TiledMap map) {
		this.map = map;
		this.layers = map.getLayers();
		this.tilesets = map.getTilesets();

		// Find size of tileimages;
		int gidsize = 0;
		for (int gid : tilesets.keySet()) {
			TilesetRef ts = tilesets.get(gid);
			gidsize = Math.max(gidsize, gid + ts.getTileset().calcTileCount());
		}
		tileimages = new BufferedImage[gidsize];
		
		// Fill tileimages
		for (int gid : tilesets.keySet()) {
			TilesetRef ts = tilesets.get(gid);
			loadTiles(tileimages, gid, ts);
		}
	}

	private void loadTiles(BufferedImage[] tileimages, int gid,
			TilesetRef tilesetref) {
		BufferedImage image = tilesetref.getTileset().getImage();

		Tileset tileset = tilesetref.getTileset();

		// TODO ALLWAYS use image width/height from xml file EXCEPT if they
		// are missing. THEN use them from image
		if (image.getWidth() != tileset.getImageWidth()
				|| image.getHeight() != tileset.getImageHeight()) {
			throw new IllegalStateException("TODO: Sync width/height");
		}
		int tileindex = gid;

		int imagewidth = image.getWidth();
		int imageheight = image.getHeight();

		int margin = tileset.getMargin();
		int spacing = tileset.getSpacing();

		int tilewidth = tileset.getTileWidth();
		int tileheight = tileset.getTileHeight();
		// TODO tileoffset

		int x = margin;
		int y = margin;

		while ((y + tileheight) <= imageheight &&
			   (x + tilewidth) <= imagewidth) {
			tileimages[tileindex] = image.getSubimage(x, y, tilewidth,
					tileheight);
			tileindex++;
			x += tilewidth + spacing;
			if ((x + tilewidth) > imagewidth) {
				x = margin;
				y += tileheight + spacing;
			}
		}

	}

	public abstract void renderLayer(int index, TileDrawTarget target,
			boolean applyOpacity);
	
	public void renderTile(int index, TileDrawTarget target, TileFlip flip, 
			int x, int y) {
		target.drawTile(x, y, tileimages[index], flip);
	}

	public BufferedImage[] getTileImages() {
		return tileimages;
	}

	public BufferedImage getTileImage(int index) {
		return tileimages[index];
	}
	
	public static TiledMapRenderer createMatchingRenderer(TiledMap map) {
		switch (map.getOrientation()) {
		case ISOMETRIC:
			return new IsometricRenderer(map);
		case ORTHOGONAL:
			return new OrthogonalRenderer(map);
		}
		return null;
	}

}
