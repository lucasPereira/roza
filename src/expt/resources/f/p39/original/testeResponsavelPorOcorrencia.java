package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exercicioTdd.Empresa;
import exercicioTdd.Funcionario;
import exercicioTdd.Ocorrencia;
import exercicioTdd.Prioridade;
import exercicioTdd.Projeto;
import exercicioTdd.TipoDeOcorrencia;

class testeResponsavelPorOcorrencia {
	
	public Projeto projeto;
	public Funcionario funcionario;
	public Ocorrencia ocorrencia;
	public Funcionario mariana;
	public Empresa empresa;
	

	@BeforeEach
	void setUp() throws Exception {
		empresa = new Empresa("Empresa grande");
		empresa.contratarFuncionario("Marina");
		funcionario = empresa.pegarFuncionario(0);
		
		empresa.contratarFuncionario("mariana");
		mariana = empresa.pegarFuncionario(1);
		
		empresa.adicionarProjeto("ZAP");
		projeto = empresa.pegarProjeto(0);
		projeto.adicionarFuncionario(funcionario);
		projeto.adicionarFuncionario(mariana);
		
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar margem do componente");
	}

	@Test
	void criarDezOcorrencias() throws Exception {
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.BUG, Prioridade.ALTA, "Arrumar margem do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.TAREFA, Prioridade.MEDIA, "Arrumar padding do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar espaçamento do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.TAREFA, Prioridade.MEDIA, "Arrumar cor do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.BUG, Prioridade.BAIXA, "Arrumar fonte do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.TAREFA, Prioridade.ALTA, "Arrumar fundo do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar cor do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.TAREFA, Prioridade.BAIXA, "Arrumar borda do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar largura do componente");

		assertEquals(10, projeto.pegarNumeroDeOcorrencias());
		assertEquals(10, funcionario.pegarNumeroOcorrencias());
	}
	
	@Test
	void criarOnzeOcorrencias() throws Exception {
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.BUG, Prioridade.ALTA, "Arrumar margem do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.TAREFA, Prioridade.MEDIA, "Arrumar padding do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar espaçamento do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.TAREFA, Prioridade.MEDIA, "Arrumar cor do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.BUG, Prioridade.BAIXA, "Arrumar fonte do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.TAREFA, Prioridade.ALTA, "Arrumar fundo do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar cor do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.TAREFA, Prioridade.BAIXA, "Arrumar borda do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar largura do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.TAREFA, Prioridade.MEDIA, "Arrumar altura do componente");

		assertEquals(11, projeto.pegarNumeroDeOcorrencias());
		assertEquals(10, funcionario.pegarNumeroOcorrencias());
	}
	
	@Test
	void trocarUmFuncionarioDeUmaOcorrenciaFechada() throws Exception {
		projeto.fecharOcorrencia(0);
		projeto.trocarFuncionarioResponsavel(0, mariana);
		
		assertEquals(0, mariana.pegarNumeroOcorrencias());
		assertEquals(0, funcionario.pegarNumeroOcorrencias());
	}
	
	@Test
	void trocarUmFuncionarioDeUmaOcorrencia() throws Exception {
		projeto.trocarFuncionarioResponsavel(0, mariana);
		
		assertEquals(1, mariana.pegarNumeroOcorrencias());
		assertEquals(0, funcionario.pegarNumeroOcorrencias());
	}
	
	@Test
	void trocarDoisFuncionariosDeDuasOcorrencias() throws Exception {
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.TAREFA, Prioridade.ALTA, "Arrumar espaçamento do componente");
		projeto.trocarFuncionarioResponsavel(0, mariana);
		projeto.trocarFuncionarioResponsavel(1, mariana);
		
		assertEquals(2, mariana.pegarNumeroOcorrencias());
		assertEquals(0, funcionario.pegarNumeroOcorrencias());
	}
	
	@Test
	void fecharUmaOcorrenciaSemFuncionarioResponsavel() throws Exception {
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.BUG, Prioridade.ALTA, "Arrumar margem do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.TAREFA, Prioridade.MEDIA, "Arrumar padding do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar espaçamento do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.TAREFA, Prioridade.MEDIA, "Arrumar cor do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.BUG, Prioridade.BAIXA, "Arrumar fonte do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.TAREFA, Prioridade.ALTA, "Arrumar fundo do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar cor do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.TAREFA, Prioridade.BAIXA, "Arrumar borda do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar largura do componente");
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.TAREFA, Prioridade.MEDIA, "Arrumar altura do componente");
		
		projeto.fecharOcorrencia(10);
		
		assertEquals(11, projeto.pegarNumeroDeOcorrencias());
		assertEquals(10, funcionario.pegarNumeroOcorrencias());
	}
	
	@Test
	void fecharOcorrenciaAberta() throws Exception {
		projeto.fecharOcorrencia(0);
		
		assertEquals(0, projeto.pegarNumeroDeOcorrencias());
		assertEquals(0, funcionario.pegarNumeroOcorrencias());
	}
	
	@Test
	void trocarPrioridadeDeUmaOcorrenciaFechada() throws Exception {
		projeto.fecharOcorrencia(0);
		projeto.trocarPrioridade(0, Prioridade.ALTA);
		
		assertEquals(0, projeto.pegarNumeroDeOcorrencias());
		assertEquals(0, funcionario.pegarNumeroOcorrencias());
	}
	
	@Test
	void trocarPrioridadeDeUmaOcorrencia() throws Exception {
		projeto.trocarPrioridade(0, Prioridade.ALTA);
		
		assertEquals(Prioridade.ALTA, projeto.pegarPrioridade(0));
	}
	
	@Test
	void trocarPrioridadeDeDuasOcorrencias() throws Exception {
		projeto.criarOcorrencia(funcionario,TipoDeOcorrencia.BUG, Prioridade.ALTA, "Arrumar margem do componente");

		projeto.trocarPrioridade(0, Prioridade.ALTA);
		projeto.trocarPrioridade(1, Prioridade.MEDIA);
		
		assertEquals(Prioridade.ALTA, projeto.pegarPrioridade(0));
		assertEquals(Prioridade.MEDIA, projeto.pegarPrioridade(1));
	}

}
