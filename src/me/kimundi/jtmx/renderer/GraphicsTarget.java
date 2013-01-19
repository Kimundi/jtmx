package me.kimundi.jtmx.renderer;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import me.kimundi.jtmx.TiledMap;
import me.kimundi.jtmx.TileFlip;

public class GraphicsTarget implements TileDrawTarget {
	private Graphics2D g;
	private Composite comp = null;;
	private float alpha;
	
	public GraphicsTarget(Graphics2D g) {
		this.g = g;
		setAlpha(1);
	}

	@Override
	public void drawTile(int x, int y, BufferedImage tile, TileFlip flippstate) {
		AffineTransform xform;
		int w = tile.getWidth();
		int h = tile.getHeight();
		
		switch (flippstate) {

		case H:
			xform = new AffineTransform(-1, 0, 0,  1, x+w, y  ); 
			break;
		case V:
			xform = new AffineTransform( 1, 0, 0, -1, x  , y+h);
			break;
		case D:
			xform = new AffineTransform( 1, 0, 0, -1, x  , y  ); 
			xform.quadrantRotate(3); break;
		case HV:
			xform = new AffineTransform(-1, 0, 0, -1, x+w, y+h); 
			break;
		case VD:
			xform = new AffineTransform(-1, 0, 0, -1, x  , y+h); 
			xform.quadrantRotate(1); break;
		case HD:
			xform = new AffineTransform(-1, 0, 0, -1, x+w, y  ); 
			xform.quadrantRotate(3); break;
		case HVD:
			xform = new AffineTransform( 1, 0, 0, -1, x+w, y+h); 
			xform.quadrantRotate(1); break;
		default: //case NONE:
			xform = new AffineTransform( 1, 0, 0,  1, x  , y  );
			break;
		}
		
		g.drawImage(tile, xform, null);
	}
	
	public static BufferedImage createLayerRender(
			TiledMapRenderer renderer, int layerindex, boolean applyOpacity) {
		BufferedImage image = createEmptyMapImage(renderer.getMap());
		Graphics2D g = image.createGraphics();

		g.setColor(     new Color(0, 0, 0, 0));
		g.setBackground(new Color(0, 0, 0, 0));
		
		g.clearRect(0, 0, image.getWidth(), image.getHeight());

		renderer.renderLayer(layerindex, new GraphicsTarget(g), applyOpacity);	
		
		return image;
	}
	
	

	public static BufferedImage createTileRender(
			TiledMapRenderer renderer, int tileIndex, TileFlip flip) {
		BufferedImage image = createEmptyTileImage(renderer.getMap());
		Graphics2D g = image.createGraphics();

		g.setColor(     new Color(0, 0, 0, 0));
		g.setBackground(new Color(0, 0, 0, 0));
		
		g.clearRect(0, 0, image.getWidth(), image.getHeight());

		renderer.renderTile(tileIndex, new GraphicsTarget(g), flip, 0, 0);	
		
		return image;
	}
	
	public static BufferedImage createEmptyMapImage(TiledMap map) {
		return createEmptyImage(map.getWidthInPixel(), map.getHeightInPixel());
	}
	
	public static BufferedImage createEmptyTileImage(TiledMap map) {
		return createEmptyImage(map.getTileWidth(), map.getTileHeight());
	}
	
	public static BufferedImage createEmptyImage(int width, int height) {
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		return image;
	}

	@Override
	public void setAlpha(float alpha) {
		if (this.alpha != alpha) {
			this.alpha = alpha;
			this.comp = AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, alpha);
			g.setComposite(comp);
		}
	}

	@Override
	public float getAlpha() {
		return alpha;
	}

}
