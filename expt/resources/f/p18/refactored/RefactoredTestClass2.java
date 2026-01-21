import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass2 {

	private Empresa Apple;

	private Empresa AppleR;

	private Funcionario Joao;

	private Funcionario Marcio;

	private Funcionario Cesar;

	private Projeto p1;

	private Ocorrencia o1;

	@Before()
	public void setup() {
		Apple = new Empresa("Apple");
		AppleR = new Empresa("Apple");
		Joao = new Funcionario("Joao", Apple);
		Marcio = new Funcionario("Marcio", Apple);
		Cesar = new Funcionario("Cesar", AppleR);
		p1 = new Projeto("Macintosh", Apple);
		o1 = new Ocorrencia("bug", "bug na criacao de threads", "alta", p1, Joao);
		return;
	}

	@Test()
	public void limiteOcorrencia() {
		Ocorrencia o1 = new Ocorrencia("bug", "bug na criacao de threads", "alta", p1, Marcio);
		Ocorrencia o2 = new Ocorrencia("bug", "bug na criacao de threads", "alta", p1, Marcio);
		Ocorrencia o3 = new Ocorrencia("bug", "bug na criacao de threads", "alta", p1, Marcio);
		Ocorrencia o4 = new Ocorrencia("bug", "bug na criacao de threads", "alta", p1, Marcio);
		Ocorrencia o5 = new Ocorrencia("bug", "bug na criacao de threads", "alta", p1, Marcio);
		Ocorrencia o6 = new Ocorrencia("bug", "bug na criacao de threads", "alta", p1, Marcio);
		Ocorrencia o7 = new Ocorrencia("bug", "bug na criacao de threads", "alta", p1, Marcio);
		Ocorrencia o8 = new Ocorrencia("bug", "bug na criacao de threads", "alta", p1, Marcio);
		Ocorrencia o9 = new Ocorrencia("bug", "bug na criacao de threads", "alta", p1, Marcio);
		Ocorrencia o10 = new Ocorrencia("bug", "bug na criacao de threads", "alta", p1, Marcio);
		try {
			Ocorrencia teste = new Ocorrencia("bug", "bug na criacao de threads", "alta", p1, Marcio);
		} catch (Error err) {
			System.out.println("Teste de limite de ocorrencias resultou em erro :  " + err);
		}
	}

	@Test()
	public void mudaPrioridadeOcorrencia() {
		o1.setPrioridadeBaixa();
		assertEquals(o1.getPrioridade(), "baixa");
	}

	@Test()
	public void mudaResponsavelAposTerminoOcorrencia() {
		Joao.terminaOcorrencia(o1);
		o1.mudaResponsavel(Marcio);
		assertNotEquals(o1.getResponsavel(), Marcio);
	}

	@Test()
	public void mudaResponsavelOcorrencia() {
		o1.mudaResponsavel(Marcio);
		assertEquals(o1.getResponsavel(), Joao);
		assertEquals(o1.getResponsavel(), Marcio);
	}

	@Test()
	public void prioridadeInvalida() {
		try {
			Ocorrencia teste = new Ocorrencia("bug", "bug na criacao de threads", "pequena", p1, Joao);
		} catch (Error err) {
			System.out.println("Teste de prioridade invalida resultou em erro :  " + err);
		}
	}

	@Test()
	public void prioridadeVazia() {
		try {
			Ocorrencia teste = new Ocorrencia("bug", "bug na criacao de threads", "", p1, Joao);
		} catch (Error err) {
			System.out.println("Teste de prioridade vazia resultou em erro :  " + err);
		}
	}

	@Test()
	public void resumoVazio() {
		try {
			Ocorrencia teste = new Ocorrencia("bug", "", "baixa", p1, Joao);
		} catch (Error err) {
			System.out.println("Teste de resumo vazio resultou em erro :  " + err);
		}
	}

	@Test()
	public void tipoVazio() {
		try {
			Ocorrencia teste = new Ocorrencia("", "bug na criacao de threads", "baixa", p1, Joao);
		} catch (Error err) {
			System.out.println("Teste de tipo vazio resultou em erro :  " + err);
		}
	}
}
