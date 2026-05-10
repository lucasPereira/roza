import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void adicionarProjeto() {
		Empresa empresa = new Empresa();
		Projeto calculadora = new Projeto();
		Projeto gerenciadorOcorrencias = new Projeto();
		empresa.addProjeto(calculadora);
		empresa.addProjeto(gerenciadorOcorrencias);
		assertEquals(2, empresa.numProjetos());
	}
}
