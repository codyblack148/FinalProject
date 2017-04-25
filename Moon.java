import java.util.Random;
public class Moon {

	
private static double gravity;
private final int XRANGE = 30;
private final int TARGET_RANGE = XRANGE - 5;
private final int TARGET_RADIUS = 5;
private int targetLocation;
public Moon(double gravity){
	this.gravity = gravity;
	targetLocation = generateTargetLocation();
}
public static double getGravity(){
	return gravity;
}
public void setGravity(double gravity){
	this.gravity = gravity;
}
private int generateTargetLocation(){
	Random rand = new Random();
	return rand.nextInt(TARGET_RANGE);
}
public int getTargetLocation(){
	return targetLocation;
}

}
