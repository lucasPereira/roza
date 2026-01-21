import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void deveAlterarPrioridade() {
		this.ocorrencia = new Ocorrencia();
		Prioridade prioridade = Prioridade.MEDIA;
		this.ocorrencia.alterarPrioridade(prioridade);
		assertEquals(prioridade, this.ocorrencia.getPrioridade());
	}
}
