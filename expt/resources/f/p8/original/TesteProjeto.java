package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import src.Empresa;
import src.Funcionario;
import src.Ocorrencia;
import src.Prioridade;
import src.Projeto;
import src.TipoOcorrencia;

public class TesteProjeto {
	private Empresa empresa;
	private Projeto projeto;

	@Before
	public void setup() {
		empresa = new Empresa();
		projeto = new Projeto();

		empresa.addProjeto(projeto);
	}

	private Ocorrencia exemploOcorrencia(Funcionario funcionario) {
		return new Ocorrencia(funcionario, TipoOcorrencia.Bug, Prioridade.Alta, "Soma com problemas");
	}
	
	@Test
	public void adicionarOcorrencia() throws Exception {
		Funcionario bob = new Funcionario();
		Ocorrencia somaBugada = exemploOcorrencia(bob);

		empresa.addFuncionario(bob);
		projeto.addOcorrencia(somaBugada);

		assertEquals(1, projeto.numOcorrencias());
	}

	@Test(expected = Exception.class)
	public void adicionarOcorrenciaDeFuncionarioForaDaEmpresa() throws Exception {
		Funcionario bob = new Funcionario();
		Ocorrencia somaBugada = exemploOcorrencia(bob);

		projeto.addOcorrencia(somaBugada);
	}
}
