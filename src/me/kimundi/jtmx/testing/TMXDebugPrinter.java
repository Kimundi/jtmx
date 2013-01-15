package me.kimundi.jtmx.testing;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import me.kimundi.jtmx.Layer;
import me.kimundi.jtmx.LayerObject;
import me.kimundi.jtmx.MetaLayer;
import me.kimundi.jtmx.MetaMap;
import me.kimundi.jtmx.ObjectLayer;
import me.kimundi.jtmx.PolygonObject;
import me.kimundi.jtmx.PolylineObject;
import me.kimundi.jtmx.RectObject;
import me.kimundi.jtmx.TiledMap;
import me.kimundi.jtmx.TileLayer;
import me.kimundi.jtmx.TileObject;
import me.kimundi.jtmx.TiledMapRef;
import me.kimundi.jtmx.Tileset;
import me.kimundi.jtmx.TilesetRef;
import me.kimundi.jtmx.TilesetTile;
import me.kimundi.jtmx.io.JTMXParseException;
import me.kimundi.jtmx.io.JTMXParser;
import me.kimundi.util.IntPoint;

public class TMXDebugPrinter {
	private final static String IN = "  ";
	
	public static void main(String[] args) throws JTMXParseException, IOException{ 
		JTMXParser tmxparser = new JTMXParser();
		
		printOutTSX(tmxparser, "etc/buildings.tsx");

		printOutTMX(tmxparser, "etc/isomap.tmx");
		printOutTMX(tmxparser, "etc/orthomap.tmx");
		
	}
	
	public static void printOutTMX(JTMXParser tmxparser, String path) 
			throws JTMXParseException, IOException {
		print(0, "----------- TMX Map Printout -----------");
		TiledMap tmx = tmxparser.parseTMX(new File(path));

		printMap(0, tmx);		
				
	}
	
	public static void printOutTSX(JTMXParser tmxparser, String path) 
			throws JTMXParseException, IOException {
		print(0, "--------- TSX Tileset Printout ---------");

		Tileset tsx = tmxparser.parseTSX(new File(path));
		
		printTileset(0, tsx);
	}
	
	public static void printOutMTMX(JTMXParser tmxparser, String path) 
			throws JTMXParseException, IOException {
		print(0, "-------- MTMX Meta Map Printout --------");
		MetaMap mtmx = tmxparser.parseMTMX(new File(path));

		printMetaMap(0, mtmx);		
		
	}

	private static void print(int indent, String msg) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++) { sb.append(IN); }
		String pre = sb.toString();
		System.out.println(pre + msg);
	}
	
	private static void printMap(int indent, TiledMap tmx) {
		print(indent, "version = " + tmx.getVersion());
		print(indent, "orientation = " + tmx.getOrientation());
		print(indent, "width = " + tmx.getWidth());
		print(indent, "height = " + tmx.getHeight());
		print(indent, "tilewidth = " + tmx.getTileWidth());
		print(indent, "tileheight = " + tmx.getTileHeight());
		print(indent, "Properties:");
		printProp(indent+1, tmx.getProperties());
		print(indent, "Tilesets:");
		for (int firstgid : tmx.getTilesets().keySet()) {
			TilesetRef tileset = tmx.getTilesets().get(firstgid);
			print(indent+1, "Tileset:");
			print(indent+2, "firstgid = " + firstgid);
			//print(2, "sourceExtern = " 	+ tileset.isSourceExtern());
			//print(2, "rawSource = " + tileset.getRawTSXFile());
			//print(2, "actualSource = " + tileset.getTSXFile());
			//print(2, "actualImageSource = " + tileset.getImageFile());			
			print(indent+2, "Data:");
			
			Tileset tilesetdata = tileset.getTileset();
			printTileset(indent+3, tilesetdata);
		}
		print(indent, "Layers:");
		for (Layer layer : tmx.getLayers()) {
			print(indent+1, "Layer:");
			print(indent+2, "Name = " + layer.getName());
			print(indent+2, "Visible = " + layer.isVisible());
			print(indent+2, "Opacity = " + layer.getOpacity());
			print(indent+2, "Properties:");
			printProp(3, layer.getProperties());
			print(indent+2, "InstanceType = " + layer.getInstanceType());
			if (layer.isObjectLayer()) {
				ObjectLayer objlayer = layer.asObjectLayer();
				print(indent+2, "Color = " + objlayer.getColor());
				print(indent+2, "Objects:");
				for(LayerObject obj : objlayer.getObjects()) {
					print(indent+3, "Object:");
					print(indent+4, "Name = " + obj.getName());
					print(indent+4, "Type = " + obj.getType());
					print(indent+4, "X = " + obj.getX());
					print(indent+4, "Y = " + obj.getY());
					print(indent+4, "Visible = " + obj.isVisible());
					print(indent+4, "Properties:");
					printProp(indent+5, obj.getProperties());
					print(indent+4, "InstanceType = " + obj.getInstanceType());
					if (obj.isRectObject()) {
						RectObject rectobj = obj.asRectObject();
						print(indent+4, "Width = " + rectobj.getWidth());
						print(indent+4, "Height = " + rectobj.getHeight());
					}
					if (obj.isTileObject()) {
						TileObject tileobj = obj.asTileObject();
						print(indent+4, "gid = " + tileobj.getGid());
					}
					
					if (obj.isPolygonObject()) {
						PolygonObject polygobj = obj.asPolygonObject();
						List<IntPoint> points = polygobj.getPoints();
						print(indent+4, "points = " + points2SetStr(points));
					}
					if (obj.isPolylineObject()) {
						PolylineObject polylobj = obj.asPolylineObject();
						List<IntPoint> points = polylobj.getPoints();
						print(indent+4, "points = " + points2SetStr(points));
					}

				}
			}
			if (layer.isTileLayer()) {
				TileLayer tilelayer = layer.asTileLayer();
				//print(2, "CompressionEnabled = " + tilelayer.isCompressionEnabled());
				//print(2, "Compression = " + tilelayer.getCompression());
				//print(2, "DataEncodingEnabled = " + tilelayer.isEncodingEnabled());
				//print(2, "DataEncoding = " + tilelayer.getEncoding());
				print(indent+2, "Data:");
				printTileLayer(indent+3, tilelayer);
			}
			
		}
	}
	
	private static void printTileset(int indent, Tileset tilesetdata) {
		print(indent, "name = " + tilesetdata.getName());
		print(indent, "tilewidth = " + tilesetdata.getTileWidth());
		print(indent, "tileheight = " + tilesetdata.getTileHeight());
		
		print(indent, "tileoffset.x = " + tilesetdata.getTileOffsetX());
		print(indent, "tileoffset.y = " + tilesetdata.getTileOffsetY());
		print(indent, "margin = " + tilesetdata.getMargin());
		print(indent, "spacing = " + tilesetdata.getSpacing());
		
		print(indent, "image.height = " + tilesetdata.getImageHeight());
		print(indent, "image.width = " + tilesetdata.getImageWidth());
		//print(3, "image.rawSource = " + tilesetdata.getImageFile());
		print(indent, "image.transparentcolor = " + tilesetdata.getImageTransparentColor());
		print(indent, "image.transparentcolorenabled = " + tilesetdata.isImageTransparentColorEnabled());
		
		print(indent, "Properties:");
		printProp(indent+1, tilesetdata.getProperties());
		
		print(indent, "Tiles:");
		Map<Integer, TilesetTile> tiles = tilesetdata.getTiles();
		for (int id : tiles.keySet()) {
			print(indent+1, "id = " + id);
			TilesetTile tile = tiles.get(id); 
			print(indent+1, "Properties:");
			printProp(indent+2, tile.getProperties());
		}		
	}
	
	private static void printMetaMap(int indent, MetaMap mtmx) {
		print(indent, "version = " + mtmx.getVersion());
		print(indent, "orientation = " + mtmx.getOrientation());
		print(indent, "tilewidth = " + mtmx.getTileWidth());
		print(indent, "tileheight = " + mtmx.getTileHeight());
		
		print(indent, "Properties:");
		printProp(indent+1, mtmx.getProperties());
		print(indent,  "Metalayers:");
		for(MetaLayer metalayer : mtmx.getMetaLayers()) {
			print(indent+1, "Layer:");
			print(indent+2, "name = " + metalayer.getName());
			print(indent+2, "Properties:");
			printProp(indent+3, metalayer.getProperties());
			print(indent+2, "Maps:");
			for (TiledMapRef tmxref : metalayer.getMaps()) {
				print(indent+3, "Map:");
				print(indent+4, "x = " + tmxref.getX());
				print(indent+4, "y = " + tmxref.getY());
				print(indent+4, "Data:");
				printMap(indent+5, tmxref.getMap());
			}
			
		}
	}
	
	private static void printTileLayer(int indent, TileLayer tilelayer) {
		int[] data = tilelayer.getRawData();
		int radix = 10;
		String pad;
		{
			int max = 0;
			int len = 0;
			for (int i = 0; i < data.length; i++) {
				if (data[i] > max) 
					max = (int) TileLayer.maskFlipBits(data[i]);
			}
			len = Integer.toString(max, radix).length();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < len ; i++) {
				sb.append(" ");
			}
			pad = sb.toString();
		}
		for (int y = 0; y < tilelayer.getHeight(); y++) {
			StringBuilder sb = new StringBuilder();
			sb.append("|");
			for (int x = 0; x < tilelayer.getWidth(); x++) {
				sb.append(" ");
				String gid = Integer.toString(tilelayer.getGid(x, y), radix);
				sb.append(pad.substring(0, pad.length() - gid.length()));
				sb.append(gid);
				switch (tilelayer.getFlip(x, y)) {
				case NONE: sb.append("---"); break;
				case H:    sb.append("H--"); break;
				case V:    sb.append("-V-"); break;
				case D:    sb.append("--D"); break;
				case HV:   sb.append("HV-"); break;
				case VD:   sb.append("-VD"); break;
				case HD:   sb.append("H-D"); break;
				case HVD:  sb.append("HVD"); break;
				}
				sb.append(" ");
			}
			sb.append("|");
			print(indent, sb.toString());
		}
	}

	private static String points2SetStr(List<IntPoint> points) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (int i = 0; i < points.size(); i++) {
			IntPoint p = points.get(i);
			sb.append("(" + p.getX() + ", " + p.getY() + ")");
			if (i != points.size() - 1)
				sb.append(", ");
		}
		sb.append("}");
		return sb.toString();
	}

	private static void printProp(int indent, Map<String, String> properties) {
		for (String key : properties.keySet()) {
			String value = properties.get(key); 
			print(indent, key + " = " + value);
		}
	}

}
