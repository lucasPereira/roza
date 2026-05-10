import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass5 {

	private Funcionario funcionario;

	private Funcionario funcionario_novo;

	private Projeto proj;

	private Ocorrencia oc;

	@Before()
	public void setup() {
		funcionario = new Funcionario("Leonardo Passig Horstmann", new Date(1996, 8, 1));
		funcionario_novo = new Funcionario("Leonardo", new Date(1996, 8, 1));
		proj = new Projeto("nome");
		oc = proj.adicionaOcorrencia("ocorrencia", TipoOcorrencia.TAREFA, funcionario, PrioridadeOcorrencia.ALTA);
	}

	@Test()
	public void finalizaOcorrenciaFuncionarioErrado() {
		Boolean finalizar = funcionario_novo.finalizarOcorrencia(oc);
		assertFalse(finalizar);
	}

	@Test()
	public void mudaResponsavelTeste() {
		Boolean altera = proj.alteraResponsavelOcorrencia(oc, funcionario_novo);
		assertTrue(altera);
		assertEquals(funcionario_novo, oc.responsavel());
	}

	@Test()
	public void mudaResponsavelTesteFinalizado() {
		funcionario.finalizarOcorrencia(oc);
		Boolean altera = proj.alteraResponsavelOcorrencia(oc, funcionario_novo);
		assertFalse(altera);
	}
}
