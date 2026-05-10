import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass3 {

	private Empresa empresa;

	private Funcionario funcionario;

	private Projetos projeto;

	@Before()
	public void setup() {
		empresa = new Empresa("Retaurante Massashin");
		empresa.contrataFunc("Paulo");
		funcionario = empresa.indexFuncionario(0);
		empresa.addProjeto("WEG-70");
		projeto = empresa.pegarProjeto(0);
	}

	@Test()
	public void ocorrenciaCompletaComResumo() {
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.TAREFA, Prioridade.ALTA, "Mal montado");
		assertEquals("Mal montado", projeto.identificarOcorrencia(0).pegarResumo());
	}

	@Test()
	public void ocorrenciaSemResumo() {
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.TAREFA, Prioridade.MEDIA, "");
		assertEquals("Ocorrencia sem descri��o", projeto.identificarOcorrencia(0).pegarResumo());
	}
}
