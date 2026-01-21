import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass5 {

	private Empresa empresa;

	private Funcionario paulo;

	@Before()
	public void setup() {
		empresa = new Empresa("Massashin");
		empresa.contrataFunc("Paulo");
		paulo = empresa.indexFuncionario(0);
	}

	@Test()
	public void AddFuncionario() {
		empresa.addProjeto("WEG-67");
		Projetos WEG67 = empresa.pegarProjeto(0);
		WEG67.addFuncionario(paulo);
		assertEquals(1, WEG67.numeroDeFuncionarios());
	}

	@Test()
	public void criaAOcorrencia() {
		empresa.addProjeto("WEG-67");
		Projetos WEG67 = empresa.pegarProjeto(0);
		WEG67.addFuncionario(paulo);
		WEG67.criaOcorrencia(paulo, Ocorrencia_tipos.BUG, Prioridade.ALTA, "Falta montar prato");
		assertEquals(new Integer(1), WEG67.pegarNumOcorrencias());
		assertEquals(new Integer(1), paulo.numeroOcorrencias());
	}

	@Test()
	public void criaMaisDeUmProjetos() {
		empresa.addProjeto("WEG-67");
		empresa.addProjeto("WEG-68");
		empresa.addProjeto("WEG-69");
		assertEquals("WEG-67", empresa.pegarProjeto(0).localizaCodProjeto());
		assertEquals("WEG-68", empresa.pegarProjeto(1).localizaCodProjeto());
		assertEquals("WEG-69", empresa.pegarProjeto(2).localizaCodProjeto());
		assertEquals(3, empresa.pegarNumProjetos());
	}

	@Test()
	public void criaProjetoComIdentificacao() {
		empresa.addProjeto("WEG-67");
		assertEquals("WEG-67", empresa.pegarProjeto(0).localizaCodProjeto());
		assertEquals(1, empresa.pegarNumProjetos());
	}

	@Test()
	public void criaProjetoNaoIdentificadoIdNull() {
		empresa.addProjeto("");
		assertEquals(0, empresa.pegarNumProjetos());
	}
}
