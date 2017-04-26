import javafx.scene.paint.Color;
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

public class View extends Application {
	public Stage stage;
	Image rocketOn = new Image( getClass().getResource("MoonLanderAcc.png").toExternalForm());
	Image target = new Image( getClass().getResource("targettemp.png").toExternalForm());
	ImageView image = new ImageView(target);
	Random rand = new Random();
	int targetLocation;
	
	
	public void start(Stage primaryStage) {
		
		/* 
		 * Main game scene and initialization.
		 */
		if(stage==null){
			stage = primaryStage;
		}
		
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
				 * Win, play again scene setup. Initialization located in AnimationTimer.
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
                    			primaryStage.setScene(scene2);
                    			primaryStage.show();
                    		}
            			System.out.println("You landed, ya piece of shit.");
            		}
            		else{
            			System.out.println("You Crashed, ya piece of shit.");
            		}
            		
            		stop();
            	}
            	

            }

        };
       gameLoop.start();
       btn1.setOnAction((event) -> {
    	    System.out.println("Play again.");
    	    lander.reset();
    	    removeTarget(root,image);
    	    targetLocation = generateTarget(root);
    	    stage.setScene(scene);
       	    stage.show();
       	    gameLoop.start();
    	});
       btn2.setOnAction((event) -> {
   	    System.out.println("Quit");

   	});
       
       
       
	}
	public static void main(String[] args){
		launch(args); //move?
	}

	private int generateTarget(Pane layer){
		int x = rand.nextInt(601) + 100;
		image.setFitHeight(100);
		image.setFitWidth(100);
		image.relocate(x, 650);
		layer.getChildren().add(image);
		return x;
	}
	private void removeTarget(Pane layer, Node target){
		layer.getChildren().remove(target);
	}

	
}

