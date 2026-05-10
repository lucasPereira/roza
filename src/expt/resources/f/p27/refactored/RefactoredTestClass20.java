import org.junit.Test;

public class RefactoredTestClass20 {

	@Test()
	public void testeCriaOcorrenciaFuncionario() {
		Funcionario func = new Funcionario("João");
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.TAREFA, Prioridade.BAIXA);
		boolean result = func.adicionaOcorrencia(oc);
		assertEquals(true, result);
	}
}
