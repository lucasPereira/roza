import org.junit.Test;

public class RefactoredTestClass20 {

	@Test()
	public void nomeVazioFuncionario() {
		assertThrows(NomeVazio.class, () -> new Funcionario("", empresa1));
	}
}
