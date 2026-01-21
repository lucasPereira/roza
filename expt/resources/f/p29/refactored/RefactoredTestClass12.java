import org.junit.Test;

public class RefactoredTestClass12 {

	@Test()
	public void retirarOcorrencia() {
		Ocorrencia newOcorrencia = new Ocorrencia(newProjeto, newFuncionario, "bug", "alta", 1);
		newOcorrencia.finalizarOcorrencia(1);
		assertEquals(null, newProjeto.getProjetosOcorrencias(1));
	}
}
