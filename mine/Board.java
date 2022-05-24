package mine;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Board{

	private static final int TILE_SIZE = 40;
	private static final int BOARD_WIDTH = 800;
	private static final int BOARD_HEIGHT = 800;
	
	private static final int HORIZONTAL_TILES = BOARD_WIDTH / TILE_SIZE;
	private static final int VERTICAL_TILES = BOARD_HEIGHT / TILE_SIZE;
	
	private Tile[][] grid = new Tile[HORIZONTAL_TILES][VERTICAL_TILES];
	private static String levelString = "1";
	public Scene scene;
	
	public int getTileSize() {
		return TILE_SIZE;
	}
	
	//create new board
	public Parent init_board() {
		Pane root = new Pane();
		root.setPrefSize(BOARD_WIDTH, BOARD_HEIGHT + 30);
		ProgressBar exp = new ProgressBar();
		Label experience = new Label("Exp:");
		Label level = new Label("Level: " + levelString);
		
		level.setLayoutX(600);
		level.setLayoutY(800);
		level.setFont(Font.font(20));
		
		experience.setLayoutX(0);
		experience.setLayoutY(800);
		experience.setFont(Font.font(20));
		
		exp.setPrefHeight(30);
		exp.setPrefWidth(500);
		exp.setLayoutX(50);
		exp.setLayoutY(800);
		exp.setProgress(0);
		
		root.getChildren().addAll(exp,experience,level);
		
		//generates tiles and places them into the grid
		for(int x = 0; x < HORIZONTAL_TILES; x++) {
			for(int y = 0; y < VERTICAL_TILES; y++) {
				Tile tile = new Tile(x, y, Math.random() < 0.2);
				
				grid[y][x] = tile;
				root.getChildren().add(tile);
			}
		}
		
		//check how many bombs are around the tile and adds corresponding text
		for(int x = 0; x < HORIZONTAL_TILES; x++) {
			for(int y = 0; y < VERTICAL_TILES; y++) {
				Tile tile = grid[y][x];
				
				if(tile.isBomb())
					continue;
				
				long bombsAroundTile = checkSurroundings(tile).stream().filter(t -> t.isBomb()).count();
				
				if(bombsAroundTile > 0) {
					tile.setText(String.valueOf(bombsAroundTile));
				}
					
			}
		}
		return root;
	}
	
	//method to obtain surrounding tiles of any one tile
	public List<Tile> checkSurroundings(Tile tile){
		List<Tile> surroundings = new ArrayList<>();
		
		/*there will be (at most) eight tiles surrounding any given tile
		  consider the target tile as being in the middle
		  the tiles surrounding the middle tile will have their coordinates expressed relative to middle tile
		  ex. middle [2][2] , top left [1][1]
		 */
		int[] coordinates = new int[] {
				//left side
				-1,-1,
				0,-1,
				1,-1,
				
				//middle
				-1,0,
				1,0,
				
				//right side
				-1,1,
				0,1,
				1,1
		};
		
		for(int i = 0; i < coordinates.length; i++) {
			int dy = coordinates[i];
			int dx = coordinates[++i];
			
			int newY = tile.getY() + dy;
			int newX = tile.getX() + dx;
			
			if(newX >= 0 && newX < HORIZONTAL_TILES  &&  newY >=0 && newY < VERTICAL_TILES)
				surroundings.add(grid[newY][newX]);
		}
		
		return surroundings;
	}

}
