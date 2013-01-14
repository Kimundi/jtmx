package me.kimundi.jtmx;

public final class Tile {
	private final TileFlip tileFlip;
	private final int gid;
	
	public TileFlip getTileFlip() {
		return tileFlip;
	}

	public int getGid() {
		return gid;
	}

	public Tile(TileFlip tileFlip, int gid) {
		this.tileFlip = tileFlip;
		this.gid = gid;
	}

	public boolean isFlipH() {
		switch (tileFlip) {
		case H:
		case HD:
		case HV:
		case HVD:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isFlipV() {
		switch (tileFlip) {
		case V:
		case VD:
		case HV:
		case HVD:
			return true;
		default:
			return false;
		}
	}
	
	public boolean isFlipD() {
		switch (tileFlip) {
		case D:
		case HD:
		case VD:
		case HVD:
			return true;
		default:
			return false;
		}
	}
}
