import org.junit.Test;

public class RefactoredTestClass8 {

	@Test()
	public void inserirSegundoProjetoNaEmpresa() {
		Projeto projeto2 = new Projeto("Projeto2", empresa1);
		assertTrue(empresa1.getProjetos().contains(projeto2));
	}
}
