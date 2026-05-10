import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass9 {

	private Funcionario pedro;

	private Ocorrencia o;

	@Before()
	public void setup() {
		pedro = new Funcionario("Pedro");
		o = new Ocorrencia("Tarefa", Ocorrencia.Tipo.TAREFA, pedro, Ocorrencia.Prioridade.MEDIA);
	}

	@Test()
	public void testAtribuirResponsavelPedroParaUmaOcorrencia() {
		assertEquals(pedro, o.responsavel());
	}

	@Test()
	public void testCompletarOcorrencia() {
		o.completar();
		assertFalse(o.aberta());
	}

	@Test()
	public void testMudarPrioridadeDeUmaOcorrenciaAbertaDeMediaParaBaixa() {
		o.prioridade(Ocorrencia.Prioridade.BAIXA);
		assertEquals(Ocorrencia.Prioridade.BAIXA, o.prioridade());
	}

	@Test()
	public void testMudarPrioridadeDeUmaOcorrenciaCompletadaDeMediaParaBaixa() {
		o.completar();
		o.prioridade(Ocorrencia.Prioridade.BAIXA);
	}
}
