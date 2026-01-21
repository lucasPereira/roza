import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass13 {

	private Empresa empresa;

	private Projeto projetoPreSal;

	@Before()
	public void setup() {
		empresa = new Empresa("Petrobras");
		projetoPreSal = new Projeto("Pré-Sal");
		empresa.criarProjeto(projetoPreSal);
	}

	@Test()
	public void ChecarOcorrenciaConcluida() {
		projetoPreSal.criaOcorrencia("Problema 1 no Pré-Sal");
		Ocorrencia ocorrencia = projetoPreSal.getOcorrenciaByName("Problema 1 no Pré-Sal");
		ocorrencia.setResponsavel(new Funcionario("João da Silva"));
		ocorrencia.setConcluido(true);
		assertEquals(true, ocorrencia.estaConcluido());
	}

	@Test()
	public void ChecarOcorrenciaNaoConcluida() {
		projetoPreSal.criaOcorrencia("Problema 1 no Pré-Sal");
		Ocorrencia ocorrencia = projetoPreSal.getOcorrenciaByName("Problema 1 no Pré-Sal");
		ocorrencia.setResponsavel(new Funcionario("João da Silva"));
		assertEquals(false, ocorrencia.estaConcluido());
	}

	@Test()
	public void FuncionarioResponsavelPorUmaOcorrencia() {
		Funcionario joao = new Funcionario("João da Silva");
		empresa.contrataFuncionario(joao);
		projetoPreSal.criaOcorrencia("Problema 1 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 1 no Pré-Sal"));
		assertEquals(1, joao.getQtdadeOcorrenciasResponsavel());
	}

	@Test()
	public void PetrobasCriaProjetoPreSal() {
		assertEquals("Pré-Sal", empresa.getProjeto("Pré-Sal").getNome());
	}

	@Test()
	public void ProjetoPreSalDaPetrobrasCriaDuasOcorrenciaComMesmoNome() {
		projetoPreSal.criaOcorrencia("Problema 1 no Pré-Sal");
		projetoPreSal.criaOcorrencia("Problema 1 no Pré-Sal");
	}

	@Test()
	public void ProjetoPreSalDaPetrobrasCriaOcorrenciaEResposavel() {
		Funcionario joao = new Funcionario("João da Silva");
		empresa.contrataFuncionario(joao);
		projetoPreSal.criaOcorrencia("Problema 1 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 1 no Pré-Sal"));
		assertEquals("João da Silva", projetoPreSal.getOcorrenciaByName("Problema 1 no Pré-Sal").getResposavel().getNome());
	}

	@Test()
	public void criaOcorrencia() {
		projetoPreSal.criaOcorrencia("Problema 1 no Pré-Sal");
		assertEquals("Problema 1 no Pré-Sal", projetoPreSal.getOcorrenciaByName("Problema 1 no Pré-Sal").getNome());
	}
}
