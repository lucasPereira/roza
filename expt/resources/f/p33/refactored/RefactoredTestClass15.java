import org.junit.Test;

public class RefactoredTestClass15 {

	@Test()
	public void mudarResponsavelEPrioridadeQuandoEstadoEstaFechado() {
		ocorrencia.fecharOcorrencia();
		ocorrencia.setPrioridade(Prioridade.MEDIA);
		ocorrencia.setFuncionario(funcionario2);
		assertEquals(Prioridade.BAIXA, ocorrencia.getPrioridade());
		assertEquals(funcionario1, ocorrencia.getResponsavel());
	}
}
