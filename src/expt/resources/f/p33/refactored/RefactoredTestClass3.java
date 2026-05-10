import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void funcionarioEProjetoSaoDeEmpresasDiferentes() {
		assertThrows(EmpresasDiferentes.class, () -> new Ocorrencia(Tipo.TAREFA, Prioridade.BAIXA, resumo, funcionario3, projeto1));
	}
}
