import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void mudarResponsavel() {
		this.joao = new Funcionario();
		Funcionario joao2 = new Funcionario();
		Ocorrencia ocurencia = new Ocorrencia(Prioridades.ALTA, "resumo", joao);
		assertNotEquals(joao2, ocurencia.getResponsavel());
		ocurencia.setResponsavel(joao2);
		assertNotEquals(joao, ocurencia.getResponsavel());
		assertEquals(joao, ocurencia.getResponsavel());
		assertEquals(joao2, ocurencia.getResponsavel());
	}
}
