package me.kimundi.util;

public final class RGBColor {
	protected int r;
	protected int g;
	protected int b;
	
	public RGBColor(int r, int g, int b) {
		checkRange(r);
		this.r = r;
		checkRange(g);
		this.g = g;
		checkRange(b);
		this.b = b;
	}
	
	public int getR() {
		return r;
	}
	
	public int getG() {
		return g;
	}
	
	public int getB() {
		return b;
	}
	
	protected static void checkRange(int v) {
		if (v < 0 || v > 255) {
			throw new IllegalArgumentException(
					"The color components must fall into the Range [0, 255]!");
		}
	}
	@Override
	public String toString() {
		return "RGBColor [r=" + r + ", g=" + g + ", b=" + b + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + b;
		result = prime * result + g;
		result = prime * result + r;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RGBColor other = (RGBColor) obj;
		if (b != other.b)
			return false;
		if (g != other.g)
			return false;
		if (r != other.r)
			return false;
		return true;
	}
	
	public RGBColor withR(int r) {
		return new RGBColor(r, g, b);
	}
	public RGBColor withG(int g) {
		return new RGBColor(r, g, b);
	}
	public RGBColor withB(int b) {
		return new RGBColor(r, g, b);
	}
	public RGBColor withRB(int r, int b) {
		return new RGBColor(r, g, b);
	}
	public RGBColor withBG(int b, int g) {
		return new RGBColor(r, g, b);
	}
	public RGBColor withRG(int r, int g) {
		return new RGBColor(r, g, b);
	}

}
