package ine5448;

import org.junit.Test;

import ine5448.Ocorrencia.Estados;
import ine5448.Ocorrencia.Prioridades;
import ine5448.Ocorrencia.Tipos;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;

public class TestesEmpresa {

	Empresa umaEmpresa;
	Funcionario ana;
	Funcionario beto;
	String nomeProjeto;
	Ocorrencia bug;
	Projeto windows;
	int codigoBug;

	@Before
	public void configuracao() {

		umaEmpresa = new Empresa();
		ana = new Funcionario("ana");
		beto = new Funcionario("beto");
		codigoBug = 1;
		nomeProjeto = "Windows 100";
		bug = new Ocorrencia(codigoBug, Tipos.BUG, Prioridades.ALTA, "Tela Azul");
		windows = new Projeto(nomeProjeto);
		
	}
	
	public void configurarCadastros() throws Exception {
		
		umaEmpresa.cadastrarProjeto(windows);
		umaEmpresa.cadastrarFuncionario(ana);
		umaEmpresa.cadastrarFuncionarioProjeto(ana, windows.obterNome());
		umaEmpresa.cadastrarFuncionario(beto);
		umaEmpresa.cadastrarFuncionarioProjeto(beto, windows.obterNome());		
	}
	
	
	@Test
	public void listarDoisFuncionarios() throws Exception {

		umaEmpresa.cadastrarProjeto(windows);
		umaEmpresa.cadastrarFuncionario(ana);
		umaEmpresa.cadastrarFuncionario(beto);
		assertEquals(2, umaEmpresa.obterFuncionarios().size());

	}

	@Test
	public void listarDoisProjetos() throws Exception {

		Projeto leilao = new Projeto("Leilao");
		Projeto banco = new Projeto("Sistema Bancario");
		umaEmpresa.cadastrarProjeto(leilao);
		umaEmpresa.cadastrarProjeto(banco);
		assertEquals(2, umaEmpresa.obterProjetos().size());

	}
	
	@Test
	public void cadastrarOcorrenciaBug() throws Exception {
		
		configurarCadastros();
		umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, bug, ana.obterNome());
		Ocorrencia ocorrenciaCadastrada = umaEmpresa.obterOcorrenciaDeProjeto(nomeProjeto, codigoBug);
		assertEquals(bug, ocorrenciaCadastrada);		
	}
	
	@Test 
	public void completarOcorrenciaBug() throws Exception{
		
		configurarCadastros();
		umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, bug, ana.obterNome());
		umaEmpresa.atulizarOcorrencia(nomeProjeto, codigoBug, null, Estados.COMPLETADA);
		Ocorrencia bugCadastrado = umaEmpresa.obterOcorrenciaDeProjeto(nomeProjeto, codigoBug);
		Funcionario responsavel = bugCadastrado.obterResponsavel();
		assertEquals(Estados.COMPLETADA, bugCadastrado.obterEstado());
		assertEquals(0, responsavel.obterOcorrencias().size());
	}
	
	@Test
	public void atualizarPrioridadeAltaOcorrencia() throws Exception {
		
		configurarCadastros();
		umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, bug, ana.obterNome());
		umaEmpresa.atulizarOcorrencia(nomeProjeto, codigoBug, Prioridades.BAIXA, null);
		Ocorrencia bugCadastrado = umaEmpresa.obterOcorrenciaDeProjeto(nomeProjeto, codigoBug);
		Funcionario responsavel = bugCadastrado.obterResponsavel();
		assertEquals(Prioridades.BAIXA, bugCadastrado.obterPrioridade());
	}
	
	@Test
	public void ocorrenciaResumoTelaAzul() throws Exception {
		
		configurarCadastros();
		umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, bug, ana.obterNome());
		Ocorrencia bugCadastrado = umaEmpresa.obterOcorrenciaDeProjeto(nomeProjeto, codigoBug);
		assertEquals("Tela Azul", bugCadastrado.obterResumo());
	}
	
	@Test
	public void cadastrar10ocorrenciasWindowsAna() throws Exception {
		
		configurarCadastros();
		int num_ocorrencias = 10;
		for (int i = 0; i < num_ocorrencias; i++) {
			umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, new Ocorrencia(i+1, Tipos.TAREFA, Prioridades.MEDIA, "Tarefa "+i), ana.obterNome());			
		}
		assertEquals(num_ocorrencias, umaEmpresa.obterFuncionario(ana.obterNome()).obterOcorrencias().size());
		assertEquals(num_ocorrencias, umaEmpresa.obterProjeto(nomeProjeto).obterOcorrencias().size());
	}
	
	@Test
	public void novoResponsavelBugBeto() throws Exception {
		
		configurarCadastros();
		umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, bug, ana.obterNome());
		Ocorrencia oBug = umaEmpresa.obterOcorrenciaDeProjeto(nomeProjeto, bug.obterCodigo());
		umaEmpresa.atulizarResponsavelOcorrencia(nomeProjeto, bug.obterCodigo(), beto.obterNome());
		assertEquals(1, umaEmpresa.obterFuncionario(beto.obterNome()).obterOcorrencias().size());
		assertEquals(0,umaEmpresa.obterFuncionario(ana.obterNome()).obterOcorrencias().size());
		
	}
	
	@Test 
	public void doisProjetosDiferentesDuasOcorrenciasAna() throws Exception {
	
		configurarCadastros();
		Projeto windows3000 = new Projeto("windows3000");
		Ocorrencia tarefa = new Ocorrencia(2, Tipos.TAREFA, Prioridades.ALTA, "Corrigir Paint");
		umaEmpresa.cadastrarProjeto(windows3000);
		umaEmpresa.cadastrarFuncionarioProjeto(ana, windows3000.obterNome());
		umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, bug, ana.obterNome());
		umaEmpresa.cadastrarOcorrenciaDeProjeto(windows3000.obterNome(), tarefa, ana.obterNome());
		Funcionario recuperarAna = umaEmpresa.obterFuncionario(ana.obterNome());
		assertEquals(2, recuperarAna.obterOcorrencias().size());
		assertEquals(2, recuperarAna.obterProjetos().size());
	
	}
	
	
	@Test(expected=Exception.class)
	public void cadastrar11ocorrenciasAna() throws Exception {
		
		configurarCadastros();
		int num_ocorrencias = 11;
		for (int i = 0; i < num_ocorrencias; i++) {
			umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, new Ocorrencia(i+1, Tipos.TAREFA, Prioridades.MEDIA, "Tarefa "+i), ana.obterNome());			
		}
		
	}
	
	
	@Test(expected=Exception.class)
	public void projetoInexistenteUbuntdows() throws Exception {
		
		umaEmpresa.obterProjeto("Ubuntdows");		
	}
	
	
	@Test(expected=Exception.class)
	public void funcionarioInexistenteOlegario() throws Exception {
		
		umaEmpresa.obterFuncionario("Olegario");
	}
	

	@Test(expected=Exception.class)
	public void ocorrenciaInexistenteNegativa() throws Exception {
		
		int codigoNegativo = -1;
		configurarCadastros();
		umaEmpresa.obterOcorrenciaDeProjeto(nomeProjeto, codigoNegativo );
	}
	
	@Test(expected=Exception.class)
	public void ocorrenciaCodigoExistente() throws Exception {
		
		configurarCadastros();
		umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, bug, ana.obterNome());
		Ocorrencia melhoria = new Ocorrencia(1, Tipos.MELHORIA, Prioridades.BAIXA, "Otimizacao de algoritmo");
		umaEmpresa.cadastrarOcorrenciaDeProjeto(nomeProjeto, melhoria, beto.obterNome());;
	}
		
	
}
