package test.gerenciadorEmpresa;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import gerenciadorEmpresa.Funcionario;
import gerenciadorEmpresa.Ocorrencia;
import gerenciadorEmpresa.Projeto;

public class TesteProjeto {

	private Funcionario joao;
	private Projeto projeto;

	@Before
	public void setUp() throws Exception {
		this.joao = new Funcionario();
		this.projeto = new Projeto();
	}

	@Test
	public void obterOcorrencia() throws Exception {
		Ocorrencia ocorrencia = new Ocorrencia(null, null, joao);
		projeto.addOcorrencia(ocorrencia);
		assertEquals(ocorrencia, projeto.getOcorrencia(0));
	}

	@Test
	public void getResponsavelDoProjeto() throws Exception {
		this.projeto.setResponsavel(this.joao);
		assertEquals(this.joao, this.projeto.getResponsavel());
	}

}
