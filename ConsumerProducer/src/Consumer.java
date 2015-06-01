import java.util.concurrent.Semaphore;


public class Consumer implements Runnable {

	Semaphore fillCount;
	
	public Consumer(Semaphore fillCount) {
		this.fillCount = fillCount;
	}

	@Override
	public void run() {
		try {
			fillCount.acquire();
		} catch (Exception e) {
			// Fix code smell
		}
	}

	public void stopRunning() {
		
	}
}
