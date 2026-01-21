import org.junit.Test;

public class RefactoredTestClass15 {

	@Test()
	public void trocarUmFuncionarioDeUmaOcorrencia() {
		projeto.trocarFuncionarioResponsavel(0, mariana);
		assertEquals(1, mariana.pegarNumeroOcorrencias());
		assertEquals(0, funcionario.pegarNumeroOcorrencias());
	}
}
