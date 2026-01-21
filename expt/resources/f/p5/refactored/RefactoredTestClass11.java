import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass11 {

	private Ocorrencia umaOcorrencia;

	@Before()
	public void setup() {
		umaOcorrencia = new Ocorrencia("Trocar a caixeta azul", TipoOcorrencia.Melhoria, Status.Aberta, new Funcionario("Fabio"), Prioridade.Alta);
	}

	@Test()
	public void funcionarioResponsavelPelaOcorrencia() {
		assertEquals(new Funcionario("Fabio").getNome(), umaOcorrencia.getResponsavel().getNome());
	}

	@Test()
	public void modificaResponsavelDaOcorrencia() {
		umaOcorrencia.setResponsavel(new Funcionario("Carbine"));
		assertEquals(new Funcionario("Carbine").getNome(), umaOcorrencia.getResponsavel().getNome());
	}

	@Test()
	public void modificarPrioridadeOcorrencia() {
		umaOcorrencia.setPrioridade(Prioridade.Baixa);
		assertEquals(Prioridade.Baixa, umaOcorrencia.getPrioridade());
	}

	@Test()
	public void retornaNomeOcorrencia() {
		assertEquals("Trocar a caixeta azul", umaOcorrencia.getDescricaoOcorrencia());
	}

	@Test()
	public void retornarChave() {
		assertNotNull(umaOcorrencia.getChave());
	}

	@Test()
	public void statusAtualDaOcorrencia() {
		assertEquals(Status.Aberta, umaOcorrencia.getStatus());
	}

	@Test()
	public void tipoDeTarefaDaOcorrencia() {
		assertEquals(TipoOcorrencia.Melhoria, umaOcorrencia.getTarefa());
	}

	@Test()
	public void unicidade() {
		Ocorrencia ocorrenciaChaveDiferente = new Ocorrencia("Trocar a caixeta azul", TipoOcorrencia.Melhoria, Status.Aberta, new Funcionario("Fabio"), Prioridade.Alta);
		assertNotSame(umaOcorrencia.getChave(), ocorrenciaChaveDiferente.getChave());
		assertEquals(new Integer(umaOcorrencia.getChave() + 1), ocorrenciaChaveDiferente.getChave());
	}
}
