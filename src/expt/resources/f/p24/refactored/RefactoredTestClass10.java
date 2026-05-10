import org.junit.Test;

public class RefactoredTestClass10 {

	@Test()
	public void umaEmpresaNaoPodeTerUmNomeVazio() {
		Empresa.apagarEmpresa();
		String nomeEmpresa = "";
		new Empresa(nomeEmpresa);
	}
}
