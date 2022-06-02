package mine;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller {
	
	private Stage stage;
    private Scene scene;
    private Parent root;
    private Board board = Main.board;

    @FXML
    private Button exitButton;
    private Button startButton;
    private Button rulesButton;
    private Button backButton;
    private Button switchtoScene2;
    
    @FXML
    private AnchorPane scenePane ;
    
    
    public void exit (ActionEvent event) {
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("Are you sure you want to exit?");
//		alert.setContentText("You sure you want to exit?");
		
		if (alert.showAndWait().get() == ButtonType.OK){

       	stage = (Stage) scenePane.getScene().getWindow();
    	System.out.println("You have exited the game!");
    	stage.close();
		}

    }
    //exchange to Scene2
    public void start (ActionEvent event) throws IOException {
    	stage = (Stage)((Node) event.getSource()).getScene().getWindow();
    	scene = board.scene;
    	stage.setScene(scene);
    	stage.centerOnScreen();
    	stage.show();
    	
    }
    //back to menu
    public void backtomenu (ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/resources/Menu.fxml"));
    	stage = (Stage)((Node) event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }

   
	//exchange to rules
		public void rules(ActionEvent event) throws IOException {
			Parent root = FXMLLoader.load(getClass().getResource("/resources/Rules.fxml"));
	    	stage = (Stage)((Node) event.getSource()).getScene().getWindow();
	    	scene = new Scene(root);
	    	stage.setScene(scene);
	    	stage.show();
	    }
		  
    
}