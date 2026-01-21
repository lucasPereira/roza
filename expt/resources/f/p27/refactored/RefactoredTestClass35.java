import org.junit.Test;

public class RefactoredTestClass35 {

	@Test()
	public void testeRetornaListaComUmProjeto() {
		Empresa emp = new Empresa("Empresa x");
		String nome1 = "projeto x";
		Projeto proj = emp.criaProjeto(nome1);
		List<Projeto> result = emp.retornaProjetos();
		assertEquals(proj, result.get(0));
	}
}
