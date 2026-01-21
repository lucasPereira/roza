package gerenciador;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.*;
import java.util.Collection.*;

import org.junit.Before;
import org.junit.Test;

import calculadora.Calculadora;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class TestesProjeto {

	private Empresa empresa;
	private Projeto projeto;

	@Before
	public void configurar() {
		empresa = new Empresa("Gerenciador de Ocorrências");
		projeto = empresa.criarProjeto("TDD");
	}
	
	@Test
	public void criarProjetoComConstrutor() throws Exception {
		// Verifica se o Projeto foi criado corretamente utilizando seu construtor. As listas de ocorrencia e funcionarem devem iniciar vazias
		projeto = new Projeto("TDD");
		assertThat(projeto.getNome(), equalTo("TDD"));
		assertThat(projeto.getListaDeOcorrencias().size(), equalTo(0));
		assertThat(projeto.getListaDeFuncionarios().size(), equalTo(0));
	}
	
	@Test
	public void adicionarOcorrencia() throws Exception {
		// Verifica se a Ocorrencia foi adicionada à lista de ocorrencias do Projeto
		Ocorrencia novaOcorrencia = projeto.criarOcorrencia("Ocorrência 01", "Descrição 01", Tipo.MELHORIA, Prioridade.ALTA);
		assertThat(projeto.getListaDeOcorrencias(), hasItem(novaOcorrencia));
		assertThat(projeto.getListaDeOcorrencias().size(), equalTo(1));
	}
	
	@Test
	public void atribuirResponsavelParaOcorrencia() throws Exception {
		// Verifica se um Funcionario foi atribuido como responsavel para a Ocorrencia criada
		Funcionario funcionario = empresa.criarFuncionario("Fabiano Manschein");
		Ocorrencia ocorrencia = projeto.criarOcorrencia("Ocorrência 01", "Descrição 01", Tipo.MELHORIA, Prioridade.ALTA);
		Boolean sucesso = projeto.atribuirResponsavelParaOcorrencia(ocorrencia, funcionario);
		assertThat(ocorrencia.getResponsavel(), equalTo(funcionario));
		assertThat(funcionario.getListaDeOcorrencias(), hasItem(ocorrencia));
		assertThat(funcionario.getListaDeOcorrencias().size(), equalTo(1));
		assertThat(sucesso, equalTo(true));
	}
	
	@Test
	public void responsavelDaOcorrenciaPodeSerModificado() throws Exception {
		// Verifica se o responsavel da ocorrencia é alterado corretamento
		Funcionario exResponsavel = empresa.criarFuncionario("Fabiano Manschein");
		Funcionario novoResponsavel = empresa.criarFuncionario("Leonardo Molinari");
		Ocorrencia ocorrencia = projeto.criarOcorrencia("Ocorrência 01", "Resumo 01", Tipo.TAREFA, Prioridade.ALTA);
		empresa.atribuirFuncionarioParaProjeto(exResponsavel, projeto);
		empresa.atribuirFuncionarioParaProjeto(novoResponsavel, projeto);
		projeto.atribuirResponsavelParaOcorrencia(ocorrencia, exResponsavel);
		
		projeto.atribuirResponsavelParaOcorrencia(ocorrencia, novoResponsavel);
		
		assertThat(ocorrencia.getResponsavel(), equalTo(novoResponsavel));
		assertThat(exResponsavel.getListaDeOcorrencias(), not(hasItem(ocorrencia)));
		assertThat(exResponsavel.getListaDeOcorrencias().size(), equalTo(0));
		assertThat(novoResponsavel.getListaDeOcorrencias(), hasItem(ocorrencia));
		assertThat(novoResponsavel.getListaDeOcorrencias().size(), equalTo(1));
	}
	
	@Test
	public void responsavelEPrioridadeNaoModificaveisEmOcorrenciasConcluidas() throws Exception {
		// Responsavel e Prioridade da ocorrencia não podem ser modificados quando o estado for "CONCLUIDA"
		Ocorrencia ocorrencia = projeto.criarOcorrencia("Ocorrência 01", "Descrição 01", Tipo.MELHORIA, Prioridade.ALTA);
		Funcionario responsavel = empresa.criarFuncionario("Fabiano Manschein");
		Funcionario novoResponsavel = empresa.criarFuncionario("Leonardo Molinari");
		empresa.atribuirFuncionarioParaProjeto(responsavel, projeto);
		empresa.atribuirFuncionarioParaProjeto(novoResponsavel, projeto);
		projeto.atribuirResponsavelParaOcorrencia(ocorrencia, responsavel);
		responsavel.concluirOcorrencia(ocorrencia);
		
		boolean sucessoAlterarResponsavel = projeto.atribuirResponsavelParaOcorrencia(ocorrencia, novoResponsavel);
		boolean sucessoAlterarPrioridade = ocorrencia.setPrioridade(Prioridade.BAIXA);

		assertThat("Foi possível alterar o responsável de uma ocorrência concluída!", sucessoAlterarResponsavel, equalTo(false));
		assertThat("Foi possível alterar a prioridade de uma ocorrência concluída!", sucessoAlterarPrioridade, equalTo(false));
	}
	
	
	
	
	
}
