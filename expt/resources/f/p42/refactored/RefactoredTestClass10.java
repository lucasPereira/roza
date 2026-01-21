import org.junit.Test;

public class RefactoredTestClass10 {

	@Test()
	public void testeDefineResponsavelAOcorrencia() {
		Ocorrencia ocorrencia = projeto.criaOcorrencia();
		Funcionario joao = empresa.criaFuncionario("Joao da Silva");
		ocorrencia.defineResponsavel(joao);
		assertEquals(joao, projeto.obterOcorrencia(0).obterResponsavel());
	}
}
