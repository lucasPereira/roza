import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void adicionarOcorrenciaFuncionario() {
		Ocorrencia newOcorrencia = new Ocorrencia(newProjeto, newFuncionario, "alta", "bug", 1);
		newEmpresa.addProjeto(newProjeto);
		newProjeto.addProjetoOcorrencia(newProjeto, newOcorrencia);
		assertEquals(newOcorrencia, newProjeto.getProjetosOcorrencias(newOcorrencia.getID()));
	}
}
