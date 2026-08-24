import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestClass3 {
	private Catalog catalog;

	@BeforeEach
	public void setup() {
		catalog = HelperClass2.setup1();
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