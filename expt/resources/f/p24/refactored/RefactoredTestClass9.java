import org.junit.Test;

public class RefactoredTestClass9 {

	@Test()
	public void umaEmpresaNaoPodeTerUmNomeNulo() {
		Empresa.apagarEmpresa();
		String nomeEmpresa = null;
		new Empresa(nomeEmpresa);
	}
}
