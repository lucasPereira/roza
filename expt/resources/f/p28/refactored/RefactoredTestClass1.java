import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void adicionarMaisDeUmFuncionarioEmpresa() {
		String nome = "Google";
		Empresa empresa = new Empresa(nome);
		String nome1 = "Gilmar Douglas";
		int maxOcorrencias = 10;
		Funcionario funcionario1 = new Funcionario(nome1, maxOcorrencias);
		String nome2 = "Patricia Vilain";
		Funcionario funcionario2 = new Funcionario(nome2, maxOcorrencias);
		empresa.adicionarFuncionario(funcionario1);
		empresa.adicionarFuncionario(funcionario2);
		assertEquals(2, empresa.getQuantidadeFuncionario());
		assertEquals(funcionario1, empresa.getFuncionario(funcionario1));
		assertEquals(funcionario2, empresa.getFuncionario(funcionario2));
	}
}
