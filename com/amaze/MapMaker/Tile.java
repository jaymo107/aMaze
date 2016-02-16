package com.amaze.MapMaker;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class Tile extends RectangleShape {

    public enum BlockType {
        WALL, FLOOR, DOOR, VOID, CHARGE, START, FINISH
    }

    private BlockType type = BlockType.WALL;
    private Texture[] blockTextures = new Texture[BlockType.values().length];

    public Tile(Texture[] blockTextures, float x, float y) {
        super(new Vector2f(x, y));
        this.blockTextures = blockTextures;
    }

    public void changeBlockType(BlockType block) {
        int index = 0;

        if (block == BlockType.WALL)   index = 0;
        if (block == BlockType.FLOOR)  index = 1;
        if (block == BlockType.DOOR)   index = 2;
        if (block == BlockType.VOID)   index = 3;
        if (block == BlockType.CHARGE) index = 4;
        if (block == BlockType.START)  index = 5;
        if (block == BlockType.FINISH) index = 6;

        setTexture(blockTextures[index]);
        type = block;
    }

    public BlockType getBlockType() {
		return type;
	}

}
