import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass5 {

	private Projeto projetoManhattan;

	private Funcionario einstein;

	private Funcionario oppenheimer;

	@Before()
	public void setup() {
		projetoManhattan = new Projeto("Projeto Manhattan");
		einstein = new Funcionario("Albert Einstein");
		oppenheimer = new Funcionario("Robert Oppenheimer");
		projetoManhattan.adicionarOcorrencia("1", "Criar a bomba", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
	}

	@Test()
	public void testeAdicionarDuasOcorreciasComChavesIguais() {
		projetoManhattan.adicionarOcorrencia("1", "Jogar a bomba no Japão", oppenheimer, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
	}

	@Test()
	public void testeAdicionarDuasOcorrenciaComChavesDiferentes() {
		projetoManhattan.adicionarOcorrencia("2", "Jogar a bomba no Japão", oppenheimer, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		assertEquals(2, projetoManhattan.obterNumeroDeOcorrencias());
	}

	@Test()
	public void testeAdicionarUmaOcorrencia() {
		assertEquals(1, projetoManhattan.obterNumeroDeOcorrencias());
	}
}
