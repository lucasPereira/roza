import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class HelperClass1 {
	public static Cart setup1() {
		Cart cart = new Cart();
		cart.add(new Item("Dune", 40));
		cart.add(new Item("Foundation", 30));
		return cart;
	}

}