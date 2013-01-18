package me.kimundi.jtmx;

import java.util.Map;

public class TileLayer extends Layer {
	public static final long FLIPPED_HORIZONTALLY_FLAG = 0x80000000;
	public static final long FLIPPED_VERTICALLY_FLAG = 0x40000000;
	public static final long FLIPPED_DIAGONALLY_FLAG = 0x20000000;
	private final int[] data;

	public TileLayer(int width, int height, String name, Map<String, String> properties,
			boolean visible, float opacity, int[] data) {
		super(width, height, name, properties, visible, opacity);
		if (data.length != height * width) 
			throw new IllegalArgumentException(
				"data[] length must match width * height");
		this.data = data.clone();
	}

	public int[] getRawData() {
		return data.clone();
	}
	
	private static int access(int[] data, int x, int y, int width, int height) {
		return data[x + width * y];
	}
	
	public int getGid(int x, int y) {
		return getGid(data, x, y, width, height);
	}

	public static int getGid(int[] data, int x, int y, int width, int height) {
		if (x < 0 || width <= x || y < 0 || height <= y) {
			throw new IllegalArgumentException("(x, y) lies outside the " +
					"range denoted by width and height");
		}
		long t = access(data, x, y, width, height);
		t = maskFlipBits(t);
		return (int)t;
	}	
	
	private static int getGidRaw(long item) {
		return (int)maskFlipBits(item);
	}
	
	public static Tile getTile(int[] data, int x, int y, int width, int height) {
		long item = access(data, x, y, width, height);
		return new Tile(getFlipRaw(item), getGidRaw(item));
	}
	
	public Tile getTile(int x, int y) {
		return getTile(data, x, y, width, height);
	}
	
	// TODO: Optimize to bit-maggioc switch statement
	private static boolean isSet(long value, boolean h, boolean v, boolean d) {
		long mask = 0L
				| (h ? FLIPPED_HORIZONTALLY_FLAG : 0)
				| (v ? FLIPPED_VERTICALLY_FLAG : 0)
				| (d ? FLIPPED_DIAGONALLY_FLAG : 0);
		return (value & mask) == mask;
	}
	
	public TileFlip getFlip(int x, int y) {
		return getFlip(data, x, y, width, height);
	}
	
	private static TileFlip getFlipRaw(long item) {
		long t = item;
		if      (isSet(t, true,  true,  true ))
			return TileFlip.HVD;
		else if (isSet(t, true,  true,  false ))
			return TileFlip.HV;
		else if (isSet(t, true,  false, true ))
			return TileFlip.HD;
		else if (isSet(t, true,  false, false ))
			return TileFlip.H;
		else if (isSet(t, false, true,  true ))
			return TileFlip.VD;
		else if (isSet(t, false, true,  false))
			return TileFlip.V;
		else if (isSet(t, false, false, true ))
			return TileFlip.D;
		else 
			return TileFlip.NONE;
	}
	
	public static TileFlip getFlip(int[] data, int x, int y, int width, int height) {
		long t = access(data, x, y, width, height);
		return getFlipRaw(t);
	}

	public static long maskFlipBits(long value) {
		return value 
				& ~FLIPPED_HORIZONTALLY_FLAG
				& ~FLIPPED_VERTICALLY_FLAG
				& ~FLIPPED_DIAGONALLY_FLAG;
	}

	@Override
	public boolean isTileLayer() {
		return true;
	}

	@Override
	public boolean isObjectLayer() {
		return false;
	}

	@Override
	public TileLayer asTileLayer() {
		return this;
	}

	@Override
	public ObjectLayer asObjectLayer() {
		throw new UnsupportedOperationException("Object is not a Objectlayer!");
	}

	@Override
	public LayerTypes getInstanceType() {
		return LayerTypes.TILELAYER;
	}
}
