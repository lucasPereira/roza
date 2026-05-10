import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class testeProjeto {
	
	private Projeto proj;

	@Before
	public void init() {
		proj = new Projeto("projeto x");
	}
	
	@Test
	public void testeCriaProjeto() {
		String nome = "projeto x";
		Projeto result = new Projeto(nome);
		
		assertEquals(nome, result.getNome());
		
	}
	
	@Test
	public void testeCriaProjetoNomeDiferente() {
		String nome = "projeto y";
		Projeto result = new Projeto(nome);
		
		assertEquals(nome, result.getNome());
		
	}
	
	@Test
	public void testeAdicionaOcorrencia() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		String resumo = "ocorrencia do projeto x";
		Ocorrencia result = proj.criaOcorrencia(resumo, Tipo.BUG, Prioridade.BAIXA);
		
		assertEquals(0, result.retornaChave());
		assertEquals(resumo, result.retornaResumo());
		assertEquals(Tipo.BUG, result.retornaTipo());
		assertEquals(Prioridade.BAIXA, result.retornaPrioridade());
		assertEquals(Estado.ABERTO, result.retornaEstado());
	}
	
	// retornar uma lista de ocorrencias e verificar os dois objetos
	@Test
	public void testeAdicionaDuasOcorrencias() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		String resumo = "ocorrencia que ocorreu no projeto x";
		String resumo2 = "outra ocorrencia que ocorreu no projeto x";		
		proj.criaOcorrencia(resumo, Tipo.BUG, Prioridade.BAIXA);
		
		Ocorrencia result = proj.criaOcorrencia(resumo2, Tipo.BUG, Prioridade.BAIXA);
		
		assertEquals(1, result.retornaChave());
		assertEquals(resumo2, result.retornaResumo());
		assertEquals(Tipo.BUG, result.retornaTipo());
		assertEquals(Prioridade.BAIXA, result.retornaPrioridade());
		assertEquals(Estado.ABERTO, result.retornaEstado());
	}
	
	@Test
	public void testeAtribuiFuncionario() {
		Projeto proj = new Projeto("projeto x");
		Funcionario func = new Funcionario("João");
		
		boolean result = proj.atribuiFuncionario(func);
		
		assertEquals(true, result);
	}
	
	@Test
	public void testeAtribuiDoisFuncionarios() {
		Projeto proj = new Projeto("projeto x");
		Funcionario func = new Funcionario("João");
		Funcionario func2 = new Funcionario("Maria");
		proj.atribuiFuncionario(func);
		
		boolean result = proj.atribuiFuncionario(func2);
		
		assertEquals(true, result);
	}
	
	@Test
	public void testeAtribuiFuncionarioDuasVezes() {
		Projeto proj = new Projeto("projeto x");
		Funcionario func = new Funcionario("João");
		proj.atribuiFuncionario(func);
		
		boolean result = proj.atribuiFuncionario(func);
		
		assertEquals(false, result);		
	}
	
	@Test
	public void testeAtribuiFuncionarioNull() {
		Projeto proj = new Projeto("projeto x");
		
		boolean result = proj.atribuiFuncionario(null);
		
		assertEquals(false, result);
	}
	
	
	@Test
	public void listaFuncionarios() {
		Projeto proj = new Projeto("projeto x");
		Funcionario func = new Funcionario("João");
		proj.atribuiFuncionario(func);
		
		List<Funcionario> result = proj.retornaFuncionarios();
		
		assertEquals(1, result.size());
		assertEquals(func, result.get(0));
	}
	
	@Test
	public void listaDoisFuncionarios() {
		Projeto proj = new Projeto("projeto x");
		Funcionario func = new Funcionario("João");
		Funcionario func2 = new Funcionario("Maria");
		proj.atribuiFuncionario(func);
		proj.atribuiFuncionario(func2);
		
		List<Funcionario> result = proj.retornaFuncionarios();
		
		assertEquals(2, result.size());
		assertEquals(func, result.get(0));
		assertEquals(func2, result.get(1));
	}
}
