import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass46 {

	private Funcionario func;

	private Ocorrencia oc;

	@Before()
	public void setup() {
		func = new Funcionario("Joao");
		oc = new Ocorrencia(0, "resumo", Tipo.BUG, Prioridade.ALTA);
		func.adicionaOcorrencia(oc);
	}

	@Test()
	public void testeFuncionarioFinalizaOcorrencia() {
		boolean result = func.finalizaOcorrencia(oc);
		assertEquals(true, result);
	}

	@Test()
	public void testeFuncionarioFinalizaOcorrenciaJaFinalizada() {
		func.finalizaOcorrencia(oc);
		boolean result = func.finalizaOcorrencia(oc);
		assertEquals(false, result);
	}

	@Test()
	public void testeOcorrenciaFinalizadaRemovidaDeFuncionario() {
		func.finalizaOcorrencia(oc);
		List<Ocorrencia> result = func.retornaOcorrencias();
		assertEquals(false, result.contains(oc));
	}

	@Test()
	public void testeVerificaSeOcorrenciaFoiFinalizadaPeloFuncionario() {
		func.finalizaOcorrencia(oc);
		Estado result = oc.retornaEstado();
		assertEquals(Estado.FECHADO, result);
	}
}
