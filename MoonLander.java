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
	private double directionAngle=0,  fuelLevel = 2000, thrust = 0;
	ImageView imageView;
    Image image;
	Pane layer;
	Moon moon = new Moon(9.8);
	
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
			this.ay=(float) Moon.getGravity();//lander is out of fuel, falls to Tatooine.
		} else {
			this.ax = (float) (thrust/100*45040/(15000+fuelLevel)*Math.sin(directionAngle));//lander is at max thrust, nothing happens.
			this.ay = (float) (thrust/100*45040/(15000+fuelLevel)*-1*Math.cos(directionAngle)+Moon.getGravity());
			fuelLevel -= thrust/100*5;
		}
	}
	public double getThrust(){
		return thrust;
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
	public void processInput(KeyCode k){
		switch(k){
		case UP: System.out.println("Increase thrust");
		Timer.reset();
		thrust +=5;
		
		;break;
		case DOWN: System.out.println("Decrease thrust.");
		Timer.reset();
		thrust -=5;
		;break;
		case RIGHT: System.out.println("Rocket rotate right.");
		this.rotateRight();
		
		;break;
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
