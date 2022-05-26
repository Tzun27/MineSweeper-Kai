package mine;

import java.util.List;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Tile extends StackPane{
	private Board board = Minesweeper.board;
	private int x, y;
	private boolean isBomb;
	private boolean isOpen = false;
	private int enemyLevel = 0;
	
	//minus two for the border
	private Rectangle square = new Rectangle(board.getTileSize() - 2, board.getTileSize() - 2);
	private Text text = new Text();
	
	public boolean isBomb() {
		return isBomb;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setText(String bombNum) {
		text.setText(bombNum);
	}
	
	public void setLevel(int level) {
		enemyLevel = level;
	}
	
	public Tile(int x, int y, boolean isBomb){
		this.x = x;
		this.y = y;
		this.isBomb = isBomb;
		
		square.setStroke(Color.WHITE);
		text.setFont(Font.font(12));
		text.setText(isBomb ? "One" : "");
		
		text.setVisible(false);
		
		getChildren().addAll(square, text);
		
		setTranslateX(x * board.getTileSize());
		setTranslateY(y * board.getTileSize());
		
		setOnMouseClicked(e -> {
			openTile();
			checkWin();
		});
		
	}
	
	
	public void checkWin() {
		if(board.openTiles == Board.HORIZONTAL_TILES * Board.VERTICAL_TILES) {
			System.out.println("You win!");
			return;
		}
	}
	
	//method for flipping open a tile, if tile is empty it'll open surrounding empty tiles
	public void openTile() {
		
		if(isOpen)
			return;
		
		board.openTiles++;
		
		if(isBomb) {
			System.out.println("Game Over");
		}
		
		isOpen = true;
		text.setVisible(true);
		square.setFill(null);
		
		if(text.getText().isEmpty()) {
			List<Tile> surroundings = board.checkSurroundings(this);
			for(Tile t : surroundings) {
				t.openTile();
			}
		}
	}
	
}
