import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass3 {

	private Empresa empresa;

	private Funcionario funcionario;

	private Projeto projeto;

	@Before()
	public void setup() {
		empresa = new Empresa("Google");
		funcionario = new Funcionario("Bryan Lima", 0);
		empresa.addFunc(funcionario);
		projeto = new Projeto("Pixel 5");
		empresa.addProj(projeto);
		projeto.addFuncionario(funcionario);
	}

	@Test()
	public void funcionarioDoisProjetos() {
		Projeto projeto2 = new Projeto("Pixel 6");
		empresa.addProj(projeto2);
		projeto2.addFuncionario(funcionario);
		List<Projeto> projetos = empresa.getProjetosPorFuncionario(funcionario.getId());
		String nomeProjeto1 = empresa.getProjetosPorFuncionario(funcionario.getId()).get(0).getName();
		String nomeProjeto2 = empresa.getProjetosPorFuncionario(funcionario.getId()).get(1).getName();
		assertEquals(2, projetos.size());
		assertEquals("Pixel 5", nomeProjeto1);
		assertEquals("Pixel 6", nomeProjeto2);
	}

	@Test()
	public void funcionarioUmProjeto() {
		List<Projeto> projetos = empresa.getProjetosPorFuncionario(funcionario.getId());
		String nomeProjeto = empresa.getProjetosPorFuncionario(funcionario.getId()).get(0).getName();
		assertEquals(1, projetos.size());
		assertEquals("Pixel 5", nomeProjeto);
	}
}
