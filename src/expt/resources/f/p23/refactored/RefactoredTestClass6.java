import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass6 {

	private Funcionario funcionario;

	private Ocorrencia ocorrencia;

	@Before()
	public void setup() {
		funcionario = new Funcionario("Leonardo Passig Horstmann", new Date(1996, 8, 1));
		ocorrencia = new Ocorrencia(1, "ocorrencia", funcionario, TipoOcorrencia.BUG);
	}

	@Test()
	public void testAlteraPrioridadeOcorrencia() {
		ocorrencia.altera_prioridade(PrioridadeOcorrencia.ALTA);
		assertEquals(PrioridadeOcorrencia.ALTA, ocorrencia.prioridade());
	}

	@Test()
	public void testAlteraResponsavelOcorrencia() {
		Funcionario novoFuncionario = new Funcionario("Joaquim Jose da Silva Xavier", new Date(1990, 1, 1));
		ocorrencia.altera_responsavel(novoFuncionario);
		assertEquals(novoFuncionario, ocorrencia.responsavel());
	}

	@Test()
	public void testCriaOcorrencia() {
		assertEquals(1, ocorrencia.codigo().intValue());
		assertEquals(funcionario, ocorrencia.responsavel());
		assertEquals(TipoOcorrencia.BUG, ocorrencia.tipo());
		assertEquals("ocorrencia", ocorrencia.descricao());
	}
}
