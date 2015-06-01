import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import java.util.concurrent.Semaphore;

import org.junit.Test;


public class ConsumerTests {

	@Test
	public void acquires_permit_for_filled_element() throws Exception {
		Semaphore mockFillCount = mock(Semaphore.class);
		
		Consumer consumer = new Consumer(mockFillCount);
		
		Thread thread = new Thread(consumer);
		thread.start();
		mockFillCount.release();
		consumer.stopRunning();
		thread.join();
		
		verify(mockFillCount).acquire();		
	}
}
