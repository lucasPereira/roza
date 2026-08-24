import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CheckoutTest {
	@Test
	public void shouldCountCartItems() {
		Cart cart = new Cart();
		cart.add(new Item("Dune", 40));
		cart.add(new Item("Foundation", 30));
		assertEquals(2, cart.size());
	}

	@Test
	public void shouldEmptyCart() {
		Cart cart = new Cart();
		cart.add(new Item("Dune", 40));
		cart.add(new Item("Foundation", 30));
		cart.clear();
		assertTrue(cart.isEmpty());
	}

	@Test
	public void shouldCountCatalogBooks() {
		Catalog catalog = new Catalog();
		catalog.add(new Book("Dune"));
		catalog.add(new Book("Foundation"));
		assertEquals(2, catalog.size());
	}

}