package me.kimundi.jtmx.renderer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import me.kimundi.jtmx.Layer;
import me.kimundi.jtmx.TiledMap;
import me.kimundi.jtmx.TileFlip;
import me.kimundi.jtmx.TileLayer;
import me.kimundi.jtmx.Tileset;
import me.kimundi.jtmx.TilesetRef;

public class TMXRenderer {
	private TiledMap map;
	private List<Layer> layers;
	private Map<Integer, TilesetRef> tilesets;
	private BufferedImage[] tileimages;

	public TiledMap getMap() {
		return map;
	}

	public TMXRenderer(TiledMap map) {
		this.map = map;
		this.layers = map.getLayers();
		this.tilesets = map.getTilesets();
		prepare();
	}

	private void prepare() {
		int gidsize = 0;
		for (int gid : tilesets.keySet()) {
			TilesetRef ts = tilesets.get(gid);
			gidsize = Math.max(gidsize, gid + ts.getTileset().calcTileCount());
		}
		tileimages = new BufferedImage[gidsize];
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
		// arre missing. THEN use them from image
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

		while ((y + tileheight) <= imageheight && (x + tilewidth) <= imagewidth) {
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

	public void renderLayer(Layer layer, TileDrawTarget target,
			boolean applyOpacity) {
		if (layers.contains(layer)) {
			renderLayer(layers.indexOf(layer), target, applyOpacity);
		} else {
			throw new IllegalArgumentException(
					"Can only draw layers of the prior given map");
		}
	}

	public void renderLayer(int index, TileDrawTarget target,
			boolean applyOpacity) {
		Layer layer = layers.get(index);
		float opacity = layer.getOpacity();
		int tilewidth = map.getTileWidth();
		int tileheight = map.getTileHeight();

		if (applyOpacity) {
			target.setAlpha(opacity);
		} else {
			target.setAlpha(1);
		}

		if (layer.isTileLayer()) {
			TileLayer tilelayer = layer.asTileLayer();
			for (int y = 0; y < map.getHeight(); y++) {
				for (int x = 0; x < map.getWidth(); x++) {
					int gid = tilelayer.getGid(x, y);
					TileFlip flip = tilelayer.getFlip(x, y);
					BufferedImage image = tileimages[gid];
					if (image != null) {
						target.drawTile(x * tilewidth, y * tileheight, image,
								flip);
					}
				}
			}
		}
	}

	public void renderTile(int index, TileDrawTarget target, TileFlip flip) {
		target.drawTile(0, 0, tileimages[index], flip);
	}

	public BufferedImage[] getTileImages() {
		return tileimages;
	}

	public BufferedImage getTileImage(int index) {
		return tileimages[index];
	}

}
