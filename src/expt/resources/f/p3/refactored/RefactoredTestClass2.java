import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass2 {

	@Before()
	public void setup() {
		this.empresa = new Empresa();
	}

	@Test()
	public void cadastrarFuncionario() {
		this.empresa.cadastrarFuncionario("Joao");
		assertTrue(this.empresa.getFuncionarios().size() > 0);
	}

	@Test()
	public void cadastrarProjeto() {
		this.empresa.cadastrarProjeto("Projeto");
		assertTrue(this.empresa.getProjetos().size() > 0);
	}
}
