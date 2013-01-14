package me.kimundi.jtmx.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import me.kimundi.jtmx.Layer;
import me.kimundi.jtmx.LayerObject;
import me.kimundi.jtmx.LayerTypes;
import me.kimundi.jtmx.MapOrientation;
import me.kimundi.jtmx.MapVersion;
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
import me.kimundi.jtmx.testing.TMXDebugPrinter;
import me.kimundi.util.IntPoint;
import me.kimundi.util.Properties;
import me.kimundi.util.RGBColor;
import me.kimundi.util.TypeParseException;
import me.kimundi.util.Utils;

import org.javatuples.KeyValue;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.common.collect.ImmutableMap;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static me.kimundi.jtmx.io.JTMXPropertyExtension.*;

public class JTMXParser {
	private DocumentBuilder docbuilder;
	private XPath xpath;
	
	private boolean useMapCache = true;	
	private boolean useTilesetCache = true;
	private boolean useTilesetImageCache = true;

	private Map<String, TiledMap> mapCache = 
			new HashMap<String, TiledMap>();
	private Map<String, Tileset> tilesetCache = 
			new HashMap<String, Tileset>();
	private Map<String, BufferedImage> tilesetImageCache =
			new HashMap<String, BufferedImage>();

	public JTMXParser() throws JTMXParseException{
		try {
			docbuilder = newDocBuilder();
		} catch (ParserConfigurationException e) {
			throw new JTMXParseException("Initialisation error", e);
		}
		xpath = newXPath();
	}
	
	public TiledMap parseTMX(File tmxfile)
			throws IOException, JTMXParseException {
		try {
			String tmxKey = tmxfile.getCanonicalPath();
			if (useMapCache && mapCache.containsKey(tmxKey)) {
				logCache("Map File", tmxKey);
				return mapCache.get(tmxKey);
			} else {
				logIO("Map File", tmxfile);
				TiledMap map = extractTMXRoot(loadDocument(tmxfile), tmxfile);
				if (useMapCache) mapCache.put(tmxKey, map);
				return map;
			}
		} catch (XPathExpressionException e) {
			throw new JTMXParseException("TMX parsing error", e);
		} catch (SAXException e) {
			throw new JTMXParseException("TMX parsing error", e);
		}
	}
	
	public Tileset parseTSX(File tsxfile) 
			throws IOException, JTMXParseException {
		try {
			String tsxKey = tsxfile.getCanonicalPath();
			if (useTilesetCache && tilesetCache.containsKey(tsxKey)) {
				logCache("Tileset File", tsxKey);
				return tilesetCache.get(tsxKey);
			} else {
				logIO("Tileset File", tsxfile);
				Tileset tileset = extractTileset(loadTilesetNode(tsxfile),
						tsxfile.getParentFile());
				if (useTilesetCache) tilesetCache.put(tsxKey, tileset);
				return tileset;
			}
		} catch (XPathExpressionException e) {
			throw new JTMXParseException("TSX parsing error", e);
		} catch (SAXException e) {
			throw new JTMXParseException("TSX parsing error", e);
		}
	}
	
	public MetaMap parseMTMX(File mtmxfile) 
			throws IOException, JTMXParseException {
		try {
			logIO("Metamap File", mtmxfile);
			Document mtmxdoc = loadDocument(mtmxfile);
			return extractMTMXRoot(mtmxdoc, mtmxfile);
		} catch (XPathExpressionException e) {
			throw new JTMXParseException("MTMX parsing error", e);
		} catch (SAXException e) {
			throw new JTMXParseException("MTMX parsing error", e);
		} catch (TypeParseException e) {
			throw new JTMXParseException("MTMX parsing error", e);
		}
	}
	public TiledMap parseTMX(String tmxpath)
			throws IOException, JTMXParseException {
		return parseTMX(new File(tmxpath));
	}
	
	public Tileset parseTSX(String tsxpath) 
			throws IOException, JTMXParseException {
		return parseTSX(new File(tsxpath));
	}
	
	public MetaMap parseMTMX(String mtmxpath) 
			throws IOException, JTMXParseException {
		return parseMTMX(new File(mtmxpath));
	}

	private MetaMap extractMTMXRoot(Node mtmxroot, File mtmxfile) 
			throws XPathExpressionException, JTMXParseException,
			TypeParseException, IOException {
		Node metaMapNode = 
				(Node) xpath.evaluate("./metamap", mtmxroot,
						XPathConstants.NODE);
		Properties metaMapAttributes = extractAttributes(metaMapNode);
			MapVersion metaMapVersion =
					prop2TMXversion(metaMapAttributes, "version");
			MapOrientation metaMapOrientation =
					prop2TMXorientation(metaMapAttributes, "orientation");
			int tilewidth  = prop2uint(metaMapAttributes, "tilewidth");
			int tileheight = prop2uint(metaMapAttributes, "tileheight");
			Properties metaMapProperties = extractProperties(metaMapNode);
			List<MetaLayer> metalayers = 
					extractMetaLayers(metaMapNode, mtmxfile);
			return new MetaMap(metaMapVersion, metaMapOrientation, 
					tilewidth, tileheight, metaMapProperties, metalayers);

	}
	
	private List<MetaLayer> extractMetaLayers(Node metaMapNode, File mtmxfile) 
			throws XPathExpressionException, TypeParseException,
			JTMXParseException, IOException {
		List<MetaLayer> ret = new ArrayList<MetaLayer>();
		NodeList metaLayerNodes = 
				(NodeList) xpath.evaluate("./metalayer", metaMapNode,
						XPathConstants.NODESET);
		for (int i = 0; i < metaLayerNodes.getLength(); i++) {
			Node metaLayerNode = metaLayerNodes.item(i);
			Properties attributes = extractAttributes(metaLayerNode);
			String name = attributes.getProperty("name");
			Properties properties = extractProperties(metaLayerNode);
			List<TiledMapRef> maps = 
					extractTiledMapRefs(metaLayerNode, mtmxfile);
			
			MetaLayer metaLayer = new MetaLayer(name, maps, properties);
			ret.add(metaLayer);
		}
		return ret;
	}

	private List<TiledMapRef> extractTiledMapRefs(Node metaLayerNode, 
			File mtmxfile) throws XPathExpressionException,
			JTMXParseException, IOException, TypeParseException {
		List<TiledMapRef> ret = new ArrayList<TiledMapRef>();
		NodeList metaMapNodes = 
				(NodeList) xpath.evaluate("./map", metaLayerNode,
						XPathConstants.NODESET);
		for (int i = 0; i < metaMapNodes.getLength(); i++) {
			Node metaMapNode = metaMapNodes.item(i);
			Properties attributes = extractAttributes(metaMapNode);
			File mapFile = new File(attributes.getProperty("source"));
			int x = attributes.getPropertyAsUInt("x");
			int y = attributes.getPropertyAsUInt("y");

			File fullMapFile = Utils.concatFiles(mtmxfile.getParentFile(),
					mapFile);
			
			ret.add(new TiledMapRef(x, y, parseTMX(fullMapFile)));
		}
		
		return ret;
	}

	private TiledMap extractTMXRoot(Node tmxroot, File tmxfile)
			throws XPathExpressionException, JTMXParseException,
			IOException, SAXException {
		Node mapNode 
			= (Node) xpath.evaluate("./map", tmxroot, XPathConstants.NODE);
		
		Properties mapAttributes = extractAttributes(mapNode);
		
		MapVersion version         
			= prop2TMXversion(mapAttributes, "version");
		MapOrientation orientation 
			= prop2TMXorientation(mapAttributes, "orientation");
		
		int width               = prop2uint(mapAttributes, "width");
		int height              = prop2uint(mapAttributes, "height");
		int tilewidth           = prop2uint(mapAttributes, "tilewidth");
		int tileheight          = prop2uint(mapAttributes, "tileheight");

		Properties properties = extractProperties(mapNode);
		
		Map<Integer, TilesetRef> tilesets = 
				extractTilesetRefs(mapNode, tmxfile);
		List<Layer> layers = extractLayers(mapNode, width, height);
		
		return new TiledMap(version, orientation, width, height, 
				tilewidth, tileheight, properties, tilesets, layers);
	}

	private List<Layer> extractLayers(Node mapNode, int width, int height) 
			throws XPathExpressionException, JTMXParseException {
		NodeList nodelist = (NodeList) xpath.evaluate(
				"./layer | ./objectgroup", mapNode, XPathConstants.NODESET);
		List<Layer> layers = new ArrayList<Layer>();
		
		for (int i = 0; i < nodelist.getLength(); i++) {
			Node layernode = nodelist.item(i);
			String layernodename = layernode.getNodeName();
			Properties layerAttributes = extractAttributes(layernode);
			Layer layer;
			{
				Properties layerProperties = extractProperties(layernode);
				String name = prop2String(layerAttributes, "name");
				float opacity = prop2float(layerAttributes, "opacity", 1);
				boolean visible = prop2uint(layerAttributes, "visible", 1) != 0;
				if (layernodename.equals("layer")) {
					layer = extractTileLayer(layernode, name, opacity, visible, layerProperties,
							width, height);
				} else if (layernodename.equals("objectgroup")){
					String color_str = prop2String(layerAttributes, "color",
							"#A0A0A4");
					color_str = color_str.substring(1, color_str.length());
					// TODO: Bug repport to TiledMap guys becuase of 
					// two differetn color formats
					RGBColor color = parseHexColor(color_str);
					layer = extractObjectLayer(layernode, name, opacity, visible, layerProperties,
							width, height, color);
				} else {
					throw new JTMXParseException(
							"Unknown layer type encounterd");
				}
			}
			layers.add(layer);
		}
		
		return layers;
	}
	
	private TileLayer extractTileLayer(Node layernode, String name, 
			float opacity, boolean visible, Properties properties, 
			int width, int height) 
					throws XPathExpressionException, JTMXParseException {
		Node datanode = (Node) xpath.evaluate("./data", layernode, XPathConstants.NODE);
		Properties dataAttributes = extractAttributes(datanode);
		
		TileDataEncoding encoding = 
				prop2TMXencoding(dataAttributes, "encoding");
		TileDataCompression compression = 
				prop2TMXcompression(dataAttributes, "compression");
	
		return extractData(datanode, encoding, compression, width, height, 
				name, properties, visible, opacity);
	}
	private TileLayer extractData(Node datanode, TileDataEncoding encoding,
			TileDataCompression compression, int width, int height, String name, Properties properties, boolean visible, float opacity) 
					throws XPathExpressionException, JTMXParseException {
		int[] data = new int[width * height];
		if (encoding.equals(TileDataEncoding.XML)
				&& compression.equals(TileDataCompression.NONE)) {
			NodeList tilenodes = (NodeList) xpath.evaluate(
					"./tile", datanode, XPathConstants.NODESET);
			if (data.length != tilenodes.getLength())
				throw new JTMXParseException("Tile count does not match Map Dimensions!");
			for (int i = 0; i < tilenodes.getLength(); i++) {
				Node tilenode = tilenodes.item(i);
				long gid = prop2ulong(extractAttributes(tilenode), "gid");
				data[i] = (int) gid;
			}
		} else {
			throw new JTMXParseException(
					"Compressed/Encoded Tile Data is not yet supported", 
					new NotImplementedException());
		}
		return new TileLayer(width, height, name, properties, visible, 
				opacity, data);
	}

	private ObjectLayer extractObjectLayer(Node layernode, 
			String layername, float opacity, boolean visible,
			Properties layerProperties, int layerwidth, int layerheight,
			RGBColor color) 
					throws XPathExpressionException, JTMXParseException {
		NodeList objectnodes = (NodeList) xpath.evaluate(
				"./object", layernode, XPathConstants.NODESET);
		List<LayerObject> objects = new ArrayList<LayerObject>();
		for (int j = 0; j < objectnodes.getLength(); j++) {
			Node objectnode = objectnodes.item(j);
			Properties objectProperties = extractProperties(objectnode);
			Properties objectAttributes = extractAttributes(objectnode);
			int x = prop2uint(objectAttributes, "x");
			int y = prop2uint(objectAttributes, "y");
			
			boolean objectvisible = prop2uint(
					objectAttributes, "visible", 1) != 0;
			String objectname = prop2String(objectAttributes, "name", "");
			String objecttype = prop2String(objectAttributes, "type", "");
			
			Node objectpoly = (Node) xpath.evaluate(
					"./polygon | ./polyline", objectnode, XPathConstants.NODE);
			LayerObject obj;
			if (objectAttributes.containsKey("gid")) {
				int gid = prop2uint(objectAttributes, "gid");
				obj = new TileObject(objectname, objecttype, x, y, 
						objectvisible, objectProperties, gid);
				
			} else if (objectpoly != null 
					&& objectpoly.getNodeName().equals("polyline")) {
				Properties polyAttributes = extractAttributes(objectpoly);
				String points_str = prop2String(polyAttributes, "points");
				List<IntPoint> points = parsePoints(points_str);
				
				obj = new PolylineObject(objectname, objecttype, x, y,
						objectvisible, objectProperties, points);
				
			} else if (objectpoly != null 
					&& objectpoly.getNodeName().equals("polygon")) {
				Properties polyAttributes = extractAttributes(objectpoly);
				String points_str = prop2String(polyAttributes, "points");
				List<IntPoint> points = parsePoints(points_str);
				
				obj = new PolygonObject(objectname, objecttype, x, y, 
						objectvisible, objectProperties, points);
				
			} else if (objectAttributes.containsKey("width")
					&& objectAttributes.containsKey("height")) {
				int width = prop2uint(objectAttributes, "width");
				int height = prop2uint(objectAttributes, "height");
				
				obj = new RectObject(objectname, objecttype, x, y, 
						objectvisible, objectProperties, width, height);
				
			} else {
				throw new JTMXParseException(
						"Found unidentifyable object type!");
			}
			objects.add(obj);
		}
		return new ObjectLayer(layerwidth, layerheight, layername, 
				layerProperties, visible, opacity, color, objects);
	}
	

	private List<IntPoint> parsePoints(String strPointList) throws JTMXParseException {
		List<IntPoint> points = new ArrayList<IntPoint>();
		
		String[] strPoints = strPointList.split(" ");
		boolean error = false;
		for (String v : strPoints) {
			String[] pointCoords = v.split(",");
			//System.out.println("Points = {" + pointCoords[0] + ", " + pointCoords[1] + "}");
			if (pointCoords.length == 2) {
				int x = 0, y = 0;
				try {
					x = Integer.parseInt(pointCoords[0]);
					y = Integer.parseInt(pointCoords[1]);
				} catch (NumberFormatException e) {
					error = true; break;
				}
				points.add(new IntPoint(x, y));
			} else {
				error = true; break;
			}
		}
		if (error) {
			throw new JTMXParseException(
					"Malformed Polygon/Polyline Coordinates!");
		} else {
			return points;
		}
	}

	public static void main(String[] args) throws JTMXParseException, IOException {
		TMXDebugPrinter.main(args);
	}
	
	private Properties extractProperties(Node node) 
			throws XPathExpressionException {
		Properties prop = new Properties();
		NodeList nodelist = (NodeList) xpath.evaluate(
				"./properties/property", node, 
				XPathConstants.NODESET);
		
		for (int i = 0; i < nodelist.getLength(); i++) {
			NamedNodeMap attributes = nodelist.item(i).getAttributes();
			for (int j = 0; j < attributes.getLength(); j++) {
				prop.put(
						attributes.getNamedItem("name").getNodeValue(),
						attributes.getNamedItem("value").getNodeValue()
				);
			}
		 }
		return prop;
	}
	
	private Map<Integer, TilesetRef> extractTilesetRefs(Node node, File tmxfile) 
					throws XPathExpressionException, JTMXParseException,
					IOException, SAXException{
		NodeList nodelist = (NodeList) xpath.evaluate(
				"./tileset", node, XPathConstants.NODESET);
		Map<Integer, TilesetRef> tilesetRefs =
				new TreeMap<Integer, TilesetRef>();
		for (int i = 0; i < nodelist.getLength(); i++) {
			Node childnode = nodelist.item(i);
			KeyValue<Integer, TilesetRef> keyvalue =
					extractTilesetRef(childnode, tmxfile);
			tilesetRefs.put(keyvalue.getKey(), keyvalue.getValue());
		}
		return tilesetRefs;
	}
	private KeyValue<Integer, TilesetRef> extractTilesetRef(
			Node node, File tmxfile)
					throws XPathExpressionException, JTMXParseException, 
					IOException, SAXException {	
		Properties attributes = extractAttributes(node);
		int firstgid = prop2int(attributes, "firstgid");
		
		Tileset tileset;
		if (attributes.containsKey("source")) {
			String sourcePath = attributes.get("source");
			File source = new File(sourcePath);
			File tsxFile = Utils.concatFiles(tmxfile.getParentFile(), source); 
			tileset = parseTSX(tsxFile);
		} else {
			tileset = extractTileset(node, tmxfile.getParentFile());
		}
		TilesetRef tilesetRef = new TilesetRef(tileset);
		
		return new KeyValue<Integer, TilesetRef>(firstgid, tilesetRef);
	}

	private Document loadDocument(File file) throws IOException, SAXException  {
		return docbuilder.parse(file.getPath());
	}
	
	private Node loadTilesetNode(File tsxFile) throws IOException, SAXException, XPathExpressionException {
		return (Node) xpath.evaluate("./tileset", loadDocument(tsxFile),
				XPathConstants.NODE);
	}
	
	private BufferedImage loadTilesetImage(File imageFile) throws IOException {
		String imageKey = imageFile.getCanonicalPath();
		if (useTilesetImageCache && tilesetImageCache.containsKey(imageKey)) {
			logCache("Tileset Image", imageKey);
			return tilesetImageCache.get(imageKey);
		} else {
			logIO("Tileset Image", imageFile);
			BufferedImage image = ImageIO.read(imageFile);
			if (useTilesetImageCache) tilesetImageCache.put(imageKey, image);
			return image;
		}
	}

	private Tileset extractTileset(Node node, File parentFile) 
	throws XPathExpressionException, JTMXParseException, IOException {
		Properties attributes = extractAttributes(node);
		Properties properties = extractProperties(node);
		
		String name      = attributes.get("name");
		int tilewidth    = prop2uint(attributes, "tilewidth");
		int tileheight   = prop2uint(attributes, "tileheight");
		
		// Non-Optional data
		Properties imageAttributes = extractAttributes(
				(Node) xpath.evaluate("./image", node, XPathConstants.NODE));
		
		String imageSourceRelative = imageAttributes.get("source");
		File imageFullFile = 
				Utils.concatFiles(parentFile, new File(imageSourceRelative));
		BufferedImage image = loadTilesetImage(imageFullFile);
		// TODO: Dimensions can be made optional!
		int imagewidth  = prop2uint(imageAttributes, "width");
		int imageheight = prop2uint(imageAttributes, "height");

		int margin = prop2uint(attributes, "margin", 0);
		int spacing = prop2uint(attributes, "spacing", 0);
	
		int tileoffsetx;
		int tileoffsety;
		Node tileoffsetNode = (Node) xpath.evaluate(
				"./tileoffset", node, XPathConstants.NODE);
		if (tileoffsetNode != null) {
			Properties tileoffsteAttributes = extractAttributes(tileoffsetNode);
			// TODO: find out if negative offset valid
			tileoffsetx = prop2uint(tileoffsteAttributes, "x", 0);
			tileoffsety = prop2uint(tileoffsteAttributes, "y", 0);
		} else {
			tileoffsetx = 0;
			tileoffsety = 0;
		}
					
		boolean useImageTrans;
		RGBColor imageTransColor;
		
		String rawImageTransColor = imageAttributes.get("trans");
		if (rawImageTransColor != null) {
			useImageTrans = true;
			imageTransColor = parseHexColor(rawImageTransColor);
		} else {
			useImageTrans = false;
			imageTransColor = new RGBColor(0, 0, 0);
		}

		Map<Integer, TilesetTile> tilemap = new TreeMap<Integer, TilesetTile>();
		{// Tiles
			NodeList tiletags = (NodeList) xpath.evaluate(
					"./tile", node, XPathConstants.NODESET);
			for (int i = 0; i < tiletags.getLength(); i++) {
				Node tilenode = tiletags.item(i);
				int id; {
					Properties tileattributes = extractAttributes(tilenode);
					id = prop2uint(tileattributes, "id");
				}
				Properties tileproperties = extractProperties(tilenode);
				tilemap.put(id, new TilesetTile(tileproperties));
			}
		}
		return new Tileset(name, tilewidth, tileheight, spacing, 
				margin, tileoffsetx, tileoffsety, properties, image, 
				imageTransColor, useImageTrans, imagewidth,
				imageheight, tilemap);
	}
	
	//--------------------------------------
	
	private RGBColor parseHexColor(String colorStr) throws JTMXParseException {
		long value = 0;
		boolean fail = false;
		try {
			value = Long.parseLong(colorStr, 16);
		} catch (NumberFormatException e) {
			fail = true;
		}
		if (value < 0 || value > 0xFFFFFF) {
			fail = true;
		}
		if (fail) throw new JTMXParseException("Invalid color value!");
		
		int r = (int) ((value & 0xFF0000) >> 16);
		int g = (int) ((value & 0x00FF00) >> 8);
		int b = (int) (value & 0x0000FF);

		return new RGBColor(r, g, b);
	}

	private static DocumentBuilder newDocBuilder() 
	throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder;
	}

	private static XPath newXPath() {
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		return xpath;
	}
	
	private static Properties extractAttributes(Node node) {
		Properties ret = new Properties();
		NamedNodeMap attributes = node.getAttributes();
		for (int j = 0; j < attributes.getLength(); j++) {
			Node a = attributes.item(j);
			ret.put(a.getNodeName(), a.getNodeValue());
		}
		return ret;
	}
	
	private static void logIO(String desc, File file) throws IOException {
		System.out.println("Loading " + desc + " '" + 
				file + "' (Full Path: '" + file.getCanonicalPath() + "')");
	}
	
	private static void logCache(String desc, String key) {
		System.out.println(
				"Using Cached " + desc + " ('" + key + "')");
	}
}
