import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void FuncionarioNovoNaoTemOcorrencias() {
		Funcionario joao = new Funcionario("João da Silva");
		assertEquals(0, joao.getQtdadeOcorrenciasResponsavel());
	}
}
