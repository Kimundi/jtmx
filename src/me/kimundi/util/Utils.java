package me.kimundi.util;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.Map;

public class Utils {
	public static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	public static File concatFiles(File parent, File child) {
		return new File(parent.getPath(), child.getPath());
	}
	
	public static String debugMap2Str(@SuppressWarnings("rawtypes") Map m) {
		String o = "{";
		boolean item = false;
		for (Object key : m.keySet()) {
			Object value = m.get(key);
			o += key + " = " + value + ", ";
			item = true;
		}
		if (item) 
			return o.substring(0, o.length()-2) + "}";
		else
			return o + "}";
	}
}
