import org.junit.Test;

public class RefactoredTestClass13 {

	@Test()
	public void trocarPrioridadeDeDuasOcorrencias() {
		projetoWPP.criarOcorrencia(funcionarioLuiz, TipoDeOcorrencia.BUG, Prioridade.ALTA, "Arrumar alguma coisa");
		projetoWPP.trocarPrioridade(0, Prioridade.ALTA);
		projetoWPP.trocarPrioridade(1, Prioridade.MEDIA);
		assertEquals(Prioridade.ALTA, projetoWPP.getPrioridade(0));
		assertEquals(Prioridade.MEDIA, projetoWPP.getPrioridade(1));
	}
}
