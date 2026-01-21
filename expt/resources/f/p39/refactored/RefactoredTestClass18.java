import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass18 {

	@Before()
	public void setup() {
		projeto.criarOcorrencia(funcionario, TipoDeOcorrencia.BUG, Prioridade.ALTA, "Arrumar margem do componente");
		projeto.criarOcorrencia(funcionario, TipoDeOcorrencia.TAREFA, Prioridade.MEDIA, "Arrumar padding do componente");
		projeto.criarOcorrencia(funcionario, TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar espaçamento do componente");
		projeto.criarOcorrencia(funcionario, TipoDeOcorrencia.TAREFA, Prioridade.MEDIA, "Arrumar cor do componente");
		projeto.criarOcorrencia(funcionario, TipoDeOcorrencia.BUG, Prioridade.BAIXA, "Arrumar fonte do componente");
		projeto.criarOcorrencia(funcionario, TipoDeOcorrencia.TAREFA, Prioridade.ALTA, "Arrumar fundo do componente");
		projeto.criarOcorrencia(funcionario, TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar cor do componente");
		projeto.criarOcorrencia(funcionario, TipoDeOcorrencia.TAREFA, Prioridade.BAIXA, "Arrumar borda do componente");
		projeto.criarOcorrencia(funcionario, TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar largura do componente");
	}

	@Test()
	public void criarDezOcorrencias() {
		assertEquals(10, projeto.pegarNumeroDeOcorrencias());
		assertEquals(10, funcionario.pegarNumeroOcorrencias());
	}

	@Test()
	public void criarOnzeOcorrencias() {
		projeto.criarOcorrencia(funcionario, TipoDeOcorrencia.TAREFA, Prioridade.MEDIA, "Arrumar altura do componente");
		assertEquals(11, projeto.pegarNumeroDeOcorrencias());
		assertEquals(10, funcionario.pegarNumeroOcorrencias());
	}

	@Test()
	public void fecharUmaOcorrenciaSemFuncionarioResponsavel() {
		projeto.criarOcorrencia(funcionario, TipoDeOcorrencia.TAREFA, Prioridade.MEDIA, "Arrumar altura do componente");
		projeto.fecharOcorrencia(10);
		assertEquals(11, projeto.pegarNumeroDeOcorrencias());
		assertEquals(10, funcionario.pegarNumeroOcorrencias());
	}
}
