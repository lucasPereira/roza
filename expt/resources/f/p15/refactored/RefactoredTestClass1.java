import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void criaEmpresa() {
		String nome = "UFSC";
		String cnpj = "83.899.526-0001/82";
		Empresa empresa = new Empresa(nome, cnpj);
		assertEquals(nome, empresa.obterNome());
		assertEquals(cnpj, empresa.obterCnpj());
		assertEquals(0, empresa.obterFuncionarios().size());
		assertEquals(0, empresa.obterProjetos().size());
	}
}
