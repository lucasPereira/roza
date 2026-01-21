import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void verificaFuncionarioPertenceEmpresa() {
		String nome = "Google";
		Empresa empresa = new Empresa(nome);
		String funcionarioTest = "Gmail";
		int maxOcorrencias = 10;
		Funcionario funcionario = new Funcionario(funcionarioTest, maxOcorrencias);
		empresa.adionarFuncionario(funcionario);
		assertTrue(empresa.funcionarioPertenceEmpresa(funcionario));
	}
}
