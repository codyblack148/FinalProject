import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.animation.*;
import java.io.File;
import java.util.Random;

import javafx.util.Duration;

/**
 * @author Cody Black  cblack4@cbu.edu
 * @author Chris Marek jmarek@cbu.edu
 */
public class View extends Application {
	public Stage stage;
	
	/**
	 * Declarations for variables. Both Images are for the MoonLander and the target to be hit.
	 * Random variable will be utilized to randomly generate target location. The Media variables
	 * are utilized to play background music. The pause check and temp times are used exclusively
	 * for the pause button. DO NOT UTILIZE THOSE VARIABLES ELSEWHERE. TimeTracker object
	 * is declared and tracks time for the time elapsed.
	 * 
	 */
	Image rocketOn = new Image( getClass().getResource("LanderFlames.png").toExternalForm());
	Image target = new Image( getClass().getResource("stormTrooperTarget.png").toExternalForm());
	ImageView image = new ImageView(target);
	Random rand = new Random();
	int targetLocation;
	private MediaPlayer audioPlayer;
	private Media audio;
	int pauseCheck = 0; 
	float tempLanderTime;
	float tempGameTime;
	private TimeTracker lTime = new TimeTracker(1,1);
	
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	public void start(Stage primaryStage) {
		
		
		Pane startPane = new Pane();
		Scene startScreen = new Scene(startPane,800,800);
		startScreen.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		startPane.setId("LastJedi"); //add what we want to be in the bg of screen.
		Button sbtn1 = new Button("Begin Game");
		Button sbtn2 = new Button("Cancel");
		sbtn1.setTranslateX(150);
		sbtn1.setTranslateY(750);
		sbtn2.setTranslateX(600);
		sbtn2.setTranslateY(750); 
		startPane.getChildren().addAll(sbtn1,sbtn2);
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
		//Makes it Run COntinuasly.
		audioPlayer.setOnEndOfMedia(new Runnable() {
       			public void run() {
         	audioPlayer.seek(Duration.ZERO);
       		}
   		});
		
		audioPlayer.play();
		
		primaryStage.show();
		// startGame(primaryStage);
	}
	
	/**
	 * @param args Main args.
	 */
	public static void main(String[] args){
		launch(args);
	}

	/**Method used for randomly generating and placing target on screen.
	 * @param layer Pane layer that the target will be appended to.
	 * @return Target location.
	 */
	private int generateTarget(Pane layer){
		int x = rand.nextInt(601) + 100;
		image.setFitHeight(100);
		image.setFitWidth(100);
		image.relocate(x, 650);
		layer.getChildren().add(image);
		return x;
	}
	
	/**Restarts the game by resetting the MoonLander, stopping the AnimationTimer, resetting
	 * the timer, and calling startGame.
	 * @param stage Stage for which game is being restarted.
	 * @param a AnimationTimer object that is being stopped.
	 * @param lander MoonLander object to be reset.
	 */
	private void restart(Stage stage,AnimationTimer a,MoonLander lander){
		lander.reset();
		a.stop();
		lTime.reset();
		startGame(stage);
	}
	/**Contains all the code to set up all scenes for the game, as well as the AnimationTimer
	 * that runs the game.
	 * @param stage Stage which is to be utilized for the game.
	 */
	private void startGame(Stage stage){

		/* 
		 * Setup for the root BorderPane of the game, the background image, and the pause button.
		 * A second BorderPane is put into the top of the root BorderPane .
		 * The MoonLander and target are both initialized here and added
		 * to the root. Another BorderPane is put into the top of the root, and then
		 * a VBox is added to the right side of that BorderPane. This allows all of the 
		 * labels for the statistics to be put into the top right corner of the screen inside
		 * of the VBox.
		 */
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root,800,800);
		scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		root.setId("MainImage");
		Button pause = new Button("Pause");
		root.setBottom(pause);
		BorderPane topGUI = new BorderPane();
		root.setTop(topGUI);
		MoonLander lander = new MoonLander(rocketOn,root);
		targetLocation = generateTarget(root);
	
		
			VBox rightStats = new VBox(5);
			rightStats.setPrefWidth(200);
			rightStats.setPrefHeight(150);
			rightStats.setStyle("-fx-background-color: black");
			Label l1 = new Label("Fuel: ");
			Label l2 = new Label("Velocity: ");
			Label l3 = new Label("Thrust %: ");
			Label l4 = new Label("Orientation: ");
			l1.setTextFill(Color.WHITE);
			l2.setTextFill(Color.WHITE);
			l3.setTextFill(Color.WHITE);
			l4.setTextFill(Color.WHITE);
			Label l5 = new Label("Time Elapsed: ");
			Label l6 = new Label("Height: ");
			l5.setTextFill(Color.WHITE);
			l6.setTextFill(Color.WHITE);
			rightStats.getChildren().addAll(l1,l2,l3,l4,l5,l6);
			topGUI.setRight(rightStats);
		
			/* 
			 * Listener for KeyEvents. Key events are passed to the processInput method
			 * of the MoonLander class to be further processed.
			 */	
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
				lander.processInput(e.getCode(),root);
				
			}
			
		});
		
			
			stage.setScene(scene);
			stage.show();
	
			/* 
			 * Win, play again or quit scene setup. Initialization located in AnimationTimer.
			 */
			Pane winPlayAgain = new Pane();
			Scene scene2 = new Scene(winPlayAgain,800,800);
			scene2.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			winPlayAgain.setId("Vader");
			Button btn1 = new Button("Play Again?");
			Button btn2 = new Button("Quit");
			btn1.setTranslateX(375);
			btn1.setTranslateY(750);
			btn2.setTranslateX(310);
			btn2.setTranslateY(750); 
			winPlayAgain.getChildren().addAll(btn1,btn2);
			
			/* 
			 * Land, play again or quit scene setup. Initialization located in AnimationTimer.
			 */
			Pane landPlayAgain = new Pane();
			Scene scene3 = new Scene(landPlayAgain,800,800);
			scene3.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			landPlayAgain.setId("Finn");
			Button btn3 = new Button("Play Again?");
			Button btn4 = new Button("Quit");
			btn3.setTranslateX(375);
			btn3.setTranslateY(750);
			btn4.setTranslateX(310);
			btn4.setTranslateY(750); 
			landPlayAgain.getChildren().addAll(btn3,btn4);
			/* 
			 * Crash, play again or quit scene setup. Initialization located in AnimationTimer.
			 */
			Pane crashPlayAgain = new Pane();
			Scene scene4 = new Scene(crashPlayAgain,800,800);
			scene4.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			crashPlayAgain.setId("Luke");
			Button btn5 = new Button("Play Again?");
			Button btn6 = new Button("Quit");
			btn5.setTranslateX(375);
			btn5.setTranslateY(750);
			btn6.setTranslateX(310);
			btn6.setTranslateY(750); 
			crashPlayAgain.getChildren().addAll(btn5,btn6);
	
	
	
	/* 
	 * Game animation loop initialization.
	 */
		AnimationTimer gameLoop = new AnimationTimer() {
	
	        /* (non-Javadoc)
	         * @see javafx.animation.AnimationTimer#handle(long)
	         */
	        @Override
	        public void handle(long now) {
	        	
	        	/* 
				 * All 6 labels are updated in real time by calling the MoonLander getters.
				 * The timer is updated from the TimeTracker object initialized in this class.
				 */
	        	l1.setText("Fuel: "+ lander.getFuel());
	        	l2.setText("Velocity: "+ lander.getVelocity());
	        	l3.setText("Thrust %: "+ lander.getThrust());
	        	l4.setText("Direction Angle: "+ lander.getDirectionAngle());
	        	l5.setText("Time Elapsed: "+lTime.getTime()/1000);
	        	l6.setText("Height: " + lander.getHeight());
	        	
	        	/* 
				 * MoonLander location, movements, and thrust are calculated according to user
				 * input and physics equations. xCheck is used to compare the MoonLander
				 * location to the location of the target.
				 */
	        	lander.updateLocation(lander.getX(), lander.getY(), lander.getDirectionAngle());
	        	lander.move();
	        	lander.thrust(lander.getThrust());
	        	float xCheck = lander.getX();
	        	
	        	/* 
				 * Nested if statements calculate whether the MoonLander lands on the target
				 * lands but missed the target, or crashes. Scenes are called accordingly.
				 */
	        	if(lander.getY()>650){ //Hits the Ground
	        		
	        		if((lander.getDirectionAngle() < 20 || lander.getDirectionAngle() > 345) && lander.getVelocity()<7){//Check to see if it Crashed (check Velocity and angles)
	        			System.out.println(targetLocation);
	        			if((targetLocation - 75) < xCheck || xCheck > (targetLocation+75)){ //Is it on the target
	        				
	                			System.out.println("Hit the Target!");
	                			stage.setScene(scene2);
	                			stage.show();
	                	}
	        			else{
	        				System.out.println("You landed.");
	        				stage.setScene(scene3);
	        				stage.show();
	        			}
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
	   
	   
		/* 
		 * Set on action calls for all of the buttons in each scene called for the end games.
		 * First button for each scene restarts the game, second button exits the game.
		 * The last button contains the logic for pausing the game.
		 */
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
	
	   pause.setOnAction((event) -> {
		    System.out.println("Pause");
		    if(pauseCheck == 0){
		    	gameLoop.stop();
		    	pauseCheck++;
		    	tempLanderTime = lander.accTimer.getTime();
		    	tempGameTime = lTime.getTime();
		    	
		    }
		    else {
		    	lander.accTimer.setTime(tempLanderTime);
		    	lTime.setTime(tempGameTime);
		    	gameLoop.start();
		    	pauseCheck--;
		    }
		    
		});
	}
	
}
