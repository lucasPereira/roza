import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass19 {

	@Before()
	public void setup() {
		projeto.fecharOcorrencia(0);
	}

	@Test()
	public void fecharOcorrenciaAberta() {
		assertEquals(0, projeto.pegarNumeroDeOcorrencias());
		assertEquals(0, funcionario.pegarNumeroOcorrencias());
	}

	@Test()
	public void trocarPrioridadeDeUmaOcorrenciaFechada() {
		projeto.trocarPrioridade(0, Prioridade.ALTA);
		assertEquals(0, projeto.pegarNumeroDeOcorrencias());
		assertEquals(0, funcionario.pegarNumeroOcorrencias());
	}

	@Test()
	public void trocarUmFuncionarioDeUmaOcorrenciaFechada() {
		projeto.trocarFuncionarioResponsavel(0, mariana);
		assertEquals(0, mariana.pegarNumeroOcorrencias());
		assertEquals(0, funcionario.pegarNumeroOcorrencias());
	}
}
