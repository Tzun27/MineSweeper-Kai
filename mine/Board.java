package mine;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Board{

	private static final int TILE_SIZE = 40;
	private static final int BOARD_WIDTH = 800;
	private static final int BOARD_HEIGHT = 800;
	
	private static final int HORIZONTAL_TILES = BOARD_WIDTH / TILE_SIZE;
	private static final int VERTICAL_TILES = BOARD_HEIGHT / TILE_SIZE;
	
	private Tile[][] grid = new Tile[HORIZONTAL_TILES][VERTICAL_TILES];
	public int openTiles = 0;
	
	private Image[] enemyPng = new Image[5];
	public ImagePattern[] ip = new ImagePattern[5];
	
	private Stage stage;
	public static Player player = new Player();
	public static Label level = new Label("Level: " + player.getPlayerLevel());
	public static ProgressBar exp = new ProgressBar();
	public static Label loseMsg = new Label("Game Over");
	public static Label winMsg = new Label("You Win!");
	public Scene scene;
	public static Button restart = new Button();
	
	public int getHorizontalTiles() {
		return HORIZONTAL_TILES;
	}
	
	public int getVerticalTiles() {
		return VERTICAL_TILES;
	}
	
	public int getTileSize() {
		return TILE_SIZE;
	}
	
	//create new board
	public Parent init_board() {
		Pane root = new Pane();
		root.setPrefSize(BOARD_WIDTH, BOARD_HEIGHT + 30);
		
		player = new Player();
		
		//load enemy designs into array
		enemyPng[0] = new Image("/resources/1.png");
		enemyPng[1] = new Image("/resources/2.png");
		enemyPng[2] = new Image("/resources/3.png");
		enemyPng[3] = new Image("/resources/4.png");
		enemyPng[4] = new Image("/resources/5.png");
		
		ip[0] = new ImagePattern(enemyPng[0]);
		ip[1] = new ImagePattern(enemyPng[1]);
		ip[2] = new ImagePattern(enemyPng[2]);
		ip[3] = new ImagePattern(enemyPng[3]);
		ip[4] = new ImagePattern(enemyPng[4]);
		
		Label experience = new Label("Exp:");
		
		level.setText("Level: " + player.getPlayerLevel());
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
		
		loseMsg.setVisible(false);
		loseMsg.setBackground(Background.fill(Color.LIGHTGREY));
		loseMsg.setBorder(Border.stroke(Color.BLACK));
		loseMsg.setLayoutX(200);
		loseMsg.setLayoutY(340);
		loseMsg.setFont(Font.font(80));
		
		winMsg.setVisible(false);
		winMsg.setBackground(Background.fill(Color.LIGHTGREY));
		winMsg.setBorder(Border.stroke(Color.BLACK));
		winMsg.setLayoutX(200);
		winMsg.setLayoutY(340);
		winMsg.setFont(Font.font(80));
		
		restart.setLayoutX(348);
		restart.setLayoutY(460);
		restart.setPrefSize(100, 50);
		restart.setText("Back to Start");
		restart.setVisible(false);
		restart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Parent root;
				try {
					root = FXMLLoader.load(getClass().getResource("/resources/Menu.fxml"));
					stage = (Stage)((Node) event.getSource()).getScene().getWindow();
			    	scene = new Scene(root);
			    	stage.setScene(scene);
			    	stage.centerOnScreen();
			    	stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    	
			}
		});
		
		root.getChildren().addAll(exp, experience, level, loseMsg, restart);
		
		//generates tiles and places them into the grid
		for(int x = 0; x < HORIZONTAL_TILES; x++) {
			for(int y = 0; y < VERTICAL_TILES; y++) {
				Tile tile = new Tile(x, y, Math.random() <= 0.2);
				
				grid[y][x] = tile;
				root.getChildren().add(tile);
			}
		}
		
		//sets enemy levels
		for(int x = 0; x < HORIZONTAL_TILES; x++) {
			for(int y = 0; y < VERTICAL_TILES; y++) {
				double rand = Math.random();
				Tile tile = grid[y][x];
				
				if(!tile.isEnemy())
					continue;
				
				if(rand <= 0.45 ) {
					tile.setLevel(1);
					//tile.square.setFill(ip[0]);
					tile.setText("One");
				}
				
				else if(rand > 0.45 && rand <= 0.7) {
					tile.setLevel(2);
					//tile.square.setFill(ip[1]);
					tile.setText("Two");
					}
				
				
				else if(rand > 0.7 && rand <= 0.85) {
					tile.setLevel(3);
					//tile.square.setFill(ip[2]);
					tile.setText("Three");
				}
				
				else if(rand > 0.85 && rand <= 0.95) {
					tile.setLevel(4);
					//tile.square.setFill(ip[3]);
					tile.setText("Four");
				}
				
				else if(rand > 0.95 && rand <= 1) {
					tile.setLevel(5);
					//tile.square.setFill(ip[4]);
					tile.setText("Five");
				}
			}
		}
		
		//check total level of enemies around the tile and adds corresponding text
		for(int x = 0; x < HORIZONTAL_TILES; x++) {
			for(int y = 0; y < VERTICAL_TILES; y++) {
				Tile tile = grid[y][x];
				
				if(tile.isEnemy())
					continue;
				
				long enemiesAroundTile = checkSurroundings(tile)
										.stream()
										.filter(t -> t.isEnemy())
										.mapToInt(t -> t.getEnemyLevel())
										.sum();
				
				if(enemiesAroundTile > 0) {
					tile.setText(String.valueOf(enemiesAroundTile));
				}
					
			}
		}
		return root;
	}
	
	//method to obtain surrounding tiles of any one tile
	public List<Tile> checkSurroundings(Tile tile){
		List<Tile> surroundings = new ArrayList<>();
		
		/* there will be (at most) eight tiles surrounding any given tile
		   consider the target tile as being in the middle
		   the tiles surrounding the middle tile will have their coordinates expressed relative to middle tile
		   ex. middle [2][2] , top left [1][1] */
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
