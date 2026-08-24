import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestClass2 {
	private Catalog catalog;

	@BeforeEach
	public void setup() {
		catalog = new Catalog();
		catalog.add(new Book("Dune"));
		catalog.add(new Book("Foundation"));
	}

	@Test
	public void shouldCountCatalogBooks() {
		assertEquals(2, catalog.size());
	}

	@Test
	public void shouldFindBookInCatalog() {
		assertTrue(catalog.contains("Dune"));
	}

}