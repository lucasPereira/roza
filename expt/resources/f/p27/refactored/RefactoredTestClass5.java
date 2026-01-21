import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void testaChaveZero() {
		int chave = 0;
		Ocorrencia oc = new Ocorrencia(chave, "resumo", Tipo.BUG, Prioridade.BAIXA);
		int resultado = oc.retornaChave();
		assertEquals(0, resultado);
	}
}
