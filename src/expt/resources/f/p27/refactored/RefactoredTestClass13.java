import org.junit.Test;

public class RefactoredTestClass13 {

	@Test()
	public void testeChaveUm() {
		int chave = 1;
		Ocorrencia oc = new Ocorrencia(chave, "resumo", Tipo.BUG, Prioridade.BAIXA);
		int resultado = oc.retornaChave();
		assertEquals(1, resultado);
	}
}
