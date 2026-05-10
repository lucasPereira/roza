import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass16 {

	private Projeto projetoImw;

	@Before()
	public void setup() {
		empresa.adicionarProjeto("IMW");
		projetoImw = empresa.pegarProjeto(0);
		projetoImw.adicionarFuncionario(funcionario);
	}

	@Test()
	public void adicionarUmFuncionario() {
		assertEquals(1, projetoImw.pegarNumeroDeFuncionarios());
	}

	@Test()
	public void criarUmaOcorrencia() {
		projetoImw.criarOcorrencia(funcionario, TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar margem do componente");
		assertEquals(1, projetoImw.pegarNumeroDeOcorrencias());
		assertEquals(1, funcionario.pegarNumeroOcorrencias());
	}
}
