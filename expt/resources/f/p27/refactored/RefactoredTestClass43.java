import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass43 {

	private Funcionario func;

	private Funcionario func2;

	private Ocorrencia oc;

	@Before()
	public void setup() {
		func = new Funcionario("Joao");
		func2 = new Funcionario("Maria");
		oc = new Ocorrencia(0, "resumo", Tipo.TAREFA, Prioridade.BAIXA);
		func.adicionaOcorrencia(oc);
	}

	@Test()
	public void testePrimeiroResponsavelAtribuido() {
		func2.adicionaOcorrencia(oc);
		Funcionario result = oc.retornaResponsavel();
		assertEquals(func, result);
	}

	@Test()
	public void testeTentaAtribuirDoisResponsaveis() {
		boolean result = func2.adicionaOcorrencia(oc);
		assertEquals(false, result);
	}
}
