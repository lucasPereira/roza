import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void empresaSemProjetos() {
		assertEquals(0, empresa.getProjetos().size());
	}
}
