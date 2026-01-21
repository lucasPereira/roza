import org.junit.Test;

public class RefactoredTestClass14 {

	@Test()
	public void testeComparaOcorrenciaCriada() {
		Funcionario func = new Funcionario("Maria");
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.TAREFA, Prioridade.BAIXA);
		func.adicionaOcorrencia(oc);
		List<Ocorrencia> result = func.retornaOcorrencias();
		assertEquals(oc, result.get(0));
	}
}
