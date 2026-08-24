import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CheckoutTest {
	@Test
	public void shouldCountCartItems() {
		Cart cart = HelperClass1.setup1();
		assertEquals(2, cart.size());
	}

	@Test
	public void shouldEmptyCart() {
		Cart cart = HelperClass1.setup1();
		cart.clear();
		assertTrue(cart.isEmpty());
	}

	@Test
	public void shouldCountCatalogBooks() {
		Catalog catalog = HelperClass2.setup1();
		assertEquals(2, catalog.size());
	}

}