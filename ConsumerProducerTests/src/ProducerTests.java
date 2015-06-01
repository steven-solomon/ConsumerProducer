import static org.mockito.Mockito.*;

import java.util.concurrent.Semaphore;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class ProducerTests {
	
	Producer producer;
	Semaphore spiedEmptyCount;
	Semaphore mockFillCount;
	Semaphore emptyCount;
	SimpleBuffer mockSimpleBuffer;
	Thread producerThread;
	
	@Before
	public void setUp() throws Exception {
		makeProducer();
		
		startThread();
	}

	private void startThread() {
		producerThread = new Thread(producer);
		producerThread.start();
	}

	private void makeProducer() {
		emptyCount = new Semaphore(1);
		spiedEmptyCount = spy(emptyCount);
		mockFillCount = mock(Semaphore.class);
		mockSimpleBuffer = mock(SimpleBuffer.class);
		producer = new Producer(spiedEmptyCount, mockFillCount, mockSimpleBuffer);
	}
		
	@Test
	public void aquires_permit_for_an_empty_element() throws Exception {	
		emptyCount.release();
		stopThread();
		
		verify(spiedEmptyCount).acquire();
	}
	
	private void stopThread() throws InterruptedException {
		producerThread.join();
		producer.stopRunning();
	}
	
	@Test
	public void releases_permit_for_a_filled_element() throws Exception {
		emptyCount.release();
		stopThread();
		
		verify(mockFillCount).release();
	}	
	
	@Test
	public void adds_element_to_buffer() throws Exception {
		emptyCount.release();
		stopThread();
		
		verify(mockSimpleBuffer).add(anyString());
	}
	
	@Test
	public void invokes_steps_in_order() throws Exception {
		emptyCount.release();
		stopThread();
		
		// Sadly it's impossible to remove this duplication
		InOrder inOrder = inOrder(spiedEmptyCount, mockSimpleBuffer, mockFillCount);
		inOrder.verify(spiedEmptyCount).acquire();
		inOrder.verify(mockSimpleBuffer).add(anyString());
		inOrder.verify(mockFillCount).release();	
	}
	
	@Test 
	public void does_not_add_to_buffer_when_stopped() throws InterruptedException {
		stopBeforeEmptyCountReleased();
		
		verify(mockSimpleBuffer, never()).add(anyString());	
	}
	
	private void stopBeforeEmptyCountReleased() throws InterruptedException {
		producer.stopRunning();		
		emptyCount.release();
		producerThread.join();
	}
	
	@Test 
	public void does_not_release_fill_count_when_stopped() throws InterruptedException {
		stopBeforeEmptyCountReleased();
		
		verify(mockFillCount, never()).release();			
	}
}
