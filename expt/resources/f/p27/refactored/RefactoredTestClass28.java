import org.junit.Test;

public class RefactoredTestClass28 {

	@Test()
	public void testeListaProjetos() {
		Empresa emp = new Empresa("Empresa x");
		String nome1 = "projeto x";
		String nome2 = "projeto y";
		Projeto proj = emp.criaProjeto(nome1);
		Projeto proj2 = emp.criaProjeto(nome2);
		List<Projeto> result = emp.retornaProjetos();
		assertEquals(2, result.size());
		assertEquals(proj, result.get(0));
		assertEquals(proj2, result.get(1));
	}
}
