import java.util.concurrent.Semaphore;


public class Consumer implements Runnable {

	Semaphore fillCount;
	SimpleBuffer simpleBuffer;
	
	public Consumer(Semaphore fillCount, SimpleBuffer simpleBuffer) {
		this.fillCount = fillCount;
		this.simpleBuffer = simpleBuffer;
	}

	@Override
	public void run() {
		try {
			fillCount.acquire();
			simpleBuffer.remove();
		} catch (Exception e) {
			// Fix code smell
		}
	}

	public void stopRunning() {
		
	}
}
