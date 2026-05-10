import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass17 {

	private Projeto projetoImw;

	@Before()
	public void setup() {
		empresaBrasil.adicionarProjeto("IMW");
		projetoImw = empresaBrasil.getProjeto(0);
		projetoImw.adicionarFuncionario(funcionarioLuiz);
	}

	@Test()
	public void adicionarUmFuncionario() {
		assertEquals(1, projetoImw.getNumeroDeFuncionarios());
	}

	@Test()
	public void criarUmaOcorrencia() {
		projetoImw.criarOcorrencia(funcionarioLuiz, TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar alguma coisa");
		assertEquals(1, projetoImw.getNumeroDeOcorrencias());
		assertEquals(1, funcionarioLuiz.getNumeroOcorrencias());
	}
}
