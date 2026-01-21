import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void testAdicionarOnzeOcorrenciasAoFuncionario() {
		Funcionario pedro = new Funcionario("Pedro");
		for (int i = 0; i < 10; i++) {
			pedro.adicionarOcorrencia(new Ocorrencia("", Ocorrencia.Tipo.TAREFA, pedro, Ocorrencia.Prioridade.ALTA));
		}
		pedro.adicionarOcorrencia(new Ocorrencia("", Ocorrencia.Tipo.TAREFA, pedro, Ocorrencia.Prioridade.ALTA));
	}
}
