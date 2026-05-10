import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void criaFuncionario() {
		String nome = "Lucas";
		String cpf = "000.111.222-33";
		Funcionario lucas = new Funcionario(nome, cpf);
		assertEquals(nome, lucas.obterNome());
		assertEquals(cpf, lucas.obterCpf());
		assertEquals(0, lucas.obterProjetos().size());
		assertEquals(0, lucas.obterOcorrencias().size());
	}
}
