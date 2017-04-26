import java.lang.Math;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyCode;

/**
 * @author codyblack
 *
 */
public class MoonLander{

	/**
	 * Moonlander class holds code to create the MoonLander, all of its attributes,
	 * and update its position to the UI.
	 */
	private float xlocation = 350, ylocation = 150;
	private float vx,vy,ax,ay, height;
	private final int MIN_THRUST = 1, MAX_THRUST = 100, MIN_FUEL = 1,DELTA=1;
	private double directionAngle=0,  fuelLevel = 2000, thrust = 0,gravity = 1.6;
	ImageView imageView;
    Image image;
	Pane layer;
	float timeAcc;
	float timeVel;
	private landerTimer accTimer = new landerTimer(1,1);
	
	
	public MoonLander(Image image, Pane layer){
		accTimer.reset();
		ax=0;
		ay=0;
		vx=0;
		vy=0;
		height = 650;
		xlocation = 350;
		ylocation = 150;
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
		this.accTimer.reset();
		this.fuelLevel=2000;
		this.ax=0;
		this.ay=0;
		this.vx=0;
		this.vy=0;
		this.height = 650;
		this.xlocation = 350;
		this.ylocation = 150;
		this.imageView.relocate(350, 150);
		
		
		
		
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
		//xlocation;
		
		ylocation += vy;
		xlocation += vx;
		if(xlocation<1){
			xlocation=0;
		}
	    if(xlocation>725){
			xlocation=724;
		}
		if(ylocation<1){
			ylocation = 1;
		}
				
		/*	xlocation = (xlocation + vx*time+1/2*ax*time*time)/5;
		ylocation = -(ylocation + vy*time+1/2*ay*time*time)/5;
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
		}*/
	}
	public void setThrust(double thrust){
		this.thrust=thrust;
	}
	public void thrust(double thrust){
		if(fuelLevel < MIN_FUEL || thrust < MIN_THRUST){
			this.ax=0;
			this.ay=(float) -gravity;//lander is out of fuel, falls to Tatooine.
		} else {
			ax = (float) (thrust*Math.sin(Math.toRadians(directionAngle))/100*45040/(15000+fuelLevel)/100);//lander is at max thrust, nothing happens.
			ay = (float) ((thrust/100*45040/(15000+fuelLevel)*Math.cos(Math.toRadians(directionAngle))-gravity)/10);
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
		vx = vx + ax;
		vy = -vy - ay*(accTimer.getTime()/1000);
		
	}
	public double getFuel(){
		return fuelLevel;
	}
	public double getDirectionAngle(){
		return directionAngle;
	}
	public void setDirectionAngle(){
		if(! (directionAngle<360)){
			this.directionAngle = Math.abs(directionAngle % 360);
		}
	}
	public float getHeight(){
		return -(ylocation - 650);
	}
	public void processInput(KeyCode k, Pane layer){
		switch(k){
		case UP: System.out.println("Increase thrust");
		accTimer.reset();
		if(! (thrust>=MAX_THRUST || fuelLevel<MIN_FUEL)){
			thrust +=5;
		}
		
		;break;
		case DOWN: System.out.println("Decrease thrust.");
		accTimer.reset();
		if(! (thrust < MIN_THRUST)){
			thrust -=5;
		}
		
		;break;
		
		case RIGHT: System.out.println("Rocket rotate right.");
		this.rotateRight();
		
		;break;
		case LEFT: System.out.println("Rocket rotate left.");
		this.rotateLeft();
		
		;break;
		case SPACE: System.out.println("Maximum thrust.");
		thrust = MAX_THRUST;
		accTimer.reset();
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

	public float getVx(){
		return vx;
	}
	public float getVy(){
		return vy;
	}
	public float getAx(){
		return ax;
	}
	public float getAy(){
		return ay;
	}
	public void updateLocation(float x, float y, double r){
		if(fuelLevel<MIN_FUEL){
			thrust=0;
		}
		
		changeVelocity();
		move();
		imageView.relocate(x, y);
		imageView.setRotate(r);
	}
}