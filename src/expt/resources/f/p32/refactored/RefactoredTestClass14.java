import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass14 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = DelegatedSetups.novaEmpresaComFuncionarioProjetoEOcorrencia();
	}

	@Test()
	public void verificarAConclusaoDeUmaOcorrencia() {
		empresa.getProjeto("Projeto TDD").fecharOcorrencia(5);
		assertEquals(false, empresa.getProjeto("Projeto TDD").getOcorrencia(5).isAberta());
	}

	@Test()
	public void verificarOResponsavelDeUmaCertaOcorrencia() {
		empresa.getProjeto("Projeto TDD").getOcorrencia(5).setResponsavel(empresa.getFuncionario("Pedro"));
		assertEquals("Pedro", empresa.getProjeto("Projeto TDD").getOcorrencia(5).getResponsavel().getNome());
	}

	@Test()
	public void verificarOcorrencia() {
		assertEquals("Atraso", empresa.getProjeto("Projeto TDD").getOcorrencia(5).getNome());
		assertEquals(null, empresa.getProjeto("Projeto TDD").getOcorrencia(4));
	}

	@Test()
	public void verificarRemocaoDeResponsavelDeUmaOcorrenciaQueFoiEncerrada() {
		empresa.getProjeto("Projeto TDD").fecharOcorrencia(5);
		assertEquals(null, empresa.getProjeto("Projeto TDD").getOcorrencia(5).getResponsavel());
	}
}
