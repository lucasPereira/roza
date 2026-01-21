import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void criarOcorrencia() {
		assertEquals(Estado.ABERTA, ocorrencia.getEstado());
		assertEquals(Tipo.TAREFA, ocorrencia.getTipo());
		assertEquals(Prioridade.BAIXA, ocorrencia.getPrioridade());
		assertEquals(resumo, ocorrencia.getResumo());
		assertEquals(funcionario1, ocorrencia.getResponsavel());
		assertEquals(projeto1, ocorrencia.getProjeto());
	}
}
