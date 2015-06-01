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
			if (isRunning)
				produceItem();
		} catch (Exception e) {
			outputError(e);
		}
	}

	private void produceItem() {
		simpleBuffer.add("Foo");
		fillCount.release();
	}

	private void outputError(Exception e) {
		System.err.println(e.getMessage());
		System.err.println(e.getStackTrace());
	}
	
	public void stopRunning() {
		isRunning = false;
	}
}
