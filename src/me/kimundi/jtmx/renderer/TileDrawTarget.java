package me.kimundi.jtmx.renderer;

import java.awt.image.BufferedImage;

import me.kimundi.jtmx.TileFlip;

public interface TileDrawTarget {
	public void drawTile(int x, int y, BufferedImage tile, TileFlip flippstate);
	public void setAlpha(float alpha);
	public float getAlpha();
}
