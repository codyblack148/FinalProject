import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.*;
import java.util.Random;

public class View extends Application {
	public Stage primaryStage;
	Image rocketOff = new Image( getClass().getResource("MoonLander.png").toExternalForm());
	Image rocketOn = new Image( getClass().getResource("MoonLanderAcc.png").toExternalForm());
	Image target = new Image( getClass().getResource("targettemp.gif").toExternalForm());
	Image target = new Image( getClass().getResource("targettemp.png").toExternalForm());
	ImageView image = new ImageView(target);
	Random rand = new Random();


	public void start(Stage primaryStage) {
		
		
		Stage stage = primaryStage;
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root,800,800);
		scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		root.setId("MainImage");
		BorderPane topGUI = new BorderPane();
		root.setTop(topGUI);
		topGUI.setId("title");
		MoonLander lander = new MoonLander(rocketOff,root);
		
		generateTarget(root);
		
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
			lander.processInput(e.getCode());
		}
		
	});
		
		stage.setScene(scene);
		stage.show();
		
		AnimationTimer gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {
            	l1.setText("Fuel: "+ lander.getFuel());
            	l2.setText("Velocity: "+ lander.getVelocity());
            	l3.setText("Thrust %: "+ lander.getThrust());
            	l4.setText("Direction Angle: "+ lander.getDirectionAngle());
            	l6.setText("Height: " + lander.getHeight());
            	lander.updateLocation(lander.getX(), lander.getY(), lander.getDirectionAngle());
            }

        };
        gameLoop.start();
	}
	public static void main(String[] args){
		launch(args); //move?
	}
		private void generateTarget(Pane layer){
		int x = rand.nextInt(601) + 100;
		image.setFitHeight(100);
		image.setFitWidth(100);
		image.relocate(x, 650);
		layer.getChildren().add(image);
	}
}

