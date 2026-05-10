import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass39 {

	private Funcionario func;

	private Funcionario func2;

	private Ocorrencia oc;

	@Before()
	public void setup() {
		func = new Funcionario("Joao");
		func2 = new Funcionario("Maria");
		oc = new Ocorrencia(0, "resumo", Tipo.BUG, Prioridade.ALTA);
		func.adicionaOcorrencia(oc);
	}

	@Test()
	public void funcionarioFinalizaOcorrenciaQueNaoEDEle() {
		boolean result = func2.finalizaOcorrencia(oc);
		assertEquals(false, result);
	}

	@Test()
	public void funcionarioNaoAlteraEstadoDeOcorrenciaQueNaoEDele() {
		func2.finalizaOcorrencia(oc);
		Estado result = oc.retornaEstado();
		assertEquals(Estado.ABERTO, result);
	}
}
