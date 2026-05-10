import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass6 {

	private Empresa empresa;

	private Projeto projeto;

	private Funcionario funcionario;

	@Before()
	public void setup() {
		empresa = new Empresa("Google");
		projeto = new Projeto("Pixel 5");
		empresa.addProj(projeto);
		funcionario = new Funcionario("Bryan Lima", 0);
	}

	@Test()
	public void ocorrenciaIdsIguais() {
		Ocorrencia ocorr1 = new TestsHelper().makeOcorrencia(0, funcionario);
		projeto.addOcorrencia(ocorr1);
		Ocorrencia ocorr2 = new TestsHelper().makeOcorrencia(0, funcionario);
		projeto.addOcorrencia(ocorr2);
	}

	@Test()
	public void projetoDuasOcorrencias() {
		Ocorrencia ocorr1 = new TestsHelper().makeOcorrencia(0, funcionario);
		projeto.addOcorrencia(ocorr1);
		Ocorrencia ocorr2 = new TestsHelper().makeOcorrencia(1, funcionario);
		projeto.addOcorrencia(ocorr2);
		List<Ocorrencia> ocorrencias = projeto.getOcorrencias();
		int ocorrenciaId1 = projeto.getOcorrencias().get(0).getId();
		int ocorrenciaId2 = projeto.getOcorrencias().get(1).getId();
		assertEquals(2, ocorrencias.size());
		assertEquals(0, ocorrenciaId1);
		assertEquals(1, ocorrenciaId2);
	}

	@Test()
	public void projetoUmaOcorrencia() {
		Ocorrencia ocorr1 = new TestsHelper().makeOcorrencia(0, funcionario);
		projeto.addOcorrencia(ocorr1);
		List<Ocorrencia> ocorrencias = projeto.getOcorrencias();
		int ocorrenciaId = projeto.getOcorrencias().get(0).getId();
		assertEquals(1, ocorrencias.size());
		assertEquals(0, ocorrenciaId);
	}

	@Test()
	public void projetoZeroOcorrencias() {
		List<Ocorrencia> ocorrencias = projeto.getOcorrencias();
		assertEquals(0, ocorrencias.size());
	}
}
