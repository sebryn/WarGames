package application;
	
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;


public class Main extends Application {
	//branch test
	private Stage window;
	private Scene titleScene, settingsScene, gameScene;
	private ComboBox<String> backgroundDropDown = new ComboBox<>();
	private TextField displayNameField = new TextField("User");
	private Button saveBtn = new Button("Save");
	private Button cancelBtn = new Button("Cancel");
	private char whoseTurn = 'X';
	// Create and initialize cell
	private Cell[][] cell =  new Cell[3][3];
	// Create and initialize a status label
	 private Label lblStatus = new Label("X's turn to play");
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			window = primaryStage;
			
			BorderPane titlePane = new BorderPane();
			titlePane.setCenter(getImage());
			titlePane.setBottom(getTitleButtons());
			titleScene = new Scene(titlePane,400,400);
			titleScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			BorderPane settingsPane = new BorderPane();
			settingsPane.setCenter(getSettings());
			settingsPane.setBottom(getSettingsButtons());
			settingsScene = new Scene(settingsPane, 400, 400);
			settingsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			GridPane gameGridPane = new GridPane();
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++)
					gameGridPane.add(cell[i][j] = new Cell(),  j,  i);	
			
			BorderPane gameBorderPane = new BorderPane();
			gameBorderPane.setCenter(gameGridPane);
			gameBorderPane.setBottom(lblStatus);	
			gameScene = new Scene(gameBorderPane, 450, 170);
			
			window.getIcons().add(new Image("WarGamesIcon.png"));
			window.setTitle("War Games");
			window.setScene(titleScene);
			window.show();
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private GridPane getSettings() {	
		GridPane settingsPane = new GridPane();
		settingsPane.setHgap(5);
		settingsPane.setVgap(5);
		settingsPane.add(new Label("Display Name:"), 0, 0);
		settingsPane.add(displayNameField, 1, 0);
		settingsPane.add(new Label("Background:"), 0, 1);
		settingsPane.add(backgroundDropDown, 1, 1);
		settingsPane.setAlignment(Pos.CENTER);
		return settingsPane;
		
	}
	
	private HBox getTitleButtons() {
		HBox hBox = new HBox(15);
		hBox.setPadding(new Insets(15,15,15,15));
		hBox.setAlignment(Pos.CENTER);
		Button playBtn = new Button("Play");
		Button settingsBtn = new Button("Settings");
		playBtn.setId("round-red");
		settingsBtn.setId("round-red");
		playBtn.setOnAction(e -> window.setScene(gameScene));
		settingsBtn.setOnAction(e -> window.setScene(settingsScene));
		hBox.getChildren().addAll(playBtn, settingsBtn);
		return hBox;
	}
	
	private HBox getSettingsButtons() {
		HBox hBox = new HBox(15);
		hBox.setPadding(new Insets(15,15,15,15));
		hBox.setAlignment(Pos.CENTER);
		Button cancelBtn = new Button("Cancel");
		Button saveBtn = new Button("Save");
		cancelBtn.setId("round-red");
		saveBtn.setId("round-red");
		cancelBtn.setOnAction(e -> window.setScene(titleScene));
		hBox.getChildren().addAll(cancelBtn, saveBtn);
		return hBox;
	}
	
	
	private HBox getImage() {
		HBox hBox = new HBox(25);
		ImageView imageView = new ImageView(new Image("WarGamesTitle.png"));
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().add(imageView);
		return hBox;
	}
	
	  /** Determine if the cell are all occupied */
	  public boolean isFull() {
	    for (int i = 0; i < 3; i++)
	      for (int j = 0; j < 3; j++)
	        if (cell[i][j].getToken() == ' ')
	          return false;

	    return true;
	  }
	  
	  /** Determine if the player with the specified token wins */
	  public boolean isWon(char token) {
	    for (int i = 0; i < 3; i++)
	      if (cell[i][0].getToken() == token
	          && cell[i][1].getToken() == token
	          && cell[i][2].getToken() == token) {
	        return true;
	      }
	    for (int j = 0; j < 3; j++)
	        if (cell[0][j].getToken() ==  token
	            && cell[1][j].getToken() == token
	            && cell[2][j].getToken() == token) {
	          return true;
	        }

	      if (cell[0][0].getToken() == token 
	          && cell[1][1].getToken() == token        
	          && cell[2][2].getToken() == token) {
	        return true;
	      }

	      if (cell[0][2].getToken() == token
	          && cell[1][1].getToken() == token
	          && cell[2][0].getToken() == token) {
	        return true;
	      }

	      return false;
	    }
	  
	// An inner class for a cell
	  public class Cell extends Pane {
	    // Token used for this cell
	    private char token = ' ';

	    public Cell() {
	      setStyle("-fx-border-color: black"); 
	      this.setPrefSize(800, 800);
	      this.setOnMouseClicked(e -> handleMouseClick());
	    }

	    /** Return token */
	    public char getToken() {
	      return token;
	    }
	    
	    /** Set a new token */
	    public void setToken(char c) {
	      token = c;
	      
	      if (token == 'X') {
	        Line line1 = new Line(10, 10, 
	          this.getWidth() - 10, this.getHeight() - 10);
	        line1.endXProperty().bind(this.widthProperty().subtract(10));
	        line1.endYProperty().bind(this.heightProperty().subtract(10));
	        Line line2 = new Line(10, this.getHeight() - 10, 
	          this.getWidth() - 10, 10);
	        line2.startYProperty().bind(
	          this.heightProperty().subtract(10));
	        line2.endXProperty().bind(this.widthProperty().subtract(10));
	        
	        // Add the lines to the pane
	        this.getChildren().addAll(line1, line2); 
	      }
	      else if (token == 'O') {
	        Ellipse ellipse = new Ellipse(this.getWidth() / 2, 
	          this.getHeight() / 2, this.getWidth() / 2 - 10, 
	          this.getHeight() / 2 - 10);
	        ellipse.centerXProperty().bind(
	          this.widthProperty().divide(2));
	        ellipse.centerYProperty().bind(
	            this.heightProperty().divide(2));
	        ellipse.radiusXProperty().bind(
	            this.widthProperty().divide(2).subtract(10));        
	        ellipse.radiusYProperty().bind(
	            this.heightProperty().divide(2).subtract(10));   
	        ellipse.setStroke(Color.BLACK);
	        ellipse.setFill(Color.WHITE);
	        
	        getChildren().add(ellipse); // Add the ellipse to the pane
	      }
	    }
	    /* Handle a mouse click event */
	    private void handleMouseClick() {
	      // If cell is empty and game is not over
	      if (token == ' ' && whoseTurn != ' ') {
	        setToken(whoseTurn); // Set token in the cell

	        // Check game status
	        if (isWon(whoseTurn)) {
	          lblStatus.setText(whoseTurn + " won! The game is over");
	          whoseTurn = ' '; // Game is over
	        }
	        else if (isFull()) {
	          lblStatus.setText("Draw! The game is over");
	          whoseTurn = ' '; // Game is over
	        }
	        else {
	          // Change the turn
	          whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
	          // Display whose turn
	          lblStatus.setText(whoseTurn + "'s turn");
	        }
	      }
	    }
	  }
	    
	
	public static void main(String[] args) {
		launch(args);
	}
}
