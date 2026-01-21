import org.junit.Test;

public class RefactoredTestClass15 {

	@Test()
	public void trocarPrioridadeDeUmaOcorrenciaFechada() {
		Ocorrencia manutencao = projetoWPP.getOcorrencia(0);
		projetoWPP.fecharOcorrencia(0);
		projetoWPP.trocarPrioridade(0, Prioridade.MEDIA);
		assertEquals(Prioridade.ALTA, manutencao.prioridade);
		assertEquals(0, projetoWPP.getNumeroDeOcorrencias());
		assertEquals(0, funcionarioLuiz.getNumeroOcorrencias());
	}
}
