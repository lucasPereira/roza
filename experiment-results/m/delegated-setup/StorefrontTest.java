import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class StorefrontTest {
	@Test
	public void shouldComputeCartTotal() {
		Cart cart = HelperClass1.setup1();
		assertEquals(70, cart.total());
	}

	@Test
	public void shouldFindBookInCatalog() {
		Catalog catalog = HelperClass2.setup1();
		assertTrue(catalog.contains("Dune"));
	}

}