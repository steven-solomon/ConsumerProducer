import java.util.concurrent.Semaphore;

public class Producer implements Runnable {

	Semaphore emptyCount;
	Semaphore fillCount;
	SimpleBuffer simpleBuffer;
	
	public Producer(Semaphore emptyCount, Semaphore fillCount, SimpleBuffer simpleBuffer) {
		this.emptyCount = emptyCount;
		this.fillCount = fillCount;
		this.simpleBuffer = simpleBuffer;
	}

	public void run() {
		try {
			emptyCount.acquire();
			simpleBuffer.add("Foo");
			fillCount.release();
		} catch (Exception e) {
			// TODO: fix this code smell
		}
	}
}
