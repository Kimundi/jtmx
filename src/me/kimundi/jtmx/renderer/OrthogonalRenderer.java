package me.kimundi.jtmx.renderer;

import java.awt.image.BufferedImage;

import me.kimundi.jtmx.TileFlip;
import me.kimundi.jtmx.TileLayer;
import me.kimundi.jtmx.TiledMap;

public class OrthogonalRenderer extends TiledMapRenderer{

	public OrthogonalRenderer(TiledMap map) {
		super(map);
	}

	public OrthogonalRenderer(TiledMap map, RenderOptions options) {
		super(map, options);
	}

	@Override
	public void renderTileLayer(TileLayer tilelayer, TileDrawTarget target,
			int originX, int originY, float alpha) {
		int tilewidth = map.getTileWidth();
		int tileheight = map.getTileHeight();

		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				int gid = tilelayer.getGid(x, y);
				TileFlip flip = tilelayer.getFlip(x, y);
				BufferedImage image = tileimages[gid];
				if (image != null) {
					target.drawTile(originX + x * tilewidth, 
							originY + y * tileheight, image,
							flip, alpha);
				}
			}
		}
	}

	@Override
	public int getUnpaddedRenderWidth() {
		return map.getWidthInPixel();
	}

	@Override
	public int getUnpaddedRenderHeight() {
		return map.getHeightInPixel();
	}



}
