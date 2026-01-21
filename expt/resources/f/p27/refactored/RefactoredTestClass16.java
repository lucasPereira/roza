import org.junit.Test;

public class RefactoredTestClass16 {

	@Test()
	public void testeCriaEmpresaNomeDiferente() {
		Empresa emp = new Empresa("Empresa x");
		String nome = "Empresa y";
		Empresa emp1 = new Empresa(nome);
		String result = emp1.getNome();
		assertEquals(nome, result);
	}
}
