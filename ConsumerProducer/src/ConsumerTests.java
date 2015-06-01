import static org.mockito.Mockito.*;

import java.util.concurrent.Semaphore;

import org.junit.Before;
import org.junit.Test;

public class ConsumerTests {
	
	private Semaphore mockFillCount;
	private SimpleBuffer simpleBuffer;
	private Consumer consumer;
	private Thread thread;

	@Before
	public void setUp() throws Exception {
		makeConsumer();
		startConsumerThread();
	}

	private void makeConsumer() {
		mockFillCount = mock(Semaphore.class);
		simpleBuffer = mock(SimpleBuffer.class);
		consumer = new Consumer(mockFillCount, simpleBuffer);
	}
	
	private void releaseFillCount() {
		mockFillCount.release();
	}

	private void stopConsumerThread() throws InterruptedException {
		consumer.stopRunning();
		thread.join();
	}

	private void startConsumerThread() {
		thread = new Thread(consumer);
		thread.start();
	}

	@Test
	public void acquires_permit_for_filled_element() throws Exception {	
		releaseFillCount();
		stopConsumerThread();
		
		verify(mockFillCount).acquire();		
	}
	
	@Test
	public void removes_item_from_buffer() throws Exception {		
		releaseFillCount();
		stopConsumerThread();
		
		verify(simpleBuffer).remove();		
	}
}
