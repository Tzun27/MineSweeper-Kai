package mine;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application{

	public static Board board = new Board();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		board.scene = new Scene(board.init_board());
		
		Image icon = new Image("/resources/icon.png");
		
		stage.getIcons().add(icon);
		stage.setTitle("Monster Sweeper");
		
		stage.setScene(board.scene);
		stage.show();
	}

}
