import org.junit.Test;

public class RefactoredTestClass15 {

	@Test()
	public void testeComparaOcorrenciaCriadaDois() {
		Funcionario func = new Funcionario("Maria");
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.TAREFA, Prioridade.BAIXA);
		Ocorrencia oc2 = new Ocorrencia(1, "resumo", Tipo.TAREFA, Prioridade.BAIXA);
		func.adicionaOcorrencia(oc);
		func.adicionaOcorrencia(oc2);
		List<Ocorrencia> result = func.retornaOcorrencias();
		assertEquals(2, result.size());
		assertEquals(oc, result.get(0));
		assertEquals(oc2, result.get(1));
	}
}
