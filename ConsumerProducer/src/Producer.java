import java.util.concurrent.Semaphore;

public class Producer implements Runnable {

	Semaphore emptyCount;
	Semaphore fillCount;
	SimpleBuffer simpleBuffer;
	boolean isRunning = true;
	
	public Producer(Semaphore emptyCount, Semaphore fillCount, SimpleBuffer simpleBuffer) {
		this.emptyCount = emptyCount;
		this.fillCount = fillCount;
		this.simpleBuffer = simpleBuffer;
	}

	public void run() {
		
		try {
			emptyCount.acquire();
			if (isRunning) {
				simpleBuffer.add("Foo");
				fillCount.release();
			}
		} catch (Exception e) {
			// TODO: fix this code smell
		}
	}
	
	public void stopRunning() {
		isRunning = false;
	}
}
