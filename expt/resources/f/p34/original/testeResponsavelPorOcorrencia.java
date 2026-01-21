package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.*;

class testeResponsavelPorOcorrencia {
	
	public Projeto projetoWPP;
	public Funcionario funcionarioLuiz;
	public Funcionario funcionarioPaulo;
	public Empresa empresaBrasil;
	
	public void CriaOcorrencias(Integer quantidade, Projeto projetoAtual, Funcionario funcionarioResponsavel){
		for (int i = 1; i <= quantidade; i++) {
			projetoAtual.criarOcorrencia(funcionarioResponsavel,TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar alguma coisa " + Integer.toString(i));
		}
	}

	@BeforeEach
	void setUp() throws Exception {
		empresaBrasil = new Empresa("Empresa Brasil");
		empresaBrasil.contratarFuncionario("Luiz Fernando");
		funcionarioLuiz = empresaBrasil.getFuncionario(0);
		
		empresaBrasil.contratarFuncionario("Paulo");
		funcionarioPaulo = empresaBrasil.getFuncionario(1);
		
		empresaBrasil.adicionarProjeto("WPP");
		projetoWPP = empresaBrasil.getProjeto(0);
		projetoWPP.adicionarFuncionario(funcionarioLuiz);
		projetoWPP.adicionarFuncionario(funcionarioPaulo);
		projetoWPP.criarOcorrencia(funcionarioLuiz,TipoDeOcorrencia.TAREFA, Prioridade.ALTA, "Adicionar feature nessa sprint");
	}

	@Test // 15
	void atribuirDezOcorrencias() throws Exception {
		
		// usar um delegated set-up fora do caso de teste
		CriaOcorrencias(9, projetoWPP, funcionarioLuiz);
		assertEquals(10, projetoWPP.getNumeroDeOcorrencias());
		assertEquals(10, funcionarioLuiz.getNumeroOcorrencias());
	}
	
	@Test // 16
	void atribuirOnzeOcorrencias() throws Exception {
		
		CriaOcorrencias(10, projetoWPP, funcionarioLuiz);

		assertEquals(11, projetoWPP.getNumeroDeOcorrencias());
		assertEquals(10, funcionarioLuiz.getNumeroOcorrencias());
	}
	
	@Test // 17
	void trocarUmFuncionarioDeUmaOcorrenciaFechada() throws Exception {
		projetoWPP.fecharOcorrencia(0);
		projetoWPP.trocarFuncionarioResponsavel(0, funcionarioPaulo);
		
		assertEquals(0, funcionarioPaulo.getNumeroOcorrencias());
		assertEquals(0, funcionarioLuiz.getNumeroOcorrencias());
	}
	
	@Test // 18
	void trocarUmFuncionarioDeUmaOcorrencia() throws Exception {
		projetoWPP.trocarFuncionarioResponsavel(0, funcionarioPaulo);
		
		assertEquals(1, funcionarioPaulo.getNumeroOcorrencias());
		assertEquals(0, funcionarioLuiz.getNumeroOcorrencias());
	}
	
	@Test // 19
	void trocarDoisFuncionariosDeDuasOcorrencias() throws Exception {
		projetoWPP.criarOcorrencia(funcionarioLuiz,TipoDeOcorrencia.TAREFA, Prioridade.ALTA, "Arrumar alguma coisa");
		projetoWPP.trocarFuncionarioResponsavel(0, funcionarioPaulo);
		projetoWPP.trocarFuncionarioResponsavel(1, funcionarioPaulo);
		
		assertEquals(2, funcionarioPaulo.getNumeroOcorrencias());
		assertEquals(0, funcionarioLuiz.getNumeroOcorrencias());
	}
	
	@Test // 20
	void fecharUmaOcorrenciaSemFuncionarioResponsavel() throws Exception {
		CriaOcorrencias(10, projetoWPP, funcionarioLuiz);
		
		projetoWPP.fecharOcorrencia(10);
		
		assertEquals(11, projetoWPP.getNumeroDeOcorrencias());
		assertEquals(10, funcionarioLuiz.getNumeroOcorrencias());
	}
	
	@Test // 21
	void fecharOcorrenciaAberta() throws Exception {
		projetoWPP.fecharOcorrencia(0);
		
		assertEquals(0, projetoWPP.getNumeroDeOcorrencias());
		assertEquals(0, funcionarioLuiz.getNumeroOcorrencias());
	}
	
	@Test // 22
	void trocarPrioridadeDeUmaOcorrenciaFechada() throws Exception {
		
		Ocorrencia manutencao = projetoWPP.getOcorrencia(0);
		projetoWPP.fecharOcorrencia(0);
		projetoWPP.trocarPrioridade(0, Prioridade.MEDIA);
		assertEquals(Prioridade.ALTA,manutencao.prioridade);
		assertEquals(0, projetoWPP.getNumeroDeOcorrencias());
		assertEquals(0, funcionarioLuiz.getNumeroOcorrencias());
	}
	
	@Test // 23
	void trocarPrioridadeDeUmaOcorrencia() throws Exception {
		projetoWPP.trocarPrioridade(0, Prioridade.ALTA);
		assertEquals(Prioridade.ALTA,projetoWPP.getPrioridade(0));
	}
	
	@Test // 24
	void trocarPrioridadeDeDuasOcorrencias() throws Exception {
		projetoWPP.criarOcorrencia(funcionarioLuiz,TipoDeOcorrencia.BUG, Prioridade.ALTA, "Arrumar alguma coisa");

		projetoWPP.trocarPrioridade(0, Prioridade.ALTA);
		projetoWPP.trocarPrioridade(1, Prioridade.MEDIA);
		
		assertEquals(Prioridade.ALTA, projetoWPP.getPrioridade(0));
		assertEquals(Prioridade.MEDIA, projetoWPP.getPrioridade(1));
	}

}
