package me.kimundi.jtmx.testing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.kimundi.jtmx.TiledMap;
import me.kimundi.jtmx.io.JTMXParseException;
import me.kimundi.jtmx.io.JTMXParser;
import me.kimundi.jtmx.renderer.GraphicsTarget;
import me.kimundi.jtmx.renderer.OrthogonalRenderer;
import me.kimundi.jtmx.renderer.TiledMapRenderer;
import me.kimundi.util.Utils;

public class JTMXDebugRender {

	/**
	 * @param args
	 * @throws JTMXParseException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, JTMXParseException {
		JTMXParser tmxparser = new JTMXParser();

		writeOutTMX(tmxparser, "etc/isomap.tmx");
		writeOutTMX(tmxparser, "etc/orthomap.tmx");
		
	}
	
	public static void writeOutTMX(JTMXParser tmxparser, String tmxpath) 
			throws JTMXParseException, IOException {
		File tmxfile = new File(tmxpath);
		File tmxrenderdir = Utils.concatFiles(tmxfile.getParentFile(),
				new File("renders"));
		tmxrenderdir.mkdirs();
		
		TiledMap map = tmxparser.parseTMX(tmxpath);
		
		TiledMapRenderer renderer = 
				TiledMapRenderer.createMatchingRenderer(map);
		BufferedImage fullMapRender = 
				GraphicsTarget.createEmptyMapImage(renderer);
		GraphicsTarget g = new GraphicsTarget(fullMapRender.createGraphics());
		
		
		for (int i = 0; i < map.getLayers().size(); i++) {
			renderer.renderLayer(i, g, true);
			BufferedImage image = GraphicsTarget.createLayerRender(
					renderer, i, true);
			image.flush();
			String path = tmxfile.getName() + "_layer" + i + ".png";
			File file = Utils.concatFiles(tmxrenderdir, new File(path));
			System.out.println("Writing " + file);
			ImageIO.write(image, "png", file);	
		}
		fullMapRender.flush();
		String path = tmxfile.getName() + "_layerAll.png";
		File file = Utils.concatFiles(tmxrenderdir, new File(path));
		System.out.println("Writing " + file);
		ImageIO.write(fullMapRender, "png", file);	
		
	}

}
