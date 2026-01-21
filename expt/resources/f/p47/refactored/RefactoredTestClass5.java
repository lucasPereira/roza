import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass5 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = new Empresa("Google");
		this.projeto = empresa.createProjeto("projeto1");
		this.funcionario = new Funcionario("Pedro");
	}

	@Test()
	public void testCloseWhenClosed() {
		this.funcionario1 = new Funcionario("Joao");
		this.ocorrencia = this.projeto.createOcorrencia(this.funcionario, "Resolver bug", PrioridadeOcorrencia.ALTA, TipoOcorrencia.BUG);
		this.ocorrencia.close();
		this.ocorrencia.close();
		assertFalse(this.ocorrencia.isOpen());
		assertFalse(this.funcionario.getOcorrencias().contains(this.ocorrencia));
	}

	@Test()
	public void testCloseWhenOpen() {
		this.funcionario1 = new Funcionario("Joao");
		this.ocorrencia = this.projeto.createOcorrencia(this.funcionario, "Resolver bug", PrioridadeOcorrencia.ALTA, TipoOcorrencia.BUG);
		this.ocorrencia.close();
		assertFalse(this.ocorrencia.isOpen());
		assertFalse(this.funcionario.getOcorrencias().contains(this.ocorrencia));
	}

	@Test()
	public void testCreateMaxAmountOfOcorrencias() {
		String resumo = "Resolver bug";
		TestProjetoHelper.createOcorrencias(this.projeto, this.funcionario, resumo, PrioridadeOcorrencia.ALTA, TipoOcorrencia.BUG, 10);
		assertEquals(10, this.projeto.getOcorrencias().size());
		assertEquals(10, this.funcionario.getOcorrencias().size());
	}

	@Test()
	public void testCreateOcorrencia() {
		String resumo = "Resolver bug";
		Ocorrencia ocorrencia = this.projeto.createOcorrencia(this.funcionario, resumo, PrioridadeOcorrencia.ALTA, TipoOcorrencia.BUG);
		assertTrue(this.funcionario.getOcorrencias().contains(ocorrencia));
		assertEquals(ocorrencia.getResponsavel(), funcionario);
		assertEquals(ocorrencia.getResumo(), resumo);
		assertEquals(ocorrencia.getPrioridade(), PrioridadeOcorrencia.ALTA);
		assertEquals(ocorrencia.getTipo(), TipoOcorrencia.BUG);
	}

	@Test()
	public void testCreateOcorrenciaOutOfLimit() {
		String resumo = "Resolver bug";
		TestProjetoHelper.createOcorrencias(this.projeto, this.funcionario, resumo, PrioridadeOcorrencia.ALTA, TipoOcorrencia.BUG, 10);
		this.projeto.createOcorrencia(this.funcionario, resumo, PrioridadeOcorrencia.ALTA, TipoOcorrencia.BUG);
	}

	@Test()
	public void testSetResponsavel() {
		this.funcionario1 = new Funcionario("Joao");
		this.ocorrencia = this.projeto.createOcorrencia(this.funcionario, "Resolver bug", PrioridadeOcorrencia.ALTA, TipoOcorrencia.BUG);
		this.ocorrencia.setResponsavel(funcionario1);
		assertFalse(this.funcionario.getOcorrencias().contains(this.ocorrencia));
		assertEquals(this.ocorrencia.getResponsavel(), this.funcionario1);
		assertTrue(this.funcionario1.getOcorrencias().contains(this.ocorrencia));
	}

	@Test()
	public void testSetResponsavelWhenClosed() {
		this.funcionario1 = new Funcionario("Joao");
		this.ocorrencia = this.projeto.createOcorrencia(this.funcionario, "Resolver bug", PrioridadeOcorrencia.ALTA, TipoOcorrencia.BUG);
		this.ocorrencia.close();
		this.ocorrencia.setResponsavel(funcionario1);
	}
}
