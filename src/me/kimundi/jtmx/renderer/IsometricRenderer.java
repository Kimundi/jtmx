package me.kimundi.jtmx.renderer;

import me.kimundi.jtmx.TiledMap;

public class IsometricRenderer extends TiledMapRenderer {

	private OrthogonalRenderer ortho;
	
	public IsometricRenderer(TiledMap map) {
		super(map);
		ortho = new OrthogonalRenderer(map);
	}

	@Override
	public void renderLayer(int index, TileDrawTarget target,
			boolean applyOpacity) {
		ortho.renderLayer(index, target, applyOpacity);
	}

}
