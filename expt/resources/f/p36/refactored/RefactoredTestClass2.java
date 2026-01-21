import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void funcionarioAtribuirOcorrenciaAberta() {
		projetoA.criarMelhoria("M1", joao, PrioridadeOcorrencia.alta);
		assertEquals(1, joao.getOcorrenciasAbertas().size());
	}
}
