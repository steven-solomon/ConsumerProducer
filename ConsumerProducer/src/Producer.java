import java.util.concurrent.Semaphore;

public class Producer {

	Semaphore emptyCount;
	Semaphore fillCount;
	SimpleBuffer simpleBuffer;
	
	public Producer(Semaphore emptyCount, Semaphore fillCount, SimpleBuffer simpleBuffer) {
		this.emptyCount = emptyCount;
		this.fillCount = fillCount;
		this.simpleBuffer = simpleBuffer;
	}

	public void start() throws Exception {
		emptyCount.acquire();
		simpleBuffer.add("Foo");
		fillCount.release();
	}
}
