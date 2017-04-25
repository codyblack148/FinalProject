import java.lang.Math;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyCode;

public class MoonLander{

	private float xlocation = 350, ylocation = 150;
	private float vx,vy,ax,ay, height;
	private boolean isLanded;
	private final int MIN_THRUST = 0, MAX_THRUST = 100, MIN_FUEL = 1,DELTA=1;
	private double directionAngle=0,  fuelLevel = 2000, thrust = 0,gravity = 9.8;
	private int checkThrust = 0;
	ImageView imageView;
    Image image;
	Pane layer;
	
	
	public MoonLander(Image image, Pane layer){
		vx=0;
		vy=0;
		ax=0;
		ay=0;
		height = 650;
		isLanded=false;
		this.image=image;
		this.layer=layer;
		this.imageView = new ImageView(image);
		this.imageView.setFitHeight(75);
		this.imageView.setFitWidth(75);
		this.imageView.relocate(350, 150);
		layer.getChildren().add(imageView);
		
		
	}
	public MoonLander(Image image, Pane layer,float previousX,
			float previousY,double previousR,double previousFuel,
			double previousThrust,float previousVx,float previousVy,
			float previousAx,float previousAy,float previousHeight){
		this.image=image;
		this.layer=layer;
		this.imageView = new ImageView(image);
		this.imageView.setFitHeight(75);
		this.imageView.setFitWidth(75);
		this.imageView.relocate(previousX, previousY);
		this.imageView.setRotate(previousR);
		layer.getChildren().add(imageView);
		
		vx = previousVx;
		vy = previousVy;
		ax = previousAx;
		ay = previousAy;
		height = previousHeight;
		thrust = previousThrust;
		xlocation = previousX;
		ylocation = previousY;
		fuelLevel = previousFuel;
		directionAngle = previousR;
	
	}
	public void reset(){
		fuelLevel=2000;
		vx=0;
		vy=0;
		ax=0;
		ay=0;
		thrust=0;
		isLanded=false;
		
	}
	public void rotateLeft(){
		directionAngle = (360 + directionAngle - 5) % 360;
		imageView.setRotate(directionAngle);
	}
	public void rotateRight(){
		directionAngle = (directionAngle + 5) % 360;
		imageView.setRotate(directionAngle);
	}
	public void move(){
		xlocation = (xlocation + vx*Timer.check()+1/2*ax*Timer.check()*Timer.check())/5;
		ylocation = -(ylocation + vy*Timer.check()+1/2*ay*Timer.check()*Timer.check())/5;
		if(ylocation>749){
			ay=0;
			vy=0;
			ylocation=750;
		}
		if(xlocation<1){
			xlocation=0;
		}
		else if(xlocation>749){
			xlocation=750;
		}
	}
	public void setThrust(double thrust){
		this.thrust=thrust;
	}
	public void thrust(double thrust){
		if(fuelLevel < MIN_FUEL){
			this.ax=0;
			this.ay=(float) gravity;//lander is out of fuel, falls to Tatooine.
		} else {
			this.ax = (float) (thrust/100*45040/(15000+fuelLevel)*Math.sin(directionAngle));//lander is at max thrust, nothing happens.
			this.ay = (float) (thrust/100*45040/(15000+fuelLevel)*-1*Math.cos(directionAngle)+gravity);
			fuelLevel -= thrust/100*5;
		}
	}
	public double getThrust(){
		return thrust;
	}
	public boolean checkThrust(){
		return thrust > 0;
	}
	public float getVelocity(){
		return (float) Math.sqrt((vx*vx) + (vy*vy));
	}
	public void changeVelocity(){
		vx = vx + ax*Timer.check();
		vy = -vy - ay*Timer.check();
		
	}
	public double getFuel(){
		return fuelLevel;
	}
	public double getDirectionAngle(){
		return directionAngle;
	}
	public void setDirectionAngle(){
		if(directionAngle<360){
			this.directionAngle = directionAngle;
		} else {
			this.directionAngle = Math.abs(directionAngle % 360);
		}
	}
	public float getHeight(){
		return ylocation;
	}
	public void processInput(KeyCode k, Pane layer){
		switch(k){
		case UP: System.out.println("Increase thrust");
		Timer.reset();
		thrust +=5;
		checkThrust++;
		if(checkThrust==1){
			layer.getChildren().remove(imageView);
			this.image = new Image( getClass().getResource("MoonLanderAcc.png").toExternalForm());
			this.imageView = new ImageView(image);
			layer.getChildren().add(imageView);
		}
		break;
		case DOWN: System.out.println("Decrease thrust.");
		Timer.reset();
		thrust -=5;
		checkThrust--;
		if(checkThrust==0){
			layer.getChildren().remove(imageView);
			this.image = new Image( getClass().getResource("MoonLander.png").toExternalForm());
			this.imageView = new ImageView(image);
			layer.getChildren().add(imageView);
		}
		;break;
		
		case RIGHT: System.out.println("Rocket rotate right.");
		this.rotateRight();
		
		break;
		case LEFT: System.out.println("Rocket rotate left.");
		this.rotateLeft();
		
		;break;
		case SPACE: System.out.println("Maximum thrust.");
		thrust = MAX_THRUST;
		Timer.reset();
		break;
		default: System.out.println("Nothing")
		
		;break;
		}
	}
	public float getX(){
		return xlocation;
	}
	public float getY(){
		return ylocation;
	}
	public void updateLocation(float x, float y, double r){
		if(fuelLevel<MIN_FUEL){
			thrust=0;
		}
		thrust(thrust);
		changeVelocity();
		move();
		imageView.relocate(x, y);
		imageView.setRotate(r);
	}
}