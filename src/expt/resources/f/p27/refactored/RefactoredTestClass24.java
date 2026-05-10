import org.junit.Test;

public class RefactoredTestClass24 {

	@Test()
	public void testeDezOcorrencias() {
		Funcionario func = ClasseAuxiliar.preencheOcorrencias(9);
		Ocorrencia oc = new Ocorrencia(10, "resumo", Tipo.TAREFA, Prioridade.BAIXA);
		boolean result = func.adicionaOcorrencia(oc);
		assertEquals(true, result);
	}
}
