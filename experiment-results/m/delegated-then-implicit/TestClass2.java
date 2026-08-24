import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TestClass2 {
	@Test
	public void shouldEmptyCart() {
		Cart cart = HelperClass1.setup1();
		cart.clear();
		assertTrue(cart.isEmpty());
	}

}