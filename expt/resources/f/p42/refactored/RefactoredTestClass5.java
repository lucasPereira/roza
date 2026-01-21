import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void testeCriaFuncionarioJoaoEJorge() {
		Funcionario joao = empresa.criaFuncionario("Joao da Silva");
		Funcionario jorge = empresa.criaFuncionario("Jorge da Silva");
		List<Funcionario> lista = new ArrayList<>();
		lista.add(joao);
		lista.add(jorge);
		assertEquals("Joao da Silva", empresa.obterFuncionario(0));
		assertEquals("Jorge da Silva", empresa.obterFuncionario(1));
		assertEquals(lista, empresa.obterFuncionarios());
		assertEquals(2, empresa.obterFuncionarios().size());
	}
}
