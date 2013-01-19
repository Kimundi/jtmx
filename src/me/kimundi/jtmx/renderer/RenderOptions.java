package me.kimundi.jtmx.renderer;

import me.kimundi.jtmx.TiledMap;
import me.kimundi.jtmx.Tileset;

public class RenderOptions {
	
	public static RenderOptions defaultOptions = new RenderOptions(
			0,0,0,0,0,0);
	
	public static RenderOptions fitAllTilesets(TiledMap map) {
		int gridWidth = map.getTileWidth();
		int gridHeight = map.getTileHeight();
		
		int newGridWidth = gridWidth;
		int newGridHeight = gridHeight;
		
		for (int gid : map.getTilesets().keySet()) {
			Tileset tileset = map.getTilesets().get(gid).getTileset();
			newGridWidth = Math.max(newGridWidth, tileset.getTileWidth());
			newGridHeight = Math.max(newGridHeight, tileset.getTileHeight());
		}
		
		int marginX = (newGridWidth - gridWidth) / 2;
		int marginY = (newGridHeight - gridHeight);
				
		return new RenderOptions(0, 0, marginY, marginX, marginX, 0);
	}
	
	public RenderOptions(int offsetX, int offsetY, int topMargin,
			int leftMargin, int rightMargin, int bottomMargin) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.leftMargin = leftMargin;
		this.rightMargin = rightMargin;
		this.topMargin = topMargin;
		this.bottomMargin = bottomMargin;
	}
	
	public final int offsetX;
	public final int offsetY;
	public final int leftMargin;
	public final int rightMargin;
	public final int topMargin;
	public final int bottomMargin;
	
}
