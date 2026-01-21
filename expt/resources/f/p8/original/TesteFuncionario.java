package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import src.Empresa;
import src.Funcionario;
import src.Ocorrencia;
import src.Prioridade;
import src.Projeto;
import src.TipoOcorrencia;

public class TesteFuncionario {
	private Empresa empresa;
	private Funcionario bob;
	private GeradorDeOcorrencias gerador;

	@Before
	public void setup() {
		empresa = new Empresa();
		bob = new Funcionario();
		gerador = new GeradorDeOcorrencias();

		empresa.addFuncionario(bob);
	}

	@Test
	public void dezOcorrencias() throws Exception {
		Projeto calculadora = new Projeto();
		List<Ocorrencia> ocorrencias = gerador.gerarOcorrencias(bob, 10);

		empresa.addProjeto(calculadora);

		calculadora.addOcorrencias(ocorrencias);

		assertEquals(10, bob.numOcorrencias());
	}

	@Test(expected = Exception.class)
	public void onzeOcorrencias() throws Exception {
		Projeto calculadora = new Projeto();
		List<Ocorrencia> ocorrencias = gerador.gerarOcorrencias(bob, 11);

		empresa.addProjeto(calculadora);

		calculadora.addOcorrencias(ocorrencias);
	}
}
