import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void cadastrarOcorrencia_jaCadastrada() {
		Projeto meuProjeto = new Projeto();
		String resumo = "Minha tarefa bonitinha";
		Funcionario responsavel = new Funcionario();
		Ocorrencia novaTarefa = new Ocorrencia(resumo, responsavel, Tipo.TAREFA);
		meuProjeto.cadastrarOcorrencia(novaTarefa);
		List<Ocorrencia> listaDeOcorrencias = new LinkedList<>(meuProjeto.getOcorrencias());
		try {
			meuProjeto.cadastrarOcorrencia(novaTarefa);
		} catch (OcorrenciaJaCadastrada e) {
			assertEquals(listaDeOcorrencias, meuProjeto.getOcorrencias());
			throw e;
		}
	}
}
