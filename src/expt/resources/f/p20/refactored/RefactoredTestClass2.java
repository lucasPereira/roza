import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass2 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = new Empresa("Empresa 1");
		empresa.adicionaProjeto("Projeto 1");
	}

	@Test()
	public void adicionaFuncionarioEm11Ocorrencias() {
		empresa.adicionaFuncionario(new Funcionario("Joao B. da Rosa"));
		empresa.adicionaFuncionarioNoProjeto("Projeto 1", "Joao B. da Rosa");
		assertTrue(empresa.adicionaOcorrencia("O 1", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertTrue(empresa.adicionaOcorrencia("O 2", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertTrue(empresa.adicionaOcorrencia("O 3", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertTrue(empresa.adicionaOcorrencia("O 4", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertTrue(empresa.adicionaOcorrencia("O 5", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertTrue(empresa.adicionaOcorrencia("O 6", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertTrue(empresa.adicionaOcorrencia("O 7", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertTrue(empresa.adicionaOcorrencia("O 8", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertTrue(empresa.adicionaOcorrencia("O 9", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertTrue(empresa.adicionaOcorrencia("O 10", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertFalse(empresa.adicionaOcorrencia("O 11", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
	}

	@Test()
	public void adicionaFuncionarioEmProjeto() {
		empresa.adicionaFuncionario(new Funcionario("João B. da Rosa"));
		assertFalse(empresa.adicionaFuncionarioNoProjeto("Projeto 1", "Joao B. da Rosa"));
		assertTrue(empresa.adicionaFuncionarioNoProjeto("Projeto 1", "João B. da Rosa"));
		assertFalse(empresa.adicionaFuncionarioNoProjeto("Projeto 1", "Joao B. da Rosa"));
	}
}
