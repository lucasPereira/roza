import org.junit.Test;

public class RefactoredTestClass14 {

	@Test()
	public void mudarResponsavelEPrioridadeQuandoEstadoEstaAberto() {
		ocorrencia.setPrioridade(Prioridade.MEDIA);
		ocorrencia.setFuncionario(funcionario2);
		assertEquals(Prioridade.MEDIA, ocorrencia.getPrioridade());
		assertEquals(funcionario2, ocorrencia.getResponsavel());
	}
}
