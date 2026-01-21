import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass3 {

	private String resumo;

	@Before()
	public void setup() {
		this.empresa = new Empresa();
		this.empresa.cadastrarProjeto("Projeto");
		this.projeto = this.empresa.getProjetos().get(0);
		this.responsavel = new Funcionario("Joao");
		resumo = "A primeira ocorrencia.";
		this.projeto.cadastrarOcorrencias("OC1", resumo, responsavel);
	}

	@Test()
	public void cadastrar11Ocorrencia() {
		this.projeto.cadastrarOcorrencias("OC2", resumo, responsavel);
		this.projeto.cadastrarOcorrencias("OC3", resumo, responsavel);
		this.projeto.cadastrarOcorrencias("OC4", resumo, responsavel);
		this.projeto.cadastrarOcorrencias("OC5", resumo, responsavel);
		this.projeto.cadastrarOcorrencias("OC6", resumo, responsavel);
		this.projeto.cadastrarOcorrencias("OC7", resumo, responsavel);
		this.projeto.cadastrarOcorrencias("OC8", resumo, responsavel);
		this.projeto.cadastrarOcorrencias("OC9", resumo, responsavel);
		this.projeto.cadastrarOcorrencias("OC10", resumo, responsavel);
		this.projeto.cadastrarOcorrencias("OC11", resumo, responsavel);
		Ocorrencia ocorrencia = (Ocorrencia) projeto.getOcorrencias().get(0);
		assertEquals(resumo, ocorrencia.getResumo());
		assertEquals(1, this.projeto.getOcorrencias().size());
		assertEquals(1, ocorrencia.getId());
	}

	@Test()
	public void modificarEstado() {
		Ocorrencia ocorrencia = (Ocorrencia) projeto.getOcorrencias().get(0);
		ocorrencia.setEstado(false);
		assertEquals(false, ocorrencia.getEstado());
	}

	@Test()
	public void modificarPrioridade() {
		Ocorrencia ocorrencia = (Ocorrencia) projeto.getOcorrencias().get(0);
		ocorrencia.setPrioridade(Prioridade.ALTA);
		assertEquals(Prioridade.ALTA, ocorrencia.getPrioridade());
	}

	@Test()
	public void modificarResponsavel() {
		Ocorrencia ocorrencia = (Ocorrencia) projeto.getOcorrencias().get(0);
		Funcionario novoResponsavel = new Funcionario("Novo");
		ocorrencia.setResponsavel(novoResponsavel);
		assertEquals(novoResponsavel.getName(), ocorrencia.getResponsavel().getName());
	}
}
