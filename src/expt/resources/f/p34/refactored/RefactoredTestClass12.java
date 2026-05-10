import org.junit.Test;

public class RefactoredTestClass12 {

	@Test()
	public void trocarDoisFuncionariosDeDuasOcorrencias() {
		projetoWPP.criarOcorrencia(funcionarioLuiz, TipoDeOcorrencia.TAREFA, Prioridade.ALTA, "Arrumar alguma coisa");
		projetoWPP.trocarFuncionarioResponsavel(0, funcionarioPaulo);
		projetoWPP.trocarFuncionarioResponsavel(1, funcionarioPaulo);
		assertEquals(2, funcionarioPaulo.getNumeroOcorrencias());
		assertEquals(0, funcionarioLuiz.getNumeroOcorrencias());
	}
}
