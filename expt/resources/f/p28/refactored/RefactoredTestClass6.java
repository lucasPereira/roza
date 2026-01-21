import org.junit.Test;

public class RefactoredTestClass6 {

	@Test()
	public void verificaOcorrenciaCadastradaComSucesso() {
		String nome = "Gmail";
		Projeto projeto = new Projeto(nome);
		String resumoOcorrencia = "Falha ao carregar rascunho";
		int maxOcorrencias = 0;
		Funcionario funcionario = new Funcionario("Thiago Souza", maxOcorrencias);
		Ocorrencia ocorrencia = new Ocorrencia(resumoOcorrencia, funcionario, TipoOcorrenciaEnum.TAREFA, PrioridadeOcorrenciaEnum.ALTA);
		projeto.adicionarOcorrencia(ocorrencia);
	}
}
