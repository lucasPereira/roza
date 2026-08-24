import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestClass1 {
	private Cart cart;

	@BeforeEach
	public void setup() {
		cart = HelperClass1.setup1();
	}

	@Test
	public void shouldCountCartItems() {
		assertEquals(2, cart.size());
	}

	@Test
	public void shouldComputeCartTotal() {
		assertEquals(70, cart.total());
	}

}