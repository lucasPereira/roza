import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass11 {

	private Empresa empresa;

	private Funcionario funcJoao;

	@Before()
	public void setup() {
		empresa = new Empresa("Petrobras");
		funcJoao = new Funcionario("João da Silva");
		empresa.contrataFuncionario(funcJoao);
	}

	@Test()
	public void petrobrasContrataFuncionarioJoao() {
		assertEquals(true, empresa.temFuncionario("João da Silva"));
	}

	@Test()
	public void petrobrasContrataJoaoEMaria() {
		Funcionario funcMaria = new Funcionario("Maria Silveira");
		empresa.contrataFuncionario(funcMaria);
		assertEquals(true, empresa.temFuncionario("João da Silva"));
		assertEquals(true, empresa.temFuncionario("Maria Silveira"));
	}
}
