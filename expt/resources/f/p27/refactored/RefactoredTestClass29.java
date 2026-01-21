import org.junit.Test;

public class RefactoredTestClass29 {

	@Test()
	public void testeOnzeOcorrencias() {
		Funcionario func = ClasseAuxiliar.preencheOcorrencias(10);
		Ocorrencia oc = new Ocorrencia(10, "resumo", Tipo.TAREFA, Prioridade.BAIXA);
		boolean result = func.adicionaOcorrencia(oc);
		assertEquals(false, result);
	}
}
