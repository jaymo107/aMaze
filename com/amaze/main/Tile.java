package com.amaze.main;

import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

/**
 * Represents a Tile and displays depending on type
 */

public class Tile extends RectangleShape{

	private BlockType blockType;

    public enum BlockType {
        WALL, FLOOR, DOOR, START, FINISH, VOID, CHARGE
    }

    /**
     * Construct a Tile
     * @param originX Start X pos
     * @param originY Start Y pos
     */
    public Tile(int originX, int originY, int sizeX, int sizeY, BlockType type, Texture[] imageCache){
		Vector2f position = new Vector2f(originX, originY);
		Vector2f tileSize = new Vector2f(sizeX, sizeY);
        Texture tileTexture = new Texture();
        tileTexture.setSmooth(true);

        //Set image according to type
        switch (type){
            case WALL:
                blockType = BlockType.WALL;
                imageCache[0].setSmooth(true);
                tileTexture = imageCache[0];
                break;
            case FLOOR:
                blockType = BlockType.FLOOR;
                imageCache[1].setSmooth(true);
                tileTexture = imageCache[1];
                break;
            case DOOR:
                blockType = BlockType.DOOR;
                imageCache[2].setSmooth(true);
                tileTexture = imageCache[2];
                break;
            case START:
                blockType = BlockType.START;
                imageCache[3].setSmooth(true);
                tileTexture = imageCache[3];
                break;
            case FINISH:
                blockType = BlockType.FINISH;
                imageCache[4].setSmooth(true);
                tileTexture = imageCache[4];
                break;
            case VOID:
                blockType = BlockType.VOID;
                imageCache[5].setSmooth(true);
                tileTexture = imageCache[5];
                break;
            case CHARGE:
                blockType = BlockType.CHARGE;
                imageCache[6].setSmooth(true);
                tileTexture = imageCache[6];
                break;
            default:
                System.out.print("Block must have type defined");
                break;
        }

        this.setTexture(tileTexture);
        this.setSize(tileSize);
        this.setPosition(position);
    }

	public BlockType getTileType() {
        return blockType;
    }

    public void setTileType(Tile.BlockType blockType) {
        this.blockType = blockType;
    }

}
