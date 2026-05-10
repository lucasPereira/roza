import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void naoPodeObterUmaEmpresaSemEstarCriadaPrimeiro() {
		Empresa.apagarEmpresa();
		Empresa.obterEmpresa();
	}
}
