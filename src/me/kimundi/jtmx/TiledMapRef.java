package me.kimundi.jtmx;

import java.io.File;
import java.io.IOException;

import me.kimundi.util.BackingFile;
import me.kimundi.util.DataCache;

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
