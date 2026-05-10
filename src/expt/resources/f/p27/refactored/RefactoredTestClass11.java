import org.junit.Test;

public class RefactoredTestClass11 {

	@Test()
	public void testeChaveDois() {
		int chave = 2;
		Ocorrencia oc = new Ocorrencia(chave, "resumo", Tipo.BUG, Prioridade.BAIXA);
		int resultado = oc.retornaChave();
		assertEquals(2, resultado);
	}
}
