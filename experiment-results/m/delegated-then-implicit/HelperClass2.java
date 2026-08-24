import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class HelperClass2 {
	public static Catalog setup1() {
		Catalog catalog = new Catalog();
		catalog.add(new Book("Dune"));
		catalog.add(new Book("Foundation"));
		return catalog;
	}

}