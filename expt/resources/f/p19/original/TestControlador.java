package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import controller.Controlador;
import model.Funcionario;
import model.Ocorrencia;
import model.Ocorrencia.Estado;
import model.Ocorrencia.Prioridade;
import model.Ocorrencia.Tipo;
import model.Projeto;

public class TestControlador {
	
	private Controlador controlador;
	private Funcionario funcionario;
	private Projeto projeto;
	private String resumoOcorrencia = "Alterar botão 'adicionar amigo'";
	private Tipo tipoOcorrencia = Tipo.MELHORIA;
	private Prioridade prioridadeOcorrencia = Prioridade.BAIXA;

	@Before
	public void setUp() throws Exception {
		controlador = new Controlador();
		funcionario = controlador.cadastrarFuncionario("Joao"); // id = 1
		projeto = controlador.cadastrarProjeto("Propaganda na home");
	}

	@Test
	public void testCriarControladorEmpresa() {
		Controlador controladorTeste = new Controlador();
		assertEquals(0, controladorTeste.getFuncionarios().size());
		assertEquals(0, controladorTeste.getOcorrencias().size());
		assertEquals(0, controladorTeste.getProjetos().size());
	}
	
	@Test
	public void testCadastrarFuncionario() throws Exception {
		Funcionario funcionarioTeste = controlador.cadastrarFuncionario("Maria"); // id = 2
		assertEquals(2, funcionarioTeste.getId());
		assertEquals(2, controlador.getFuncionarios().size());
		assertTrue(controlador.existeFuncionario(funcionarioTeste.getId()));
	}
	
	@Test
	public void testCadastrarProjeto() throws Exception {
		Projeto projetoTeste = controlador.cadastrarProjeto("Sugestão de amigos");
		assertEquals(2, projetoTeste.getId());
		assertEquals(2, controlador.getProjetos().size());
		assertTrue(controlador.existeProjeto(projetoTeste.getId()));
	}
	
	@Test
	public void testCadastrarOcorrencia() throws Exception {
		Ocorrencia ocorrenciaTeste = controlador.cadastrarOcorrencia("Mudar background da home",
													Tipo.MELHORIA, Prioridade.BAIXA,
													funcionario.getId(),
													projeto.getId());
		assertEquals(1, ocorrenciaTeste.getId());
		assertEquals(1, controlador.getOcorrencias().size());
		assertTrue(controlador.existeOcorrencia(ocorrenciaTeste.getId()));
		assertEquals(1, funcionario.getQuantidadeDeOcorrencias());
		assertTrue(projeto.getOcorrencias().contains(ocorrenciaTeste));
		
	}
	
	@Test
	public void testCadastrarDezOcorrenciasParaFuncionario() throws Exception {
		int totalOcorrencias = 10; // funcionário já tem 1 ocorrência atribuída
		gerarNOcorrencias(totalOcorrencias);
		assertEquals(10, funcionario.getQuantidadeDeOcorrencias());
		assertEquals(10, controlador.getOcorrencias().size());
		assertEquals(10, projeto.getOcorrencias().size());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testCadastrarOnzeOcorrenciasParaFuncionario() throws Exception {
		int totalOcorrencias = 11;
		gerarNOcorrencias(totalOcorrencias);
	}
	

	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarOcorrenciaFuncionarioNaoExistente() throws Exception {
		int idFuncionarioInvalido = 1000;
		controlador.cadastrarOcorrencia(resumoOcorrencia, tipoOcorrencia,
										prioridadeOcorrencia, projeto.getId(), idFuncionarioInvalido);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarOcorrenciaProjetoNaoExistente() throws Exception {
		int idProjetoInvalido = 1000;
		controlador.cadastrarOcorrencia(resumoOcorrencia, tipoOcorrencia,
										prioridadeOcorrencia, idProjetoInvalido, funcionario.getId());
	}
	
	@Test
	public void testFinalizarOcorrencia() throws Exception {
		Ocorrencia ocorrencia = controlador.cadastrarOcorrencia(resumoOcorrencia, tipoOcorrencia,
				prioridadeOcorrencia, projeto.getId(), funcionario.getId());

		controlador.finalizarOcorrencia(1);
		Set<Ocorrencia> ocorrenciasDoProjeto = projeto.getOcorrencias();
		Ocorrencia ocorrenciaDoProjeto = ocorrenciasDoProjeto.iterator().next();
		
		assertEquals(0, funcionario.getQuantidadeDeOcorrencias());
		assertEquals(Estado.FINALIZADA, ocorrencia.getEstado());
		assertEquals(Estado.FINALIZADA, ocorrenciaDoProjeto.getEstado());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testFinalizarOcorrenciaNaoExistente() throws Exception {
		int idOcorrenciaInvalida = 1000;
		controlador.finalizarOcorrencia(idOcorrenciaInvalida);
	}
	
	private void gerarNOcorrencias(int n) {
		for (int i = 0; i < n; i++) {
			controlador.cadastrarOcorrencia(resumoOcorrencia, tipoOcorrencia,
					prioridadeOcorrencia, projeto.getId(), funcionario.getId());
		}

	}
	
	
	

	
}
