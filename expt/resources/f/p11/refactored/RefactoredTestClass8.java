import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass8 {

	@Before()
	public void setup() {
		this.projeto = new Projeto("Alpha");
		this.funcionario = new Funcionario("Saulo");
		this.ocorrencia = new Ocorrencia("Consertar o método N.", TipoOcorrencia.BUG);
	}

	@Test()
	public void testeAdicionaNovaOcorrenciaEVerifiqueSeExiste() {
		this.projeto.adicionaNovaOcorrencia(this.funcionario, this.ocorrencia);
		Ocorrencia resultado = this.projeto.encontreOcorrenciaPorFuncionario(this.funcionario);
		assertEquals(this.ocorrencia, resultado);
	}

	@Test()
	public void testeConseguiuAdicionaNovaOcorrencia() {
		boolean conseguiuAdicionar = this.projeto.adicionaNovaOcorrencia(this.funcionario, this.ocorrencia);
		assertEquals(conseguiuAdicionar, true);
	}

	@Test()
	public void testeConseguiuRemoverOcorrencia() {
		this.projeto.adicionaNovaOcorrencia(this.funcionario, this.ocorrencia);
		boolean conseguiuRemover = this.projeto.removaOcorrencia(this.funcionario);
		assertEquals(conseguiuRemover, true);
	}

	@Test()
	public void testeNomeProjeto() {
		String expected = "Alpha";
		assertEquals(expected, this.projeto.getNome());
	}

	@Test()
	public void testeRemoverOcorrenciaEVerifiqueSeExiste() {
		this.projeto.adicionaNovaOcorrencia(this.funcionario, this.ocorrencia);
		this.projeto.removaOcorrencia(this.funcionario);
		Ocorrencia resultado = this.projeto.encontreOcorrenciaPorFuncionario(funcionario);
		assertEquals(null, resultado);
	}
}
