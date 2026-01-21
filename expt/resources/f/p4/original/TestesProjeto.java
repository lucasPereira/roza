package testes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import projeto.Funcionario;
import projeto.Ocorrencia;
import projeto.Projeto;

public class TestesProjeto {
	
	Projeto projeto;
	
	@Before
	public void inicializacao() {
		projeto = new Projeto();
	}
	
	@Test
	public void projetoSemOcorrencias() throws Exception {
		List<Ocorrencia> listaDeOcorrenciasVazia = new ArrayList<Ocorrencia>();
		assertEquals(listaDeOcorrenciasVazia, projeto.obtemListaDeOcorrencias());
	}
	
	@Test
	public void projetoComUmaOcorrencia() throws Exception {
		Funcionario joao = new Funcionario();
		projeto.novaOcorrencia(joao);
		assertEquals(1, projeto.obtemListaDeOcorrencias().size());
	}
	
}
