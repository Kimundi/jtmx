package me.kimundi.jtmx;

public class TiledMapRef {
	private final int x;
	private final int y;
	private final TiledMap map;

	public TiledMapRef(int x, int y, TiledMap map){
		this.x = x;
		this.y = y;
		this.map = map;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public TiledMap getMap() {
		return map;
	}

}
