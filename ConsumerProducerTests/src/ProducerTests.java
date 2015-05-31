import static org.mockito.Mockito.*;
import java.util.concurrent.Semaphore;

import org.junit.Before;
import org.junit.Test;

public class ProducerTests {
	
	Producer producer;
	Semaphore mockEmptyCount;
	Semaphore mockFillCount;
	SimpleBuffer mockSimpleBuffer;
	
	@Before
	public void setUp() throws Exception {
		mockEmptyCount = mock(Semaphore.class);
		mockFillCount = mock(Semaphore.class);
		mockSimpleBuffer = mock(SimpleBuffer.class);
		producer = new Producer(mockEmptyCount, mockFillCount, mockSimpleBuffer);
		
		producer.start();
	}
		
	@Test
	public void aquires_permit_for_an_empty_element() throws Exception {		
		verify(mockEmptyCount).acquire();
	}
	
	@Test
	public void releases_permit_for_a_filled_element() throws Exception {
		verify(mockFillCount).release();
	}
	
	@Test
	public void adds_element_to_buffer() throws Exception {
		verify(mockSimpleBuffer).add(anyString());
	}
}
