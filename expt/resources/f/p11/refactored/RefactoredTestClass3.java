import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void testeOcorrenciaFuncionarioZero() {
		this.funcionario = new Funcionario("Betinho");
		int numOcorrenciasEsperada = 0;
		int numOcorrenciasFuncionario = this.funcionario.getQuantiaOcorrencias();
		assertEquals(numOcorrenciasEsperada, numOcorrenciasFuncionario);
	}
}
