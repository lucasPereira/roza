import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass7 {

	@Before()
	public void setup() {
		this.empresa = new Empresa();
		this.funcionario = new Funcionario("Roberto Abbud");
		this.projeto = new Projeto("Alpha");
	}

	@Test()
	public void cadastrarNovoFuncionarioEVerifiqueSeExiste() {
		String nomeFuncionario = "Roberto Abbud";
		this.empresa.cadastrarNovoFuncionario(this.funcionario);
		assertEquals(nomeFuncionario, this.empresa.encontreFuncionarioPorNome(nomeFuncionario).getNome());
	}

	@Test()
	public void cadastrarNovoProjetoEVerifiqueSeExiste() {
		String nomeProjeto = "Alpha";
		this.empresa.cadastrarNovoProjeto(this.projeto);
		assertEquals(nomeProjeto, this.empresa.encontreProjetoPorNome(nomeProjeto).getNome());
	}

	@Test()
	public void verifiqueSeExisteFuncionarioInexistente() {
		String nomeFuncionarioInexistente = "Marcos Silva";
		assertEquals(null, this.empresa.encontreFuncionarioPorNome(nomeFuncionarioInexistente));
	}

	@Test()
	public void verifiqueSeExisteProjetoInexistente() {
		String nomeProjetoInexistente = "Beta";
		assertEquals(null, this.empresa.encontreProjetoPorNome(nomeProjetoInexistente));
	}
}
