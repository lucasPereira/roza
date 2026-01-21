package gerenciador;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.*;
import java.util.Collection.*;

import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;

import calculadora.Calculadora;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class TestesOcorrencia {

		private Empresa empresa;
		private Projeto projeto;
		private Ocorrencia ocorrencia;

		@Before
		public void configurar() {
			empresa = new Empresa("Gerenciador de Ocorrências");
			projeto = empresa.criarProjeto("TDD");
			ocorrencia = projeto.criarOcorrencia("Ocorrência 01", "Descrição 01", Tipo.TAREFA, Prioridade.ALTA);
		}
		
		@Test
		public void criarOcorrenciaComConstrutor() throws Exception {
			// Verifica se a ocorrencia foi criada corretamente utilizando seu construtor. Ao ser criada, a ocorrencia não possui um responsável
			ocorrencia = new Ocorrencia("Ocorrência 01", "Descrição 01", Tipo.TAREFA, Prioridade.ALTA);
			assertThat(ocorrencia.getTitulo(), equalTo("Ocorrência 01"));
			assertThat(ocorrencia.getResumo(), equalTo("Descrição 01"));
			assertThat(ocorrencia.getTipo(), equalTo(Tipo.TAREFA));
			assertThat(ocorrencia.getPrioridade(), equalTo(Prioridade.ALTA));
			assertThat(ocorrencia.getEstado(), equalTo(Estado.ABERTA));
			assertThat(ocorrencia.getResponsavel(), is(nullValue()));
		}

		@Test
		public void checarUnicidadeDaChaveDaOcorrência() throws Exception {
			// Verifica se a ocorrencia possui chave unica
			Ocorrencia outraOcorrencia = projeto.criarOcorrencia("Ocorrência 02", "Descrição 02", Tipo.TAREFA, Prioridade.ALTA);
			assertThat(ocorrencia.getChave(), not(equalTo(outraOcorrencia.getChave())));
		}
		
		@Test
		public void existemTrêsTiposDeOcorrência() throws Exception {
			// Verifica se é possível criar 3 tipos de ocorrência: tarefa, bug, melhoria
			Ocorrencia ocorrenciaTipoBug = projeto.criarOcorrencia("Ocorrência 02", "Resumo 02", Tipo.BUG, Prioridade.ALTA);
			Ocorrencia ocorrenciaTipoMelhoria = projeto.criarOcorrencia("Ocorrência 03", "Resumo 03", Tipo.MELHORIA, Prioridade.ALTA);
			assertThat(ocorrencia.getTipo(), equalTo(Tipo.TAREFA));
			assertThat(ocorrenciaTipoBug.getTipo(), equalTo(Tipo.BUG));
			assertThat(ocorrenciaTipoMelhoria.getTipo(), equalTo(Tipo.MELHORIA));
		}
		
		@Test
		public void existemTrêsTiposDePrioridade() throws Exception {
			// Verifica se é possível atribuir 3 tipos diferentes de prioridade para ocorrencias: alta, média, baixa
			Ocorrencia ocorrenciaDePrioridadeMedia = projeto.criarOcorrencia("Ocorrência 02", "Resumo 02", Tipo.BUG, Prioridade.MEDIA);
			Ocorrencia ocorrenciaDePrioridadeBaixa = projeto.criarOcorrencia("Ocorrência 03", "Resumo 03", Tipo.MELHORIA, Prioridade.BAIXA);
			assertThat(ocorrencia.getPrioridade(), equalTo(Prioridade.ALTA));
			assertThat(ocorrenciaDePrioridadeMedia.getPrioridade(), equalTo(Prioridade.MEDIA));
			assertThat(ocorrenciaDePrioridadeBaixa.getPrioridade(), equalTo(Prioridade.BAIXA));
		}
		
		@Test
		public void prioridadeDaOcorrenciaPodeSerModificada() throws Exception {
			// Verifica se é possível modificar a prioridade da ocorrência
			ocorrencia.setPrioridade(Prioridade.BAIXA);
			assertThat(ocorrencia.getPrioridade(), equalTo(Prioridade.BAIXA));
		}
		
		@Test
		public void concluirOcorrência() throws Exception {
			// Verifica se é possível concluir uma ocorrência
			Funcionario funcionario = empresa.criarFuncionario("Fabiano Manschein");
			empresa.atribuirFuncionarioParaProjeto(funcionario, projeto);
			projeto.atribuirResponsavelParaOcorrencia(ocorrencia, funcionario);
			boolean sucesso = funcionario.concluirOcorrencia(ocorrencia);
			assertThat(ocorrencia.getEstado(), equalTo(Estado.CONCLUIDA));
			assertThat(sucesso, equalTo(true));
		}
		
		@Test
		public void ocorrênciaSòPodeSerCompletadaPeloResponsável() throws Exception {
			// Verifica se somente o responsável pela ocorrência pode concluí-la
			Funcionario responsavel = empresa.criarFuncionario("Fabiano Manschein");
			Funcionario funcionarioQualquer = empresa.criarFuncionario("Leonardo Molinari");
			empresa.atribuirFuncionarioParaProjeto(responsavel, projeto);
			empresa.atribuirFuncionarioParaProjeto(funcionarioQualquer, projeto);
			projeto.atribuirResponsavelParaOcorrencia(ocorrencia, responsavel);
			boolean sucesso = funcionarioQualquer.concluirOcorrencia(ocorrencia);
			assertThat(ocorrencia.getEstado(), equalTo(Estado.ABERTA));
			assertThat(sucesso, equalTo(false));
		}
		

		
		
		

}
