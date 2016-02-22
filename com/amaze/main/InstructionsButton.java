package com.amaze.main;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * This class holds appropriate information about play button.
 */
public class InstructionsButton extends Button {

	private String background;

    /**
     * Construct a button with following parameters:
     *
     * @param xCord - x-coordinate of the button
     * @param yCord - y-coordinate of the button
     * @param width - width of the button
     * @param height - height of the button
     * @param window - reference to the main window
     */
    public InstructionsButton(float xCord, float yCord, float width, float height, Window window, MenuScene menu, String background) throws IOException {
        super(xCord, yCord, width, height, window, menu);

        this.background = background;
        getDefaultIcon().loadFromFile(Paths.get("res/menuGraphics/instructions.png"));
        getSelectedIcon().loadFromFile(Paths.get("res/menuGraphics/instructionssel.png"));
        getDefaultIcon().setSmooth(true);
        getSelectedIcon().setSmooth(true);

        this.setTexture(getDefaultIcon());
    }

    /**
     * This function changes the scene to LevelMenuScene when called.
     */
    public void performAction() {
        System.out.println("Instruction Button Pressed");
        try {
            InstructionScene instructionScene = new InstructionScene("Instructions", getWindow(), getMenu().getMusic(), background);
            getWindow().addScene(instructionScene);
			getWindow().setScene(getWindow().getArrayList().indexOf(instructionScene));
            getMenu().setRunning(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
