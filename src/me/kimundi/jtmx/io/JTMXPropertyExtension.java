package me.kimundi.jtmx.io;

import java.util.Map;

import me.kimundi.jtmx.MapOrientation;
import me.kimundi.jtmx.MapVersion;

public class JTMXPropertyExtension {
	
	public static int prop2int(Map<String, String> prop, String key) throws JTMXParseException {
		return prop2int_(prop, key, false, 0);
	}
	
	public static int prop2int(Map<String, String> prop, String key, int defaultv) throws JTMXParseException {
		return prop2int_(prop, key, true, defaultv);
	}

	private static int prop2int_(Map<String, String> prop, String key, boolean optional, int defaultv) 
	throws JTMXParseException {
		String v = prop.get(key);
		int r;
		if (v == null && optional) {
			r = defaultv;
		} else try {
			r = Integer.parseInt(v);
		}catch (NumberFormatException e) {
			throw new JTMXParseException(
					"Key '" + key + "' is not a int! " +
					"(Actual value: '" + v + "')");
		}
		return r;
	}
	public static long prop2long(Map<String, String> prop, String key) throws JTMXParseException {
		return prop2long_(prop, key, false, 0);
	}
	
	public static long prop2long(Map<String, String> prop, String key, long defaultv) throws JTMXParseException {
		return prop2long_(prop, key, true, defaultv);
	}
	
	private static long prop2long_(Map<String, String> prop, String key, boolean optional, long defaultv)
		throws JTMXParseException {
		String v = prop.get(key);
		long r;
		if (v == null && optional) {
			r = defaultv;
		} else try {
			r = Long.parseLong(v);
		}catch (NumberFormatException e) {
			throw new JTMXParseException(
					"Key '" + key + "' is not a long! " +
					"(Actual value: '" + v + "')");
		}
		return r;
		
	}
	public static int prop2uint(Map<String, String> prop, String key) throws JTMXParseException {
		return prop2uint_(prop, key, false, 0);
	}
	
	public static int prop2uint(Map<String, String> prop, String key, int defaultv) throws JTMXParseException {
		return prop2uint_(prop, key, true, defaultv);
	}
	
	private static int prop2uint_(Map<String, String> prop, String key, boolean optional, int defaultv) 
	throws JTMXParseException {
		int v = prop2int_(prop, key, optional, defaultv);
		if (v < 0) {
			throw new JTMXParseException(
					"Key '" + key + "' is not a unsigned int! " +
					"(Actual value: '" + v + "')");
		}
		return v;
	}
	public static long prop2ulong(Map<String, String> prop, String key) throws JTMXParseException {
		return prop2ulong_(prop, key, false, 0);
	}
	
	public static long prop2ulong(Map<String, String> prop, String key, long defaultv) throws JTMXParseException {
		return prop2ulong_(prop, key, true, defaultv);
	}
	private static long prop2ulong_(Map<String, String> prop, String key, boolean optional, long defaultv) 
	throws JTMXParseException {
		long v = prop2long_(prop, key, optional, defaultv);
		if (v < 0) {
			throw new JTMXParseException(
					"Key '" + key + "' is not a unsigned long! " +
					"(Actual value: '" + v + "')");
		}
		return v;
	}
	
	public static float prop2float(Map<String, String> prop, String key) throws JTMXParseException {
		return prop2float_(prop, key, false, 0);
	}
	
	public static float prop2float(Map<String, String> prop, String key, float defaultv) throws JTMXParseException {
		return prop2float_(prop, key, true, defaultv);
	}
	private static float prop2float_(Map<String, String> prop, String key, boolean optional, float defaultv) 
	throws JTMXParseException {
		String v = prop.get(key);
		float r;
		if (v == null && optional) {
			r = defaultv;
		} else try {
			r = Float.parseFloat(v);
		}catch (NumberFormatException e) {
			throw new JTMXParseException(
					"Key '" + key + "' is not a float! " +
					"(Actual value: '" + v + "')");
		}
		return r;
	}
	public static double prop2double(Map<String, String> prop, String key) throws JTMXParseException {
		return prop2double_(prop, key, false, 0);
	}
	
	public static double prop2double(Map<String, String> prop, String key, double defaultv) throws JTMXParseException {
		return prop2double_(prop, key, true, defaultv);
	}
	private static double prop2double_(Map<String, String> prop, String key, boolean optional, double defaultv)
		throws JTMXParseException {
		String v = prop.get(key);
		double r;
		if (v == null && optional) {
			r = defaultv;
		} else try {
			r = Double.parseDouble(v);
		}catch (NumberFormatException e) {
			throw new JTMXParseException(
					"Key '" + key + "' is not a double! " +
					"(Actual value: '" + v + "')");
		}
		return r;
	}

	public static MapOrientation prop2TMXorientation(Map<String, String> prop, String key) 
	throws JTMXParseException {
		String v = prop.get(key);
		if (v.equals("orthogonal")) {
			return MapOrientation.ORTHOGONAL;
		} else if (v.equals("isometric")) {
			return MapOrientation.ISOMETRIC;
		}
		throw new JTMXParseException("Unknown TMX orientation '" + v + "'");
	}

	public static MapVersion prop2TMXversion(Map<String, String> prop, String key) 
	throws JTMXParseException {
		String v = prop.get(key);
		if (v.equals("1.0")) {
			return MapVersion.VER_1_0;
		}
		throw new JTMXParseException("Unknown TMX version '" + v + "'");
	}
	private static String prop2String_(Map<String, String> prop, String key, boolean optional, String defaultv)
	throws JTMXParseException {
		String v = prop.get(key);
		if (v == null && optional) {
			v = defaultv;
		}
		return v;
	}
	public static String prop2String(Map<String, String> prop, String key) 
	throws JTMXParseException {
		return prop2String_(prop, key, false, null);
	}
	public static String prop2String(Map<String, String> prop, String key, String defaultv) 
	throws JTMXParseException {
		return prop2String_(prop, key, true, defaultv);
	}
	
	public static TileDataEncoding prop2TMXencoding(Map<String, String> prop, String key) 
	throws JTMXParseException {
		String v = prop.get(key);
		if (v == null) return TileDataEncoding.XML;
		if (v.equals("base64")) return TileDataEncoding.BASE64;
		if (v.equals("csv")) return TileDataEncoding.CSV;
		throw new JTMXParseException("Unknown encoding '" + v + "'");
	}
	public static TileDataCompression prop2TMXcompression(Map<String, String> prop, String key) 
	throws JTMXParseException {
		String v = prop.get(key);
		if (v == null) return TileDataCompression.NONE;
		if (v.equals("gzip")) return TileDataCompression.GZIP;
		if (v.equals("zlib")) return TileDataCompression.ZLIB;
		throw new JTMXParseException("Unknown compression '" + v + "'");
	}
			
}
