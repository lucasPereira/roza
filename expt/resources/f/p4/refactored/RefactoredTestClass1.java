import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void adicionaNovoProjeto() {
		Empresa empresa = new Empresa();
		Projeto novoProjeto = new Projeto();
		empresa.adicionaNovoProjeto(novoProjeto);
		List<Projeto> projetos = empresa.obterProjetos();
		assertEquals(1, projetos.size());
	}
}
