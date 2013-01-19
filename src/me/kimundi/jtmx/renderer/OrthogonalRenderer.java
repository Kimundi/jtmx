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
	public void renderTileLayer(TileLayer tilelayer, TileDrawTarget target,
			float alpha) {
		int tilewidth = map.getTileWidth();
		int tileheight = map.getTileHeight();

		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				int gid = tilelayer.getGid(x, y);
				TileFlip flip = tilelayer.getFlip(x, y);
				BufferedImage image = tileimages[gid];
				if (image != null) {
					target.drawTile(x * tilewidth, y * tileheight, image,
							flip, alpha);
				}
			}
		}
	}

	@Override
	public int getTargetAreaWidth() {
		return map.getPixelWidth();
	}

	@Override
	public int getTargetAreaHeight() {
		return map.getPixelHeight();
	}



}
