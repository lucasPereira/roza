import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void cadastrarOcorrencia() {
		String nome = "Gmail";
		Projeto projeto = new Projeto(nome);
		String resumoOcorrencia = "Problemas ao visualizar email";
		int maxOcorrencias = 10;
		Funcionario funcionario = new Funcionario("Gilmar Douglas", maxOcorrencias);
		Ocorrencia ocorrencia = new Ocorrencia(resumoOcorrencia, funcionario, TipoOcorrenciaEnum.TAREFA, PrioridadeOcorrenciaEnum.ALTA);
		projeto.adicionarOcorrencia(ocorrencia);
		assertTrue(projeto.ocorrenciaPertenceProjeto(ocorrencia));
	}
}
