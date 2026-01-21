package empresaTDD;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


// duvidas
//1: como evitar "empresa.projetos.get(indiceProjeto).ocorrencias.get(chave).getResponsavel().getNome()"?
//2: uso de strings para comparações, por exemplo, if(estado == "aberta") ... qual a melhor prática?
//3: tratamento de return qnd algo dá errado, voltar objeto? voltar msg? voltar null? exceção em "moodelo de negocio"?
// 



// Sequência de testes

// testar criação empresa e verificação do nome correto
// testar acréscimo de funcionários pelo tamanho da lista
// testar criação de projeto e verificar se foi o primeiro da lista
// testar criação de ocorrencia como lista
// adaptar teste p chave (hashMap)
// testar criação atributo resumo de ocorrência
// testar designar funcionario a uma ocorrência
// testar criação atributo estado de ocorrência
// testar criação atributo tipo de ocorrência
// testar criação atributo prioridade de ocorrência
// testar alteração do funcionário com ocorrência aberta
// testar alteração do funcionário com ocorrência fechada (falhou)
// acréscimo de "if" para qnd funcionário já tem 10 ocorrências 

public class TesteEmpresa {
	private Empresa empresa;
	private Projeto projetoManhattan;
	private Funcionario funcionarioBeltrano;
	private Ocorrencia ocorrencia;
	private int chave;
	private int indiceProjeto;
	@Before
	public void configuraImplicit() {
		empresa = new Empresa("Nome da Empresa");
		projetoManhattan = new Projeto("Manhattan");
		empresa.projetos.add(projetoManhattan);
		indiceProjeto = 0;
		funcionarioBeltrano = new Funcionario("Beltrano");
		empresa.funcionarios.add(funcionarioBeltrano);
		
		chave = 2;
		ocorrencia = new Ocorrencia(chave,empresa.funcionarios.get(0));
		empresa.projetos.get(indiceProjeto).criaOcorrencia(chave,ocorrencia);
	}
	@Test
	public void testaNomeEmpresa() {
		assertEquals("Nome da Empresa",empresa.getNome());
	}
	
	@Test
	public void testaAdicionarDoisFuncionarios() {
		Funcionario funcionarioFulano = new Funcionario("Fulano");
		Funcionario funcionarioSiclano = new Funcionario("Siclano");
		empresa.funcionarios.add(funcionarioFulano);
		empresa.funcionarios.add(funcionarioSiclano);
		
		
		assertEquals(funcionarioBeltrano,empresa.funcionarios.get(0));
		assertEquals(funcionarioFulano,empresa.funcionarios.get(1));
		assertEquals(funcionarioSiclano,empresa.funcionarios.get(2));
		assertEquals(3,empresa.funcionarios.size());
	}
	
	@Test
	public void testaCriacaoProjeto() {
		assertEquals(projetoManhattan,empresa.projetos.get(0));
		assertEquals(1,empresa.projetos.size());
	}
	
	@Test // comecei com fake, alterei '1' p chave e qnd fui dar o get(1) falhou
	public void testaCriacaoOcorrencia() {
		int chave = 1;
		Ocorrencia ocorrencia = new Ocorrencia(chave,empresa.funcionarios.get(0));
		empresa.projetos.get(0).criaOcorrencia(chave,ocorrencia);
		assertEquals(ocorrencia,empresa.projetos.get(indiceProjeto).ocorrencias.get(chave));
	}
	
	@Test 
	public void testaCriaResumoOcorrencia() {
		Ocorrencia ocorrencia = new Ocorrencia(chave,empresa.funcionarios.get(0));
		ocorrencia.alteraResumo("Resumo 2");
		empresa.projetos.get(0).criaOcorrencia(chave,ocorrencia);
		assertEquals("Resumo 2",ocorrencia.getResumo());
	}
	
	@Test 
	public void testaAtribuicaoFuncionarioOcorrencia() {
		assertEquals(funcionarioBeltrano,empresa.projetos.get(indiceProjeto).ocorrencias.get(chave).getResponsavel());
	}
	
	@Test 
	public void testaEstadoOcorrencia() {
		empresa.projetos.get(0).ocorrencias.get(chave).setEstado("fechada");
		assertEquals("fechada",empresa.projetos.get(indiceProjeto).ocorrencias.get(chave).getEstado());
	}
	
	@Test 
	public void testaTipoOcorrencia() {
		empresa.projetos.get(0).ocorrencias.get(chave).setTipo("bug");
		assertEquals("bug",empresa.projetos.get(indiceProjeto).ocorrencias.get(chave).getTipo());
	}
	
	@Test 
	public void testaPrioridadeOcorrencia() {
		empresa.projetos.get(indiceProjeto).ocorrencias.get(chave).setPrioridade("alta");
		assertEquals("alta",empresa.projetos.get(indiceProjeto).ocorrencias.get(chave).getPrioridade());
		
	}
	
	@Test 
	public void testaAlteracaoFuncionarioOcorrenciaAberta() {
		Funcionario funcionarioFulano = new Funcionario("Fulano");
		empresa.funcionarios.add(funcionarioFulano);
		empresa.projetos.get(indiceProjeto).ocorrencias.get(chave).alteraResponsavel(funcionarioFulano);
		assertEquals(funcionarioFulano,empresa.projetos.get(indiceProjeto).ocorrencias.get(chave).getResponsavel());
	}
	@Test 
	public void testaAlteracaoFuncionarioComOcorrenciaFechada() {
		Funcionario funcionarioFulano = new Funcionario("Fulano");
		empresa.funcionarios.add(funcionarioFulano);
		empresa.projetos.get(indiceProjeto).ocorrencias.get(chave).setEstado("fechada");
		empresa.projetos.get(indiceProjeto).ocorrencias.get(chave).alteraResponsavel(funcionarioFulano);
		assertEquals(funcionarioBeltrano,empresa.projetos.get(indiceProjeto).ocorrencias.get(chave).getResponsavel());
	}
	
	@Test 
	public void testaAtribuicaoFuncionarioOcorrenciaComMaisDeDez() {
		Funcionario funcionarioFulano = new Funcionario("Fulano");
		empresa.funcionarios.add(funcionarioFulano);
		funcionarioFulano.setNumeroDeOcorrencias(10);
		ocorrencia.atribuiResponsavel(funcionarioFulano);
		assertEquals(10,funcionarioFulano.getNumeroDeOcorrencias());
	}
	
	@Test
	public void testaOcorrenciaCompletada() {
		empresa.projetos.get(indiceProjeto).ocorrencias.get(chave).setEstado("fechada");
		assertEquals("fechada",empresa.projetos.get(indiceProjeto).ocorrencias.get(chave).getEstado());
	}
	
	
	
	
}
