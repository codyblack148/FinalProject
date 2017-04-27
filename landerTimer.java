import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Cody Black  cblack4@cbu.edu
 * @author Chris Marek jmarek@cbu.edu
 */

/**
 *  landerTimer is a timer object that is used specifically to track time for the MoonLander
 *  object in the game.
 */
public class landerTimer {
	
	static float t = 0;
	
	/**
	 * @param period Period for the TimerTask. Reference TimerTask documentation for more info.
	 * @param delay Delay for the TimerTask. Reference TimerTask documentation for more info.
	 */
	public landerTimer(int period, int delay){
		Timer timer = new Timer();
		TimerTask task = new TimerTask(){
			public void run(){
				t++;
				System.out.println(t);
			}
		};	
		timer.scheduleAtFixedRate(task, delay, period);
	}
	
	/**
	 *  Reset timer to 0.
	 */
	public void reset(){
		t= 0;
	}
	
	/**Getter for timer.
	 * @return Value of current time according to timer.
	 */
	public float getTime(){
		return t;
	}
	
	/**Set time. Used exclusively for pause button in game.
	 * @param time Time that the timer is to be set to. 
	 */
	public void setTime(float time){
		t = time;
	}
	
}