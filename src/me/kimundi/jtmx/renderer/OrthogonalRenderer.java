package me.kimundi.jtmx.renderer;

import java.awt.image.BufferedImage;

import me.kimundi.jtmx.Layer;
import me.kimundi.jtmx.TileFlip;
import me.kimundi.jtmx.TileLayer;
import me.kimundi.jtmx.TiledMap;

public class OrthogonalRenderer extends TiledMapRenderer{

	public OrthogonalRenderer(TiledMap map) {
		super(map);
	}

	@Override
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

}
