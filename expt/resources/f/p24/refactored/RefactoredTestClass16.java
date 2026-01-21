import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass16 {

	private String nomeFuncionario;

	private Funcionario funcionario;

	@Before()
	public void setup() {
		nomeFuncionario = "Fabio";
		funcionario = new Funcionario(nomeFuncionario);
	}

	@Test()
	public void umFuncionarioCriadoDeveTerUmNome() {
		assertEquals(nomeFuncionario, funcionario.obterNome());
	}

	@Test()
	public void umFuncionarioTemZeroOcorrencias() {
		Integer contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(0, is(contadorOcorrencias));
	}
}
