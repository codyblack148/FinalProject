
import java.util.Timer;
import java.util.TimerTask;

public class TimeTracker {
	public static float t = 0;
	
public TimeTracker(int period, int delay){
	Timer timer = new Timer();
	TimerTask task = new TimerTask(){
		public void run(){
			t++;
		//	System.out.println(t);
		}
	};
	timer.scheduleAtFixedRate(task, delay, period);
}
public void reset(){
	t = 0;
	
}
public float getTime(){
	return t;
}
public void setTime(float time){
	t = time;
}
}
