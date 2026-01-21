package src;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TesteGerenciadorOcorrencias {

	@Test 
	public void criarEmpresaPetrobras() throws Exception {
		Empresa empresa = new Empresa("Petrobras");
		assertEquals("Petrobras", empresa.getNome());
		assertEquals(0, empresa.getFuncionarios().size());
	}
	
	@Test (expected = RuntimeException.class)
	public void criarEmpresaSemNome() throws Exception {
		new Empresa("");
	}
	
	@Test 
	public void EmpresaPetrobrasIniciaSemFuncionarios() throws Exception {
		Empresa empresa = new Empresa("Petrobras");
		assertEquals(0, empresa.getFuncionarios().size());
	}
	
	@Test 
	public void EmpresaPetrobrasIniciaSemProejtos() throws Exception {
		Empresa empresa = new Empresa("Petrobras");
		assertEquals(0, empresa.getProjetos().size());
	}
	
	@Test (expected = RuntimeException.class)
	public void criarEmpresNomeNull() throws Exception {
		new Empresa(null);
	}
	
	@Test
	public void criarFuncionario() throws Exception {
		Funcionario func = new Funcionario("João da Silva");
		assertEquals("João da Silva", func.getNome());
	}
	
	@Test (expected = RuntimeException.class)
	public void criarFuncionarioSemNome() throws Exception {
		new Funcionario("");
	}
	
	@Test (expected = RuntimeException.class)
	public void criarFuncionarioComNomeNull() throws Exception {
		new Funcionario(null);
	}
	
	@Test
	public void FuncionarioNovoNaoTemOcorrencias() throws Exception {
		Funcionario joao = new Funcionario("João da Silva");
		assertEquals(0, joao.getQtdadeOcorrenciasResponsavel());
	}
	
	@Test
	public void petrobrasContrataFuncionarioJoao() throws Exception {
		Empresa empresa = new Empresa("Petrobras");
		Funcionario funcJoao = new Funcionario("João da Silva");
		empresa.contrataFuncionario(funcJoao);
		assertEquals(true, empresa.temFuncionario("João da Silva"));
	}
	
	@Test
	public void petrobrasContrataJoaoEMaria() throws Exception {
		Empresa empresa = new Empresa("Petrobras");
		Funcionario funcJoao = new Funcionario("João da Silva");
		empresa.contrataFuncionario(funcJoao);
		Funcionario funcMaria = new Funcionario("Maria Silveira");
		empresa.contrataFuncionario(funcMaria);
		assertEquals(true, empresa.temFuncionario("João da Silva"));
		assertEquals(true, empresa.temFuncionario("Maria Silveira"));
	}
	
	@Test
	public void CriaProjetoPreSal() throws Exception {
		Projeto projetoPreSal = new Projeto("Pré-Sal");
		assertEquals("Pré-Sal", projetoPreSal.getNome());
	}
	
	@Test (expected = RuntimeException.class)
	public void CriaProjetoSemNome() throws Exception {
		new Projeto("");
	}
	
	@Test
	public void ProjetoPreSalNovoNaoTemOcorrencias() throws Exception {
		Projeto projetoPreSal = new Projeto("Pré-Sal");
		assertEquals(0, projetoPreSal.getOcorrencias().size());
	}
	
	@Test
	public void PetrobasCriaProjetoPreSal() throws Exception {
		Empresa empresa = new Empresa("Petrobras");
		Projeto projetoPreSal = new Projeto("Pré-Sal");
		empresa.criarProjeto(projetoPreSal);
		assertEquals("Pré-Sal", empresa.getProjeto("Pré-Sal").getNome());
	}
	
	@Test
	public void criaOcorrencia() throws Exception {
		Empresa empresa = new Empresa("Petrobras");
		Projeto projetoPreSal = new Projeto("Pré-Sal");
		empresa.criarProjeto(projetoPreSal);
		projetoPreSal.criaOcorrencia("Problema 1 no Pré-Sal");
		assertEquals("Problema 1 no Pré-Sal", projetoPreSal.getOcorrenciaByName("Problema 1 no Pré-Sal").getNome());
	}
	
	@Test
	public void ProjetoPreSalDaPetrobrasCriaOcorrencia() throws Exception {
		Empresa empresa = new Empresa("Petrobras");
		Projeto projetoPreSal = new Projeto("Pré-Sal");
		projetoPreSal.criaOcorrencia("Problema 1 no Pré-Sal");
		empresa.criarProjeto(projetoPreSal);
		assertEquals("Problema 1 no Pré-Sal", projetoPreSal.getOcorrenciaByName("Problema 1 no Pré-Sal").getNome());
	}
	
	@Test (expected = RuntimeException.class)
	public void ProjetoPreSalDaPetrobrasCriaDuasOcorrenciaComMesmoNome() throws Exception {
		Empresa empresa = new Empresa("Petrobras");
		Projeto projetoPreSal = new Projeto("Pré-Sal");
		empresa.criarProjeto(projetoPreSal);
		projetoPreSal.criaOcorrencia("Problema 1 no Pré-Sal");
		projetoPreSal.criaOcorrencia("Problema 1 no Pré-Sal");
	}
	
	@Test
	public void ProjetoPreSalDaPetrobrasCriaOcorrenciaEResposavel() throws Exception {
		Empresa empresa = new Empresa("Petrobras");
		Projeto projetoPreSal = new Projeto("Pré-Sal");
		empresa.criarProjeto(projetoPreSal);
		Funcionario joao = new Funcionario("João da Silva");
		empresa.contrataFuncionario(joao);
		projetoPreSal.criaOcorrencia("Problema 1 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 1 no Pré-Sal"));
		assertEquals("João da Silva", projetoPreSal.getOcorrenciaByName("Problema 1 no Pré-Sal").getResposavel().getNome());
	}
	
	@Test
	public void FuncionarioResponsavelPorUmaOcorrencia() throws Exception {
		Empresa empresa = new Empresa("Petrobras");
		Projeto projetoPreSal = new Projeto("Pré-Sal");
		empresa.criarProjeto(projetoPreSal);
		Funcionario joao = new Funcionario("João da Silva");
		empresa.contrataFuncionario(joao);
		projetoPreSal.criaOcorrencia("Problema 1 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 1 no Pré-Sal"));
		assertEquals(1, joao.getQtdadeOcorrenciasResponsavel());
	}
	
	@Test  (expected = RuntimeException.class)
	public void FuncionarioResponsavelPorOnzeOcorrencias() throws Exception {
		Empresa empresa = new Empresa("Petrobras");
		Projeto projetoPreSal = new Projeto("Pré-Sal");
		empresa.criarProjeto(projetoPreSal);
		Funcionario joao = new Funcionario("João da Silva");
		empresa.contrataFuncionario(joao);
		projetoPreSal.criaOcorrencia("Problema 1 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 1 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 2 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 2 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 3 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 3 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 4 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 4 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 5 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 5 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 6 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 6 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 7 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 7 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 8 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 8 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 9 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 9 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 10 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 10 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 111 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 11 no Pré-Sal"));
	}
	
	
	
	@Test
	public void ChecarOcorrenciaNaoConcluida() throws Exception {
		Empresa empresa = new Empresa("Petrobras");
		Projeto projetoPreSal = new Projeto("Pré-Sal");
		empresa.criarProjeto(projetoPreSal);
		projetoPreSal.criaOcorrencia("Problema 1 no Pré-Sal");
		Ocorrencia ocorrencia = projetoPreSal.getOcorrenciaByName("Problema 1 no Pré-Sal");
		ocorrencia.setResponsavel(new Funcionario("João da Silva"));
		assertEquals(false, ocorrencia.estaConcluido());
	}
	
	@Test
	public void ChecarOcorrenciaConcluida() throws Exception {
		Empresa empresa = new Empresa("Petrobras");
		Projeto projetoPreSal = new Projeto("Pré-Sal");
		empresa.criarProjeto(projetoPreSal);
		projetoPreSal.criaOcorrencia("Problema 1 no Pré-Sal");
		Ocorrencia ocorrencia = projetoPreSal.getOcorrenciaByName("Problema 1 no Pré-Sal");
		ocorrencia.setResponsavel(new Funcionario("João da Silva"));
		ocorrencia.setConcluido(true);
		assertEquals(true, ocorrencia.estaConcluido());
	}
}
