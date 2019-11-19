package clueGame;

public class WaitThread extends Thread {

	public static Integer monitor = 0;
	
	public void run() {
		//System.out.println("Waiting...");
		
		try {
			synchronized(monitor) {
				monitor.wait();
				
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			
		}
		
		System.out.println("Finished");
		
	}
	
}
