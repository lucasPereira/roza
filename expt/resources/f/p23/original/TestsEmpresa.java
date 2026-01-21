import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

public class TestsEmpresa {
	

	@SuppressWarnings("deprecation")
	@Test
	public void testCriaFuncionario() {
		Funcionario funcionario = new Funcionario("Leonardo Passig Horstmann", new Date(1996,8,1));
		assertEquals("Leonardo Passig Horstmann", funcionario.nome());
		assertEquals(new Date(1996,8, 1), funcionario.data_nascimento());
	}
	
	@Test
	public void testCriaOcorrencia() {
		@SuppressWarnings("deprecation")
		Funcionario funcionario = new Funcionario("Leonardo Passig Horstmann", new Date(1996,8,1));
		Ocorrencia ocorrencia = new Ocorrencia(1, "ocorrencia", funcionario, TipoOcorrencia.BUG);
		assertEquals(1, ocorrencia.codigo().intValue());
		assertEquals(funcionario, ocorrencia.responsavel());
		assertEquals(TipoOcorrencia.BUG, ocorrencia.tipo());
		assertEquals("ocorrencia", ocorrencia.descricao());
		
	}
	
	@Test
	public void testAlteraPrioridadeOcorrencia() {
		Funcionario funcionario = new Funcionario("Leonardo Passig Horstmann", new Date(1996,8,1));
		Ocorrencia ocorrencia = new Ocorrencia(1, "ocorrencia", funcionario, TipoOcorrencia.BUG);
		ocorrencia.altera_prioridade(PrioridadeOcorrencia.ALTA);
		assertEquals(PrioridadeOcorrencia.ALTA, ocorrencia.prioridade());
	}
	
	@Test
	public void testAlteraResponsavelOcorrencia() {
		Funcionario funcionario = new Funcionario("Leonardo Passig Horstmann", new Date(1996,8,1));
		Ocorrencia ocorrencia = new Ocorrencia(1, "ocorrencia", funcionario, TipoOcorrencia.BUG);
		Funcionario novoFuncionario = new Funcionario("Joaquim Jose da Silva Xavier", new Date(1990,1,1));
		ocorrencia.altera_responsavel(novoFuncionario);
		assertEquals(novoFuncionario, ocorrencia.responsavel());
	}
	
	@Test
	public void testeCriaProjeto() {
		Projeto proj = new Projeto("nome");
		assertEquals("nome", proj.nome());
	}
	
	@Test 
	public void adicionaOcorrenciaProjeto() {
		Funcionario funcionario = new Funcionario("Leonardo Passig Horstmann", new Date(1996,8,1));
		Projeto proj = new Projeto("nome");
		Ocorrencia oc = proj.adicionaOcorrencia("ocorrencia", TipoOcorrencia.TAREFA, funcionario);
		assertEquals(funcionario, oc.responsavel());
		assertEquals(1, proj.numeroOcorrencias().intValue());
		assertEquals(1, proj.numeroOcorrenciasAbertas().intValue());
	}
	
	@Test 
	public void adicionaOcorrenciaProjetoComPrioridade() {
		Funcionario funcionario = new Funcionario("Leonardo Passig Horstmann", new Date(1996,8,1));
		Projeto proj = new Projeto("nome");
		Ocorrencia oc = proj.adicionaOcorrencia("ocorrencia", TipoOcorrencia.TAREFA, funcionario, PrioridadeOcorrencia.ALTA);
		assertEquals (PrioridadeOcorrencia.ALTA, oc.prioridade());
	}
	
	@Test
	public void finalizarOcorrencia() {
		Funcionario funcionario = new Funcionario("Leonardo Passig Horstmann", new Date(1996,8,1));
		Projeto proj = new Projeto("nome");
		Ocorrencia oc = proj.adicionaOcorrencia("ocorrencia", TipoOcorrencia.TAREFA, funcionario, PrioridadeOcorrencia.ALTA);
		Boolean finalizar = funcionario.finalizarOcorrencia(oc);
		assertTrue(finalizar);
		assertEquals(0, proj.numeroOcorrenciasAbertas().intValue());
	}
	
	@Test
	public void finalizaOcorrenciaFuncionarioErrado() {
		Funcionario funcionario = new Funcionario("Leonardo Passig Horstmann", new Date(1996,8,1));
		Funcionario funcionario_novo = new Funcionario("Leonardo", new Date(1996,8,1));
		Projeto proj = new Projeto("nome");
		Ocorrencia oc = proj.adicionaOcorrencia("ocorrencia", TipoOcorrencia.TAREFA, funcionario, PrioridadeOcorrencia.ALTA);
		Boolean finalizar = funcionario_novo.finalizarOcorrencia(oc);
		assertFalse(finalizar);
	}
	
	@Test
	public void mudaResponsavelTeste() {
		Funcionario funcionario = new Funcionario("Leonardo Passig Horstmann", new Date(1996,8,1));
		Funcionario funcionario_novo = new Funcionario("Leonardo", new Date(1996,8,1));
		Projeto proj = new Projeto("nome");
		Ocorrencia oc = proj.adicionaOcorrencia("ocorrencia", TipoOcorrencia.TAREFA, funcionario, PrioridadeOcorrencia.ALTA);
		Boolean altera = proj.alteraResponsavelOcorrencia(oc, funcionario_novo);
		assertTrue(altera);
		assertEquals(funcionario_novo, oc.responsavel());
	}
	
	@Test
	public void mudaResponsavelTesteFinalizado() {
		Funcionario funcionario = new Funcionario("Leonardo Passig Horstmann", new Date(1996,8,1));
		Funcionario funcionario_novo = new Funcionario("Leonardo", new Date(1996,8,1));
		Projeto proj = new Projeto("nome");
		Ocorrencia oc = proj.adicionaOcorrencia("ocorrencia", TipoOcorrencia.TAREFA, funcionario, PrioridadeOcorrencia.ALTA);
		funcionario.finalizarOcorrencia(oc);
		Boolean altera = proj.alteraResponsavelOcorrencia(oc, funcionario_novo);
		assertFalse(altera);
	}
	
	@Test
	public void criaEmpresa() {
		Empresa empresa = new Empresa("nome");
		assertEquals("nome", empresa.nome());
	}
	
	@Test
	public void criaProjetoNaEmpresa() {
		Empresa empresa = new Empresa("nome");
		Projeto p = empresa.criaProjeto("meu_proj");
		assertEquals("meu_proj", p.nome());
		assertEquals(1, empresa.numeroDeProjetos());
	}
	
	@Test
	public void adicionaFuncionarioAEmpresa() {
		Funcionario funcionario = new Funcionario("Leonardo Passig Horstmann", new Date(1996,8,1));
		Empresa empresa = new Empresa("nome");
		empresa.adicionaFuncionario(funcionario);
		assertEquals(1, empresa.numFuncionarios().intValue());
	}
}
