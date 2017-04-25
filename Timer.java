
public class Timer {
	private static long start;
	private static long end;
	public static void reset(){
		start=System.nanoTime();
	}
	public static int check(){
		end=System.nanoTime();
		return (int) ((end-start)/1000000000);
	}
}
