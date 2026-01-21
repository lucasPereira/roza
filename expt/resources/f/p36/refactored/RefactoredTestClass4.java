import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void mudarResponsavelOcorrencia() {
		Ocorrencia melhoria = projetoA.criarMelhoria("Melhoria a", funcionario, PrioridadeOcorrencia.alta);
		Funcionario vitor = empresa.criarFuncionario("Vitor");
		projetoA.mudarResponsavelOcorrencia(melhoria, vitor);
		assertEquals(vitor, melhoria.getResponsavel());
		assertEquals(0, funcionario.getOcorrenciasAbertas().size());
		assertEquals(1, vitor.getOcorrenciasAbertas().size());
	}
}
