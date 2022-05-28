package mine;

import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Tile extends StackPane{
	private Board board = Minesweeper.board;
	private Player player = Minesweeper.board.player;
	private Label level = Minesweeper.board.level;
	
	private int x, y;
	private boolean isEnemy;
	private boolean isOpen = false;
	private int enemyLevel = 0;
	
	//minus two for the border
	public Rectangle square = new Rectangle(board.getTileSize() - 2, board.getTileSize() - 2);
	private Text text = new Text("");
	private ProgressBar exp = Minesweeper.board.exp;
	
	public boolean isEnemy() {
		return isEnemy;
	}
	
	public int getEnemyLevel() {
		return enemyLevel;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setText(String enemyType) {
		text.setText(enemyType);
	}
	
	public void setLevel(int level) {
		enemyLevel = level;
	}
	
	public Tile(int x, int y, boolean isEnemy){
		this.x = x;
		this.y = y;
		this.isEnemy = isEnemy;
		
		square.setStroke(Color.WHITE);
		text.setFont(Font.font(12));
		text.setText(isEnemy ? "One" : "");
		
		text.setVisible(false);
		
		getChildren().addAll(square, text);
		
		setTranslateX(x * board.getTileSize());
		setTranslateY(y * board.getTileSize());
		
		setOnMouseClicked(e -> {
			openTile();
			checkWin();
		});
		
	}
	
	//Player gains different amount of exp depending on type of enemy defeated as well as own level
	public void calculateExpGain(int playerLevel) {
		switch (playerLevel) {
		case 1: {
			lv1ExpGain();
			break;
		}
		case 2: {
			lv2ExpGain();
			break;
		}
		case 3: {
			lv3ExpGain();
			break;
		}
		case 4: {
			lv4ExpGain();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + enemyLevel);
		}
	}
	
	public void lv1ExpGain() {
		player.addPlayerExp(0.2);
		return;
	}
	
	public void lv2ExpGain() {
		switch (enemyLevel) {
		case 1: {
			player.addPlayerExp(0.15);;
			break;
		}
		case 2: {
			player.addPlayerExp(0.25);;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + enemyLevel);
		}
		return;
	}

	public void lv3ExpGain() {
		switch (enemyLevel) {
		case 1: {
			player.addPlayerExp(0.1);;
			break;
		}
		case 2: {
			player.addPlayerExp(0.15);;
			break;
		}
		case 3: {
			player.addPlayerExp(0.2);;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + enemyLevel);
		}
		return;
	}

	public void lv4ExpGain() {
		switch (enemyLevel) {
		case 1: {
			player.addPlayerExp(0.05);;
			break;
		}
		case 2: {
			player.addPlayerExp(0.1);;
			break;
		}
		case 3: {
			player.addPlayerExp(0.15);;
			break;
		}
		case 4: {
			player.addPlayerExp(0.2);;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + enemyLevel);
		}
		return;
	}

	
	public void checkWin() {
		if(board.openTiles == board.getHorizontalTiles() * board.getVerticalTiles()) {
			System.out.println("You win!");
			return;
		}
	}
	
	//method for flipping open a tile, if tile is empty it'll open surrounding empty tiles
	public void openTile() {
		
		if(isOpen)
			return;
		
		board.openTiles++;
		
		if(isEnemy) {
			if(player.getPlayerLevel() < enemyLevel) {
				System.out.println("Game Over");
			}
			
			calculateExpGain(player.getPlayerLevel());
			exp.setProgress(player.getPlayerExp() % 1.0);
			player.updateLevel();
			level.setText("Level: " + player.getPlayerLevel());
		}
		
		//System.out.println(player.getPlayerExp());
		//System.out.println(player.getPlayerLevel());
		
		isOpen = true;
		
		if(!isEnemy)
			text.setVisible(true);
		
		switch (enemyLevel) {
		case 0: {
			square.setFill(null);
			break;
		}
		case 1: {
			square.setFill(board.ip[0]);
			break;
		}
		case 2: {
			square.setFill(board.ip[1]);
			break;
		}
		case 3: {
			square.setFill(board.ip[2]);
			break;
		}
		case 4: {
			square.setFill(board.ip[3]);
			break;
		}
		case 5: {
			square.setFill(board.ip[4]);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + enemyLevel);
		}
		
		if(text.getText().isEmpty()) {
			List<Tile> surroundings = board.checkSurroundings(this);
			for(Tile t : surroundings) {
				t.openTile();
			}
		}
	}
	
}
