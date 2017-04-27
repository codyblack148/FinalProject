import java.lang.Math;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyCode;

/**
 * @author Cody Black cblack4@cbu.edu
 * @author Chris Marek jmarek@cbu.edu
 */

/**
 * Moonlander class holds code to create the MoonLander, all of its attributes,
 * and update its position to the UI.
 */
public class MoonLander{


	
	private float xlocation = 350, ylocation = 150;
	private float vx,vy,ax,ay;
	private final int MIN_THRUST = 1, MAX_THRUST = 100, MIN_FUEL = 1;
	private double directionAngle=0,  fuelLevel = 2000, thrust = 0,gravity = 1.6;
	ImageView imageView;
    Image image;
	Pane layer;
	float timeAcc;
	float timeVel;
	public landerTimer accTimer = new landerTimer(1,1);
	
	
	/**MoonLander constructor.
	 * @param image Image of the moonlander passed from View to be displayed on screen.
	 * @param layer The pane that the moonlander will be displayed to.
	 */
	public MoonLander(Image image, Pane layer){
		accTimer.reset();
		ax=0;
		ay=0;
		vx=0;
		vy=0;
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
	
	/**
	 * Reset for the MoonLander object. Resets all attributes to default values,
	 * resets image on screen.
	 */
	public void reset(){
		this.accTimer.reset();
		this.fuelLevel=2000;
		this.ax=0;
		this.ay=0;
		this.vx=0;
		this.vy=0;
		this.xlocation = 350;
		this.ylocation = 150;
		this.imageView.relocate(350, 150);
	}
	
	/**
	 * Rotates the MoonLander to the left on screen.
	 */
	public void rotateLeft(){
		directionAngle = (360 + directionAngle - 5) % 360;
		imageView.setRotate(directionAngle);
	}
	
	/**
	 * Rotates the MoonLander to the right on screen.
	 */
	public void rotateRight(){
		directionAngle = (directionAngle + 5) % 360;
		imageView.setRotate(directionAngle);
	}
	
	/**
	 * Updates the x and y location of the MoonLander according to the velocity.
	 * Keeps the MoonLander from going off screen.
	 */
	public void move(){
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
	}

	
	/**Calculates the acceleration of both the x and y axes by 
	 * combining the thrust percentage, the mass of a real world moon lander 
	 * and the thrust of a real world rocket.
	 * @param thrust The current thrust of the MoonLander.
	 */
	public void thrust(double thrust){
		if(fuelLevel < MIN_FUEL || thrust < MIN_THRUST){
			this.ax=0;
			this.ay=(float) -gravity;
		} else {
			ax = (float) (thrust*Math.sin(Math.toRadians(directionAngle))/100*45040/(15000+fuelLevel)/100);
			ay = (float) ((thrust/100*45040/(15000+fuelLevel)*Math.cos(Math.toRadians(directionAngle))-gravity)/10);
			fuelLevel -= thrust/100*5;
		}
	}
	
	/**Getter for MoonLander thrust.
	 * @return Thrust.
	 */
	public double getThrust(){
		return thrust;
	}
	
	/**Getter for velocity magnitude, calculated using the components of velocity.
	 * @return Velocity.
	 */
	public float getVelocity(){
		return (float) Math.sqrt((vx*vx) + (vy*vy));
	}
	
	/**
	 * Change the velocity of the MoonLander based on acceleration and previous velocity.
	 */
	public void changeVelocity(){
		vx = vx + ax;
		vy = -vy - ay*(accTimer.getTime()/1000);
	}
	
	/**Getter for MoonLander current fuel.
	 * @return Fuel level.
	 */
	public double getFuel(){
		return fuelLevel;
	}
	
	/**Getter for orientation of MoonLander.
	 * @return Current angle of MoonLander.
	 */
	public double getDirectionAngle(){
		return directionAngle;
	}
	
	/**Getter for current height of MoonLander. One meter per pixel.
	 * @return Current height.
	 */
	public float getHeight(){
		return -(ylocation - 650);
	}
	
	/**Processes keyboard input. Switch statement decides which key is pressed
	 * and MoonLander methods are called accordingly to update the thrust and the
	 * direction.
	 * @param k Key pressed.
	 * @param layer Pane that key was pressed from.
	 */
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
		case CONTROL: System.out.println("Maximum thrust.");
		thrust = MAX_THRUST;
		accTimer.reset();
		break;
		default: System.out.println("Nothing")
		
		;break;
		}
	}
	
	/**Getter for x position in pixels.
	 * @return x location value.
	 */
	public float getX(){
		return xlocation;
	}
	/**Getter for y position in pixels.
	 * @return y location value.
	 */
	public float getY(){
		return ylocation;
	}

	/**Getter for velocity of MoonLander in x direction
	 * @return Velocity in x direction.
	 */
	public float getVx(){
		return vx;
	}
	/**Getter for velocity of MoonLander in y direction
	 * @return Velocity in y direction.
	 */
	public float getVy(){
		return vy;
	}
	
	/**Getter for acceleration of MoonLander in x direction.
	 * @return Acceleration in x direction.
	 */
	public float getAx(){
		return ax;
	}
	/**Getter for acceleration of MoonLander in y direction.
	 * @return Acceleration in y direction.
	 */
	public float getAy(){
		return ay;
	}
	
	/**Updates the game screen with the current position of the MoonLander.
	 * Method is called near constantly from AnimationTimer in View.
	 * @param x X position of MoonLander, in pixels.
	 * @param y Y position of MoonLander, in pixels.
	 * @param r Current angle of MoonLander.
	 */
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