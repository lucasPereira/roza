import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestClass1 {
	private Cart cart;

	@BeforeEach
	public void setup() {
		cart = new Cart();
		cart.add(new Item("Dune", 40));
		cart.add(new Item("Foundation", 30));
	}

	@Test
	public void shouldCountCartItems() {
		assertEquals(2, cart.size());
	}

	@Test
	public void shouldEmptyCart() {
		cart.clear();
		assertTrue(cart.isEmpty());
	}

	@Test
	public void shouldComputeCartTotal() {
		assertEquals(70, cart.total());
	}

}