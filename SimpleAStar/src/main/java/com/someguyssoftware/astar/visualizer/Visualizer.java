/**
 * 
 */
package com.someguyssoftware.astar.visualizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.javafx.scene.control.IntegerField;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * @author Mark Gottschling on Sep 25, 2020
 *
 */
public class Visualizer extends Application {
	private static final Logger LOGGER = LogManager.getLogger(Visualizer.class);
	
	private int stageWidth = 1000;
	private int stageHeight = 600;
	private int startX = 0;
	private int startY = 0;
	private int tileWidth = 5;
	private int tileHeight = 5;
	
	private int mapWidth;
	private int mapHeight;
	private boolean[][] cellMap;
	
	private HBox mainBox = new HBox();
	private HBox mapBox = new HBox();
	private VBox inputBox = new VBox();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		// setup the stage
		stage.setWidth(stageWidth);
		stage.setHeight(stageHeight);
		stage.setTitle("Simple A* Visualizer");

		buildInputPane(inputBox);
		buildMapPanel(mapBox);
		
		mainBox.getChildren().addAll(inputBox, mapBox);

		Scene scene = new Scene(mainBox);
		stage.setScene(scene);

		// display the application
		stage.show();
	}

	private void buildInputPane(VBox pane) {
		HBox widthBox = addIntegerInput("Width:", 96);
		HBox heightBox = addIntegerInput("Height:", 96);
		
		pane.setPadding(new Insets(5, 5, 5, 5));		
		pane.setMinWidth(stageWidth/2);
		pane.setMaxWidth(stageWidth/2);
		pane.getChildren().addAll(widthBox, heightBox);
	}

	private void buildMapPanel(HBox mapBox) {
		// container for all the visual elements
		Group group = new Group();
		
		// clear any children
		mapBox.getChildren().clear();
		
		// initialize size of map // TODO get values from input
		mapWidth = 96;
		mapHeight = 96;
		
		// intialize cell map
		cellMap = new boolean[mapWidth][mapHeight];
		
		// create background
		Rectangle bg = new Rectangle(0, 0, mapWidth *tileWidth, mapHeight * tileHeight);
		bg.setFill(Color.WHITE);
		group.getChildren().add(bg);
		
		// draw the grid
		for (int x = 0; x < mapWidth; x++) {
			for (int y = 0; y < mapHeight; y++) {
				// TODO interrogate the grid -> true / false creates empty / blocked
				// create a tile
				Rectangle tile = createTile(x, y, Color.WHITE);
				group.getChildren().add(tile);
			}
		}
		
		// TODO draw the path on a new layer
		
		mapBox.getChildren().add(group);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param fill
	 * @return
	 */
	public Rectangle createTile(int x, int y, Color fill) {
		Rectangle tile = new Rectangle(getXPos(startX, x), getYPos(startY, y), tileWidth, tileHeight);
		tile.setStroke(Color.BLACK);
		tile.setFill(fill);
		return tile;
	}
	
	public int getXPos(int startX, int x) {
		return startX + (x * tileWidth);
	}
	public int getYPos(int startY, int y) {
		return startY + (y * tileHeight);
	}
	
	/**
	 * 
	 * @param labelText
	 * @param defaultValue
	 * @return
	 */
	private HBox addIntegerInput(String labelText, int defaultValue) {

		Label label = new Label(labelText);
		IntegerField field = new IntegerField(defaultValue);
//		TextField field = new TextField(defaultValue);
		HBox hBox = new HBox(label, field);
		
		// set the label properties
		label.setMinWidth(120);
		label.setMaxWidth(120);
		label.setWrapText(true);
		label.setTextAlignment(TextAlignment.LEFT);		

		// set the field properties
		field.setMinSize(30, 15);
		field.setMaxSize(50, 20);

		hBox.setPadding(new Insets(5, 0, 0, 0));
		hBox.setSpacing(5);
		
		return hBox;
	}
}
