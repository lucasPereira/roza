import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void criaOcorrencia() {
		UUID chave = UUID.randomUUID();
		String resumo = "Breve descrição da ocorrência.";
		Funcionario responsavel = new Funcionario("Lucas", "00011122233");
		Ocorrencia ocorrencia = new Ocorrencia(chave, resumo, responsavel);
		assertEquals(chave, ocorrencia.obterChave());
		assertEquals(resumo, ocorrencia.obterResumo());
		assertEquals(responsavel, ocorrencia.obterResponsavel());
	}
}
