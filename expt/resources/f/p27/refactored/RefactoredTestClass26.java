import org.junit.Test;

public class RefactoredTestClass26 {

	@Test()
	public void testeFuncionarioAtribuido() {
		Funcionario func = new Funcionario("Joao");
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.TAREFA, Prioridade.BAIXA);
		func.adicionaOcorrencia(oc);
		Funcionario result = oc.retornaResponsavel();
		assertEquals(func, result);
	}
}
