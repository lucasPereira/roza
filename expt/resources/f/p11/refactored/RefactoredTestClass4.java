import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void testeOcorrenciasFuncionarioMaiorQueZeroMasMaiorDoQueLimite() {
		this.funcionario = new Funcionario("Betinho");
		int numOcorrenciasEsperada = 10;
		for (int i = 0; i < 11; i++) this.funcionario.adicioneOcorrencia();
		int numOcorrenciasFuncionario = this.funcionario.getQuantiaOcorrencias();
		assertEquals(numOcorrenciasEsperada, numOcorrenciasFuncionario);
	}
}
