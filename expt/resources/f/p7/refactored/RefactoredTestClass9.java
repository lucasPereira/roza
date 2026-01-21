import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass9 {

	private Funcionario meuFuncionario;

	private String resumo;

	private Tipo tipo;

	@Before()
	public void setup() {
		meuFuncionario = new Funcionario();
		resumo = "meu resumo fofinho";
		tipo = Tipo.TAREFA;
	}

	@Test()
	public void atribuiOcorrencia_10() {
		for (int i = 0; i < 9; ++i) new Ocorrencia(resumo, meuFuncionario, tipo);
		new Ocorrencia(resumo, meuFuncionario, tipo);
		assertEquals(10, meuFuncionario.getOcorrenciasAbertas().size());
	}

	@Test()
	public void atribuiOcorrencia_maisDe10() {
		for (int i = 0; i < 10; ++i) new Ocorrencia(resumo, meuFuncionario, tipo);
		List<Ocorrencia> listaDeOcorrencias = new LinkedList<>(meuFuncionario.getOcorrenciasAbertas());
		try {
			new Ocorrencia(resumo, meuFuncionario, tipo);
		} catch (ExcedeOLimiteDeOcorrenciasPermitida e) {
			assertEquals(listaDeOcorrencias, meuFuncionario.getOcorrenciasAbertas());
			throw e;
		}
	}
}
