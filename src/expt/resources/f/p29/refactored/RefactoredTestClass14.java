import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass14 {

	private Ocorrencia newOcorrencia;

	@Before()
	public void setup() {
		newOcorrencia = new Ocorrencia(newProjeto, stefanoFuncionario, "bug", "alta", 1);
		newProjeto.addProjetoOcorrencia(newProjeto, newOcorrencia);
	}

	@Test()
	public void adicionarOcorrenciaFuncionario() {
		stefanoFuncionario.addOcorrencia(newProjeto, newOcorrencia);
		assertEquals(newOcorrencia, stefanoFuncionario.getOcorrencia(newProjeto, newOcorrencia.getID()));
	}

	@Test()
	public void errarOcorrenciaFuncionario() {
		assertNotEquals(newOcorrencia, stefanoFuncionario.getOcorrencia(newProjeto, newOcorrencia.getID()));
	}
}
