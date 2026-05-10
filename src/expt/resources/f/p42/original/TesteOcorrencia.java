package testes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import exceptions.OcorrenciaException;
import modelos.Empresa;
import modelos.Funcionario;
import modelos.Ocorrencia;
import modelos.Ocorrencia.Prioridade;
import modelos.Ocorrencia.Tipo;
import modelos.Projeto;

public class TesteOcorrencia {
	private Empresa empresa;
	private Projeto projeto;

	@BeforeEach
	public void configurar() {
		empresa = new Empresa("Empresa do Joao");
		projeto = empresa.criaProjeto("Projeto X");
	}
	
	@Test // Teste adicionado na revisão
	void testeVerificaProjetoSemOcorrencias() throws Exception {
		assertThrows(OcorrenciaException.class, () -> {
			projeto.obterOcorrencia(0);
		});
	}
	
	@Test
	void testeCriaUmaOcorrenciaDoProjetoX() throws Exception {
		Ocorrencia ocorrencia = projeto.criaOcorrencia();
		assertEquals(ocorrencia, projeto.obterOcorrencia(0));
	}
	
	@Test
	void testeCriaDuasOcorrenciasDoProjetoX() throws Exception {
		Ocorrencia ocorrencia1 = projeto.criaOcorrencia();
		Ocorrencia ocorrencia2 = projeto.criaOcorrencia();
		List<Ocorrencia> lista = new ArrayList<>();
		lista.add(ocorrencia1);
		lista.add(ocorrencia2);
		
		assertEquals(ocorrencia1, projeto.obterOcorrencia(0));
		assertEquals(ocorrencia2, projeto.obterOcorrencia(1));
		assertEquals(lista, projeto.obterOcorrencias());
		assertEquals(2, projeto.obterOcorrencias().size());
		assertNotEquals(ocorrencia1.obterID(), ocorrencia2.obterID());
	}
	
	@Test
	void testeCriaResumoParaOcorrencia() throws Exception {
		Ocorrencia ocorrencia = projeto.criaOcorrencia();
		ocorrencia.criaResumo("Este é o resumo da Ocorrencia 1");
		assertEquals("Este é o resumo da Ocorrencia 1", projeto.obterOcorrencia(0).obterResumo());
	}
	
	@Test
	void testeDefineResponsavelAOcorrencia() throws Exception {
		Ocorrencia ocorrencia = projeto.criaOcorrencia();
		Funcionario joao = empresa.criaFuncionario("Joao da Silva");
		ocorrencia.defineResponsavel(joao);
		assertEquals(joao, projeto.obterOcorrencia(0).obterResponsavel());
	}
	
	@Test
	void testeDefineResponsavelAOcorrenciaCompletada() throws Exception {
		Ocorrencia ocorrencia = projeto.criaOcorrencia();
		ocorrencia.defineEstado(Ocorrencia.Estado.COMPLETADA);
		Funcionario joao = empresa.criaFuncionario("Joao da Silva");
		assertThrows(OcorrenciaException.class, () -> {
	    	ocorrencia.defineResponsavel(joao);
		});
	}
	
//	@Test
//	void testeDefineTipoDeOcorrenciasTarefa() throws Exception {
//		Ocorrencia ocorrencia = projeto.criaOcorrencia();
//		ocorrencia.defineTipo(Ocorrencia.Tipo.TAREFA);
//		assertEquals(Ocorrencia.Tipo.TAREFA, projeto.obterOcorrencia(0).obterTipo());
//	}
//	
//	@Test
//	void testeDefineTipoDeOcorrenciasBug() throws Exception {
//		Ocorrencia ocorrencia = projeto.criaOcorrencia();
//		ocorrencia.defineTipo(Ocorrencia.Tipo.BUG);
//		assertEquals(Ocorrencia.Tipo.BUG, projeto.obterOcorrencia(0).obterTipo());
//	}
//	
//	@Test
//	void testeDefineTipoDeOcorrenciasMelhoria() throws Exception {
//		Ocorrencia ocorrencia = projeto.criaOcorrencia();
//		ocorrencia.defineTipo(Ocorrencia.Tipo.MELHORIA);
//		assertEquals(Ocorrencia.Tipo.MELHORIA, projeto.obterOcorrencia(0).obterTipo());
//	}
	
	@ParameterizedTest
	@EnumSource(Tipo.class)
	void testeParametrizadoDefineTipoDeOcorrenciasTarefaBugMelhoria(Tipo tipo) throws Exception {
		Ocorrencia ocorrencia = projeto.criaOcorrencia();
		ocorrencia.defineTipo(tipo);
		assertEquals(tipo, projeto.obterOcorrencia(0).obterTipo());
	}
	
//	@Test
//	void testeDefinePrioridadeAltaAOcorrencia() throws Exception {
//		Ocorrencia ocorrencia = projeto.criaOcorrencia();
//		ocorrencia.definePrioridade(Ocorrencia.Prioridade.ALTA);
//		assertEquals(Ocorrencia.Prioridade.ALTA, projeto.obterOcorrencia(0).obterPrioridade());
//	}
//	
//	@Test
//	void testeDefinePrioridadeMediaAOcorrencia() throws Exception {
//		Ocorrencia ocorrencia = projeto.criaOcorrencia();
//		ocorrencia.definePrioridade(Ocorrencia.Prioridade.MEDIA);
//		assertEquals(Ocorrencia.Prioridade.MEDIA, projeto.obterOcorrencia(0).obterPrioridade());
//	}
//	
//	@Test
//	void testeDefinePrioridadeBaixaAOcorrencia() throws Exception {
//		Ocorrencia ocorrencia = projeto.criaOcorrencia();
//		ocorrencia.definePrioridade(Ocorrencia.Prioridade.BAIXA);
//		assertEquals(Ocorrencia.Prioridade.BAIXA, projeto.obterOcorrencia(0).obterPrioridade());
//	}
	
	@ParameterizedTest
	@EnumSource(Prioridade.class)
	void testeParametrizadoDefinePrioridadeAltaMediaBaixa(Prioridade prioridade) throws Exception {
		Ocorrencia ocorrencia = projeto.criaOcorrencia();
		ocorrencia.definePrioridade(prioridade);
		assertEquals(prioridade, projeto.obterOcorrencia(0).obterPrioridade());
	}
	
	@Test
	void testeDefinePrioridadeAltaAOcorrenciaCompletada() throws Exception {
		Ocorrencia ocorrencia = projeto.criaOcorrencia();
		ocorrencia.defineEstado(Ocorrencia.Estado.COMPLETADA);
		assertThrows(OcorrenciaException.class, () -> {
	    	ocorrencia.definePrioridade(Ocorrencia.Prioridade.ALTA);
		});
	}
	
	@Test
	void testeDefineResponsabildiadeDeDezOcorrenciasParaFuncionario() throws Exception {
		Funcionario joao = empresa.criaFuncionario("Joao da Silva");
		criaOcorrenciasEDefineResponsavel(10, joao);
		assertEquals(10, joao.obterOcorrencias().size());
	}
	
	@Test
	void testeDefineResponsabildiadeDeOnzeOcorrenciasParaFuncionario() throws Exception {
		Funcionario joao = empresa.criaFuncionario("Joao da Silva");
		assertThrows(OcorrenciaException.class, () -> {
			criaOcorrenciasEDefineResponsavel(11, joao);
		});
	}
	
	//Metodo para Delegated Setup
	void criaOcorrenciasEDefineResponsavel(int qtdOcorrencias, Funcionario funcionario) throws OcorrenciaException {
		for (int i = 0; i < qtdOcorrencias; i++) {
			Ocorrencia ocorrencia = projeto.criaOcorrencia();
			ocorrencia.defineResponsavel(funcionario);
		}
	}
}
