import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void testeOcorrenciasFuncionarioMaiorQueZeroMasMenorDoQueLimite() {
		this.funcionario = new Funcionario("Betinho");
		int numOcorrenciasEsperada = 1;
		this.funcionario.adicioneOcorrencia();
		int numOcorrenciasFuncionario = this.funcionario.getQuantiaOcorrencias();
		assertEquals(numOcorrenciasEsperada, numOcorrenciasFuncionario);
	}
}
