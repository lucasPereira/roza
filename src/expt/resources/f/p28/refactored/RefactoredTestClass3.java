import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void adicionarMaisDeUmacorrenciaProjeto() {
		String nome = "Gmail";
		Projeto projeto = new Projeto(nome);
		String resumo1 = "Filtro nao é aplicado corretamente";
		String resumo2 = "Video não carrega corretamente";
		int maxOcorrencias = 10;
		Funcionario funcionario = new Funcionario("Roberto Alves", maxOcorrencias);
		Ocorrencia ocorrencia1 = new Ocorrencia(resumo1, funcionario, TipoOcorrenciaEnum.TAREFA, PrioridadeOcorrenciaEnum.ALTA);
		Ocorrencia ocorrencia2 = new Ocorrencia(resumo2, funcionario, TipoOcorrenciaEnum.TAREFA, PrioridadeOcorrenciaEnum.ALTA);
		projeto.adicionarOcorrencia(ocorrencia1);
		projeto.adicionarOcorrencia(ocorrencia2);
		assertEquals(2, projeto.getQuatidadeOcorrencias());
		assertEquals(ocorrencia1, projeto.getOcorrencia(ocorrencia1));
		assertEquals(ocorrencia2, projeto.getOcorrencia(ocorrencia2));
	}
}
