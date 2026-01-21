import org.junit.Test;

public class RefactoredTestClass13 {

	@Test()
	public void trocarPrioridadeDeDuasOcorrencias() {
		projeto.criarOcorrencia(funcionario, TipoDeOcorrencia.BUG, Prioridade.ALTA, "Arrumar margem do componente");
		projeto.trocarPrioridade(0, Prioridade.ALTA);
		projeto.trocarPrioridade(1, Prioridade.MEDIA);
		assertEquals(Prioridade.ALTA, projeto.pegarPrioridade(0));
		assertEquals(Prioridade.MEDIA, projeto.pegarPrioridade(1));
	}
}
