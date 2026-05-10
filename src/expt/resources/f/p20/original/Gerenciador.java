package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import modelo.Empresa;
import modelo.Funcionario;
import modelo.Ocorrencia;
import modelo.PrioridadeOcorrencia;
import modelo.TipoOcorrencia;

public class Gerenciador {
	
	
	private Empresa empresa;
	@Before
	public void setUp() throws Exception {
		empresa = new Empresa("Empresa 1");
	}

	@Test
	public void criaJoaoConfereNome() {
		Funcionario joao = new Funcionario("João B. da Rosa");
		assertEquals("João B. da Rosa", joao.getNome());
	}
	
	@Test
	public void criaEmpresa() {
		assertEquals("Empresa 1", empresa.getNome());
	}
	
	@Test
	public void adicionaFuncionario() {
		Funcionario joao = new Funcionario("João B. da Rosa");
		empresa.adicionaFuncionario(joao);
		assertEquals(joao.getNome(), empresa.getFuncionario("João B. da Rosa").getNome());
		assertEquals(null, empresa.getFuncionario("aaa"));
	}
	
	@Test
	public void criaProjeto() {
		assertTrue(empresa.adicionaProjeto("Projeto 1"));
	}
	
	@Test
	public void adicionaFuncionarioEmProjeto() {
		empresa.adicionaProjeto("Projeto 1");
		assertFalse(empresa.adicionaFuncionarioNoProjeto("Projeto 1", "Joao B. da Rosa"));
		empresa.adicionaFuncionario(new Funcionario("João B. da Rosa"));
		assertTrue(empresa.adicionaFuncionarioNoProjeto("Projeto 1", "João B. da Rosa"));
		assertFalse(empresa.adicionaFuncionarioNoProjeto("Projeto 1", "Joao B. da Rosa"));
		
	}
	
	@Test
	public void adicionaFuncionarioEm11Ocorrencias() {
		empresa.adicionaProjeto("Projeto 1");
		empresa.adicionaFuncionario(new Funcionario("Joao B. da Rosa"));
		empresa.adicionaFuncionarioNoProjeto("Projeto 1", "Joao B. da Rosa");
		assertTrue(empresa.adicionaOcorrencia("O 1", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertTrue(empresa.adicionaOcorrencia("O 2", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertTrue(empresa.adicionaOcorrencia("O 3", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertTrue(empresa.adicionaOcorrencia("O 4", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertTrue(empresa.adicionaOcorrencia("O 5", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertTrue(empresa.adicionaOcorrencia("O 6", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertTrue(empresa.adicionaOcorrencia("O 7", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertTrue(empresa.adicionaOcorrencia("O 8", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertTrue(empresa.adicionaOcorrencia("O 9", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertTrue(empresa.adicionaOcorrencia("O 10", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
		assertFalse(empresa.adicionaOcorrencia("O 11", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG));
	}
	
	@Test
	public void completaOcorrencia() {
		empresa.adicionaFuncionario(new Funcionario("Joao B. da Rosa"));
		empresa.adicionaProjeto("Projeto 1");
		empresa.adicionaFuncionarioNoProjeto("Projeto 1", "Joao B. da Rosa");
		empresa.adicionaOcorrencia("O X", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG);
		assertEquals(1, empresa.getFuncionario("Joao B. da Rosa").getNumOcorrencias());
		Ocorrencia ocorrencia = empresa.getOcorrencia("Projeto 1", 1);
		ocorrencia.getFuncionario().completaOcorrencia(ocorrencia);
		assertTrue(empresa.getOcorrencia("Projeto 1", 1).getCompleta());
		assertEquals(0, empresa.getFuncionario("Joao B. da Rosa").getNumOcorrencias());
		assertEquals(1, empresa.getFuncionario("Joao B. da Rosa").getNumOcorrenciasCompletas());
	}
	

}
