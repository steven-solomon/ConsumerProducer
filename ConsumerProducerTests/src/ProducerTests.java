import static org.mockito.Mockito.*;

import java.util.concurrent.Semaphore;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class ProducerTests {
	
	Producer producer;
	Semaphore mockEmptyCount;
	Semaphore mockFillCount;
	Semaphore semaphore;
	SimpleBuffer mockSimpleBuffer;
	
	@Before
	public void setUp() throws Exception {
		semaphore = new Semaphore(1);
		mockEmptyCount = spy(semaphore);
		mockFillCount = mock(Semaphore.class);
		mockSimpleBuffer = mock(SimpleBuffer.class);
		producer = new Producer(mockEmptyCount, mockFillCount, mockSimpleBuffer);
	}
		
	@Test
	public void aquires_permit_for_an_empty_element() throws Exception {	
		Thread thread = new Thread(producer);
		thread.start();
		semaphore.release();
		thread.join();
		producer.stopRunning();
		
		verify(mockEmptyCount).acquire();
	}
	
	@Test
	public void releases_permit_for_a_filled_element() throws Exception {
		Thread thread = new Thread(producer);
		thread.start();
		semaphore.release();
		thread.join();
		producer.stopRunning();
		
		verify(mockFillCount).release();
	}
	
	@Test
	public void adds_element_to_buffer() throws Exception {
		Thread thread = new Thread(producer);
		thread.start();
		semaphore.release();
		thread.join();
		producer.stopRunning();
		
		verify(mockSimpleBuffer).add(anyString());
	}
	
	@Test
	public void invokes_steps_in_order() throws Exception {
		Thread thread = new Thread(producer);
		thread.start();
		semaphore.release();
		thread.join();
		producer.stopRunning();
		
		// Sadly it's impossible to remove this duplication
		InOrder inOrder = inOrder(mockEmptyCount, mockSimpleBuffer, mockFillCount);
		inOrder.verify(mockEmptyCount).acquire();
		inOrder.verify(mockSimpleBuffer).add(anyString());
		inOrder.verify(mockFillCount).release();	
	}
	
	@Test 
	public void does_not_add_to_buffer_when_it_is_stopped() throws InterruptedException {
		Thread thread = new Thread(producer);
		thread.start();
		producer.stopRunning();		
		semaphore.release();
		thread.join();
		
		verify(mockSimpleBuffer, never()).add(anyString());
		verify(mockFillCount, never()).release();			
	}
}
