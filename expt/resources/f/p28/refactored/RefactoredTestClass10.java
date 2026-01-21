import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass10 {

	private String nome;

	private int maxOcorrencias;

	private Funcionario funcionario;

	@Before()
	public void setup() {
		nome = "Gilmar Douglas";
		maxOcorrencias = 10;
		funcionario = new Funcionario(nome, maxOcorrencias);
	}

	@Test()
	public void alterarNomeFuncionario() {
		String novoNome = "Thiago Souza";
		funcionario.setNome(novoNome);
		assertEquals(novoNome, funcionario.getNome());
	}

	@Test()
	public void alterarQuantidadeMaximaOcorrencia() {
		int novaQuantidadeMaxima = 5;
		funcionario.setMaxOcorrencias(novaQuantidadeMaxima);
		assertEquals(novaQuantidadeMaxima, funcionario.getMaxOcorrencias());
	}

	@Test()
	public void criarFuncionario() {
		assertEquals("Gilmar Douglas", funcionario.getNome());
		assertEquals(maxOcorrencias, funcionario.getMaxOcorrencias());
	}

	@Test()
	public void verificaSeFuncionarioEstaComMaxOcorrenciasRelacionadas() {
		int zeroMaxOcorrencias = 0;
		funcionario.setMaxOcorrencias(zeroMaxOcorrencias);
		assertFalse(funcionario.possuiMaxOcorrencias());
	}
}
