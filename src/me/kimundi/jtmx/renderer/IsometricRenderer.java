package me.kimundi.jtmx.renderer;

import java.awt.image.BufferedImage;

import me.kimundi.jtmx.Layer;
import me.kimundi.jtmx.TileFlip;
import me.kimundi.jtmx.TileLayer;
import me.kimundi.jtmx.TiledMap;

public class IsometricRenderer extends TiledMapRenderer {

	private OrthogonalRenderer ortho;
	
	public IsometricRenderer(TiledMap map) {
		super(map);
		ortho = new OrthogonalRenderer(map);
	}

	@Override
	public int getTargetAreaWidth() {
		int width = map.getWidth();
		int height = map.getHeight();
		int tilewidth = map.getTileWidth();
		
		return (height * tilewidth)/2 + (width * tilewidth)/2;
	}

	@Override
	public int getTargetAreaHeight() {
		int width = map.getWidth();
		int height = map.getHeight();
		int tileheight = map.getTileHeight();

		return (height * tileheight)/2 + (width * tileheight)/2;
	}


	@Override
	public void renderTileLayer(TileLayer tilelayer, TileDrawTarget target,
			float alpha) {
		int tilewidth = map.getTileWidth();
		int tileheight = map.getTileHeight();
		int width = map.getWidth();
		int height = map.getHeight();
		
		// The origin point of every tile to-be-drawn is the bottom-center
		// of tile (0, 0) in *maps* tilewidth and tileheight dimensions.
		int pxlOriginX = getTargetAreaWidth() / 2;
		int pxlOriginY = tileheight;
		
		int x = 0;
		int y = 0;
		while(true) {
			if (x >= width && y >= height) {
				// Every tile reached -> exit loop
				break;    
			} else if (x < width && y < height) {
				// render stuff at tile (x,y),
				// but skip out-of-map areas
				int gid = tilelayer.getGid(x, y);
				TileFlip flip = tilelayer.getFlip(x, y);
				BufferedImage image = tileimages[gid];
				if (image != null) {
					// exact offset for each *individual*
					// tile to align it at its bottom-center
					int tilePxlOffsetX = -image.getWidth() / 2;
					int tilePxlOffsetY = -image.getHeight();

					// exact offset for each tiles position,
					// relative to Origin.
					int pxlOffsetX = (tilewidth * x) / 2 - 
							(tilewidth * y) / 2;
					int pxlOffsetY = (tileheight * x) / 2 + 
							(tileheight * y) / 2;
					
					int pxlX = pxlOriginX + tilePxlOffsetX + pxlOffsetX;
					int pxlY = pxlOriginY + tilePxlOffsetY + pxlOffsetY;
					
					target.drawTile(pxlX, pxlY, image, flip, alpha);
				}
			}
			
			

			// Advance tiles in diagonal lines, starting from (0,0)
			x += 1;
			y -= 1;
			if (y < 0) {
				y = x;
				x = 0;
			}
		}
	}

}
