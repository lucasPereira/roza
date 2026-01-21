import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void enumValues_coverage() {
		Funcionario meuResponsavel = new Funcionario();
		for (Tipo val : Tipo.values()) Tipo.valueOf(val.toString());
		for (Estado val : Estado.values()) Estado.valueOf(val.toString());
		for (Prioridade val : Prioridade.values()) Prioridade.valueOf(val.toString());
	}
}
