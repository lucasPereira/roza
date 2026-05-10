import org.junit.Test;

public class RefactoredTestClass9 {

	@Test()
	public void testeAtribuiProjetoFuncionario() {
		Empresa emp = new Empresa("Empresa x");
		String nomeFunc = "João";
		String nomeProj = "projeto x";
		Funcionario func = emp.criaFuncionario(nomeFunc);
		Projeto proj = emp.criaProjeto(nomeProj);
		boolean result = emp.atribuiProjetoFuncionario(func, proj);
		assertEquals(true, result);
	}
}
