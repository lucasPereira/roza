import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class StorefrontTest {
	@Test
	public void shouldComputeCartTotal() {
		Cart cart = new Cart();
		cart.add(new Item("Dune", 40));
		cart.add(new Item("Foundation", 30));
		assertEquals(70, cart.total());
	}

	@Test
	public void shouldFindBookInCatalog() {
		Catalog catalog = new Catalog();
		catalog.add(new Book("Dune"));
		catalog.add(new Book("Foundation"));
		assertTrue(catalog.contains("Dune"));
	}

}