import org.junit.Test;

public class RefactoredTestClass7 {

	@Test()
	public void newFuncionario() {
		Funcionario meuFuncionario = new Funcionario();
		assertTrue(meuFuncionario.getProjetos().isEmpty());
		assertTrue(meuFuncionario.getOcorrenciasAbertas().isEmpty());
	}
}
