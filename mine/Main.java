package mine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Main extends Application{

	public static Board board = new Board();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		board.scene = new Scene(board.init_board());
		
		Parent root = FXMLLoader.load(getClass().getResource("/resources/Menu.fxml"));
		Scene menu = new Scene(root);
		String css = this.getClass().getResource("/resources/application.css").toExternalForm();
		menu.getStylesheets().add(css);
		Image background = new Image("/resources/background.jpg");
		ImageView iv = new ImageView(background);
		Group startUp = new Group();
		startUp.getChildren().add(iv);
		
		Image icon = new Image("/resources/icon.png");
		
		stage.getIcons().add(icon);
		stage.setResizable(false);
		stage.setTitle("Monster Sweeper");
		
		stage.setScene(menu);
		stage.show();
	}

}
