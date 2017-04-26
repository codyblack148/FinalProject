import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.animation.*;
import java.io.File;
import java.util.Random;
import javafx.embed.swing.JFXPanel;

public class View extends Application {
	public Stage stage;
	Image rocketOn = new Image( getClass().getResource("MoonLanderAcc.png").toExternalForm());
	Image target = new Image( getClass().getResource("targettemp.png").toExternalForm());
	ImageView image = new ImageView(target);
	Random rand = new Random();
	int targetLocation;
	
	private MediaPlayer audioPlayer;
	private Media audio;
	
	
	public void start(Stage primaryStage) {
		Pane startPane = new Pane();
		Scene startScreen = new Scene(startPane,800,1186);
		startScreen.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		startPane.setId("LastJedi"); //add what we want to be in the bg of screen.
		Button sbtn1 = new Button("Begin Game");
		Button sbtn2 = new Button("Cancel");
		Label startHeader = new Label("Tatooine Lander - A Moon Landing Simulator\n Designed by: Cody Black and Chris Marek.");
		startHeader.setFont(new Font("Arial", 30));
		startHeader.setTextFill(Color.DEEPSKYBLUE);
		startHeader.setTranslateX(120);
		sbtn1.setTranslateX(150);
		sbtn1.setTranslateY(750);
		sbtn2.setTranslateX(600);
		sbtn2.setTranslateY(750); 
		startPane.getChildren().addAll(sbtn1,sbtn2,startHeader);
		   sbtn1.setOnAction((event) -> {
			   startGame(primaryStage);
			});
		   sbtn2.setOnAction((event) -> {
			    System.out.println("Quit");
			    primaryStage.close();
			});
		primaryStage.setTitle("Tatooine Lander Start Screen");
		primaryStage.setScene(startScreen);
		
		File file = new File("RebellionsAreBuiltonHope.mp3");
		audio = new Media(file.toURI().toString());
		audioPlayer = new MediaPlayer(audio);
		audioPlayer.play();
		
		primaryStage.show();
		// startGame(primaryStage);
       }
	public static void main(String[] args){
		launch(args);
	}

	private int generateTarget(Pane layer){
		int x = rand.nextInt(601) + 100;
		image.setFitHeight(100);
		image.setFitWidth(100);
		image.relocate(x, 650);
		layer.getChildren().add(image);
		return x;
	}
void removeTarget(Pane layer, Node target){
		layer.getChildren().remove(target);
	}
	void cleanup(AnimationTimer a, MoonLander lander){
		lander.reset();
		a.stop();
		
	}
void restart(Stage stage,AnimationTimer a,MoonLander lander){
	cleanup(a,lander);
	startGame(stage);
}
void startGame(Stage stage){

	/* 
	 * Main game scene and initialization.
	 */
	
	BorderPane root = new BorderPane();
	Scene scene = new Scene(root,800,800);
	scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
	root.setId("MainImage");
	BorderPane topGUI = new BorderPane();
	root.setTop(topGUI);
	topGUI.setId("title");
	MoonLander lander = new MoonLander(rocketOn,root);
	targetLocation = generateTarget(root);
	//Top left Vbox of the GUI that displays the stats for the rocket.
			VBox leftStats = new VBox(12);
			leftStats.setPrefWidth(200);
			leftStats.setPrefHeight(150);
			leftStats.setStyle("-fx-background-color: black;");
			Label l1 = new Label("Fuel: ");
			Label l2 = new Label("Velocity: ");
			Label l3 = new Label("Thrust %: ");
			Label l4 = new Label("Orientation: ");
			l1.setTextFill(Color.WHITE);
			l2.setTextFill(Color.WHITE);
			l3.setTextFill(Color.WHITE);
			l4.setTextFill(Color.WHITE);
			leftStats.getChildren().addAll(l1,l2,l3,l4);
			topGUI.setLeft(leftStats);
			//Top right Vbox of the GUI, shows height in meters and elapsed time.
			VBox rightStats = new VBox(12);
			rightStats.setPrefWidth(200);
			rightStats.setPrefHeight(150);
			rightStats.setStyle("-fx-background-color: black");
			Label l5 = new Label("Time Elapsed: ");
			Label l6 = new Label("Height: ");
			l5.setTextFill(Color.WHITE);
			l6.setTextFill(Color.WHITE);
			rightStats.getChildren().addAll(l5,l6);
			topGUI.setRight(rightStats);
			
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
				lander.processInput(e.getCode(),root);
			
			}
			
		});
		//TimeTracker timer = new TimeTracker();
			
			stage.setScene(scene);
			stage.show();
	
			/* 
			 * Win, play again or quit scene setup. Initialization located in AnimationTimer.
			 */
			Pane winPlayAgain = new Pane();
			Scene scene2 = new Scene(winPlayAgain,400,363);
			scene2.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			winPlayAgain.setId("Vader");
			Button btn1 = new Button("Play Again?");
			Button btn2 = new Button("Quit");
			btn1.setTranslateX(280);
			btn1.setTranslateY(325);
			btn2.setTranslateX(25);
			btn2.setTranslateY(325); 
			winPlayAgain.getChildren().addAll(btn1,btn2);
			
			/* 
			 * Land, play again or quit scene setup. Initialization located in AnimationTimer.
			 */
			Pane landPlayAgain = new Pane();
			Scene scene3 = new Scene(landPlayAgain,500,206);
			scene3.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			landPlayAgain.setId("Finn");
			Button btn3 = new Button("Play Again?");
			Button btn4 = new Button("Quit");
			btn3.setTranslateX(400);
			btn3.setTranslateY(175);
			btn4.setTranslateX(15);
			btn4.setTranslateY(175); 
			landPlayAgain.getChildren().addAll(btn3,btn4);
			/* 
			 * Crash, play again or quit scene setup. Initialization located in AnimationTimer.
			 */
			Pane crashPlayAgain = new Pane();
			Scene scene4 = new Scene(crashPlayAgain,360,240);
			scene4.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			crashPlayAgain.setId("Luke");
			Button btn5 = new Button("Play Again?");
			Button btn6 = new Button("Quit");
			btn5.setTranslateX(270);
			btn5.setTranslateY(210);
			btn6.setTranslateX(0);
			btn6.setTranslateY(210); 
			crashPlayAgain.getChildren().addAll(btn5,btn6);
	
	
	
	/* 
	 * Game animation loop initialization.
	 */
	AnimationTimer gameLoop = new AnimationTimer() {

        @Override
        public void handle(long now) {
        	
        	l1.setText("Fuel: "+ lander.getFuel());
        	l2.setText("Velocity: "+ lander.getVelocity());
        	l3.setText("Thrust %: "+ lander.getThrust());
        	l4.setText("Direction Angle: "+ lander.getDirectionAngle());
        	//l5.setText("Time Elapsed: "+timer.getTime());
        	l6.setText("Height: " + lander.getHeight());
        	
        	lander.updateLocation(lander.getX(), lander.getY(), lander.getDirectionAngle());
        	lander.move();
        	lander.thrust(lander.getThrust());
        	
        	if(lander.getY()>650){ //Hits the Ground
        		
        		if(lander.getDirectionAngle() < 20 || lander.getDirectionAngle() > 345){//Check to see if it Crashed (check Velocity and angles)
        			System.out.println(targetLocation);
        			if((targetLocation - 75) < lander.getX() && lander.getX() < (targetLocation+75)){ //Is it on the target
        				
                			System.out.println("Hit the Target!");
                			stage.setScene(scene2);
                			stage.show();
                		}
        			System.out.println("You landed.");
        			stage.setScene(scene3);
        			stage.show();
        		}
        		else{
        			System.out.println("You Crashed.");
        			stage.setScene(scene4);
        			stage.show();
        		}
        		
        		stop();
        	}
        	

        }

    };
   gameLoop.start();
   
   btn1.setOnAction((event) -> {
	   restart(stage, gameLoop, lander);
	});
   btn2.setOnAction((event) -> {
	    System.out.println("Quit");
	    stage.close();
	    System.exit(0);

	});
   btn3.setOnAction((event) -> {
	   restart(stage, gameLoop, lander);
	});
   btn4.setOnAction((event) -> {
	    System.out.println("Quit");
	    stage.close();
	    System.exit(0);

	});
   btn5.setOnAction((event) -> {
	   restart(stage, gameLoop, lander);
	});
   btn6.setOnAction((event) -> {
	    System.out.println("Quit");
	    stage.close();
	    System.exit(0);

	});
}
	
}

