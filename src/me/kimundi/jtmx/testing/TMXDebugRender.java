package me.kimundi.jtmx.testing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.kimundi.jtmx.Layer;
import me.kimundi.jtmx.TiledMap;
import me.kimundi.jtmx.TileFlip;
import me.kimundi.jtmx.io.JTMXParseException;
import me.kimundi.jtmx.io.JTMXParser;
import me.kimundi.jtmx.renderer.GraphicsTarget;
import me.kimundi.jtmx.renderer.TMXRenderer;
import me.kimundi.jtmx.renderer.TileDrawTarget;

public class TMXDebugRender {

	/**
	 * @param args
	 * @throws JTMXParseException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, JTMXParseException {
		JTMXParser tmxparser = new JTMXParser();
		TiledMap map = tmxparser.parseTMX("assets/map/Testmap2.tmx");
		
		TMXRenderer renderer = new TMXRenderer(map);
		for (int i = 0; i < map.getLayers().size(); i++) {
			BufferedImage image = GraphicsTarget.createLayerRender(renderer, i, true);
			image.flush();
			String path = "assets/out" + i + ".png";
			System.out.println("Writing " + path);
			ImageIO.write(image, "png", new File(path));	
		}
		
		
	}

}
