import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass7 {

	private Empresa empresa;

	private Projeto projeto;

	private Funcionario func;

	private Ocorrencia ocorr1;

	@Before()
	public void setup() {
		empresa = new Empresa("Google");
		projeto = new Projeto("Pixel 5a");
		func = new Funcionario("Bryan Lima", 0);
		empresa.addFunc(func);
		ocorr1 = new TestsHelper().makeOcorrencia(0, func);
	}

	@Test()
	public void getResponsavelOcorrencia() {
		projeto.addOcorrencia(ocorr1);
		Funcionario responsavel = projeto.getOcorrencias().get(0).getResponsavel();
		assertEquals(0, responsavel.getId());
	}

	@Test()
	public void ocorrenciaCriadaAberta() {
		Estado estado = ocorr1.getEstado();
		assertEquals(Estado.ABERTA, estado);
	}

	@Test()
	public void ocorrenciaTipo() {
		Tipo tipo = ocorr1.getTipo();
		assertEquals(Tipo.TAREFA, tipo);
	}

	@Test()
	public void trocaDePrioridadeOcorrenciaAberta() {
		projeto.addOcorrencia(ocorr1);
		ocorr1.alterarPrioridade(Prioridade.BAIXA);
		Prioridade prioridade = ocorr1.getPrioridade();
		assertEquals(Prioridade.BAIXA, prioridade);
	}

	@Test()
	public void trocaDePrioridadeOcorrenciaFinalizade() {
		projeto.addOcorrencia(ocorr1);
		func.finalizarOcorrencia(ocorr1.getId());
		ocorr1.alterarPrioridade(Prioridade.BAIXA);
	}

	@Test()
	public void trocaDeResponsavelOcorrenciaAberta() {
		projeto.addOcorrencia(ocorr1);
		Funcionario func2 = new Funcionario("Patricia Vilain", 1);
		projeto.getOcorrencias().get(0).trocarResponsavel(func2);
		Funcionario responsavel = projeto.getOcorrencias().get(0).getResponsavel();
		assertEquals(1, responsavel.getId());
	}

	@Test()
	public void trocaDeResponsavelOcorrenciaFinalizada() {
		projeto.addOcorrencia(ocorr1);
		func.finalizarOcorrencia(ocorr1.getId());
		Funcionario func2 = new Funcionario("Patricia Vilain", 1);
		ocorr1.trocarResponsavel(func2);
	}
}
