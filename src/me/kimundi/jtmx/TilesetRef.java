package me.kimundi.jtmx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import me.kimundi.util.BackingFile;
import me.kimundi.util.DataCache;
import me.kimundi.util.FormatedDataReadException;

public class TilesetRef {
	private final Tileset tileset;

	public TilesetRef(Tileset tileset) {
		this.tileset = tileset;
	}

	public Tileset getTileset() {
		return tileset;
	}


	
}
