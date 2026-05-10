import org.junit.Test;

public class RefactoredTestClass12 {

	@Test()
	public void trocarDoisFuncionariosDeDuasOcorrencias() {
		projeto.criarOcorrencia(funcionario, TipoDeOcorrencia.TAREFA, Prioridade.ALTA, "Arrumar espaçamento do componente");
		projeto.trocarFuncionarioResponsavel(0, mariana);
		projeto.trocarFuncionarioResponsavel(1, mariana);
		assertEquals(2, mariana.pegarNumeroOcorrencias());
		assertEquals(0, funcionario.pegarNumeroOcorrencias());
	}
}
