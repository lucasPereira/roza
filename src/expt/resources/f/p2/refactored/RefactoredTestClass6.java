import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass6 {

	private Funcionario einstein;

	private Ocorrencia criarBomba;

	@Before()
	public void setup() {
		einstein = new Funcionario("Albert Einstein");
		criarBomba = new Ocorrencia("1", "Criar a bomba", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
	}

	@Test()
	public void testeMudarFuncionarioDeAberta() {
		Funcionario oppenheimer = new Funcionario("Robert Oppenheimer");
		criarBomba.mudarResponsavel(oppenheimer);
		assertEquals(oppenheimer, criarBomba.obterResponsavel());
	}

	@Test()
	public void testeMudarFuncionarioDeOcorrenciaFechada() {
		Funcionario oppenheimer = new Funcionario("Robert Oppenheimer");
		criarBomba.fechar();
		criarBomba.mudarResponsavel(oppenheimer);
	}

	@Test()
	public void testeMudarPrioridadeDeOcorrenciaAberta() {
		criarBomba.mudarPrioridade(Ocorrencia.Prioridade.MEDIA);
		assertEquals(Ocorrencia.Prioridade.MEDIA, criarBomba.obterPrioridade());
	}

	@Test()
	public void testeMudarPrioridadeDeOcorrenciaFechada() {
		criarBomba.fechar();
		criarBomba.mudarPrioridade(Ocorrencia.Prioridade.BAIXA);
	}
}
