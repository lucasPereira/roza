package testes;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.Test;
import domain.Empresa;
import domain.Funcionario;
import domain.Ocorrencia;
import domain.Projeto;
import domain.enums.PRIORIDADE;
import domain.enums.TIPO;

public class FuncionarioTestes {

	@Test
	public void umFuncionarioCriadoDeveTerUmNome() {
		String nomeFuncionario = "Fabio";
		Funcionario funcionario = new Funcionario(nomeFuncionario);
		
		assertEquals(nomeFuncionario, funcionario.obterNome());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void umFuncionarioNaoPodeTerUmNomeNulo() {
		String nomeFuncionario = null;
		new Funcionario(nomeFuncionario);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void umFuncionarioNaoPodeTerUmNomeVazio() {
		String nomeFuncionario = "";
		new Funcionario(nomeFuncionario);
	}
	
	@Test
	public void deveLocalizarEObterFuncionarioPeloNome() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		java.util.List<String> nomesFunc = java.util.List.of("Fabio", "Bruno");
		empresa.adicionarFuncionario(nomesFunc.get(0));
		empresa.adicionarFuncionario(nomesFunc.get(1));
		
		assertEquals(nomesFunc.get(0), empresa.obterFuncionarioPeloNome(nomesFunc.get(0)).obterNome());
	}
	
	@Test
	public void deveLocalizarEObterOutroFuncionarioPeloNome() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		java.util.List<String> nomesFunc = java.util.List.of("Fabio", "Bruno");
		empresa.adicionarFuncionario(nomesFunc.get(0));
		empresa.adicionarFuncionario(nomesFunc.get(1));
		
		assertEquals(nomesFunc.get(1), empresa.obterFuncionarioPeloNome(nomesFunc.get(1)).obterNome());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void aBuscaAoFuncionarioPeloNomeNaoPodeSerComNomeInexistente() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		assertEquals(0, empresa.obterFuncionarios().size());
		
		java.util.List<String> nomesFunc = java.util.List.of("Fabio", "Bruno");
		empresa.adicionarFuncionario(nomesFunc.get(0));
		empresa.adicionarFuncionario(nomesFunc.get(1));
		
		empresa.obterFuncionarioPeloNome("Jos�");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void aBuscaAoFuncionarioPeloNomeNaoPodeSerComNomeVazio() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		assertEquals(0, empresa.obterFuncionarios().size());
		
		java.util.List<String> nomesFunc = java.util.List.of("Fabio", "Bruno");
		empresa.adicionarFuncionario(nomesFunc.get(0));
		empresa.adicionarFuncionario(nomesFunc.get(1));
		
		empresa.obterFuncionarioPeloNome("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void aBuscaAoFuncionarioPeloNomeNaoPodeSerComNomeNulo() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		assertEquals(0, empresa.obterFuncionarios().size());
		
		java.util.List<String> nomesFunc = java.util.List.of("Fabio", "Bruno");
		empresa.adicionarFuncionario(nomesFunc.get(0));
		empresa.adicionarFuncionario(nomesFunc.get(1));
		
		empresa.obterFuncionarioPeloNome(null);
	}

	// OCORRENCIAS DO FUNCIONARIO
	@Test
	public void umFuncionarioTemZeroOcorrencias() {
		String nomeFuncionario = "Fabio";
		Funcionario funcionario = new Funcionario(nomeFuncionario);
		Integer contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		
		assertThat(0, is(contadorOcorrencias));
	}
	
	@Test
	public void umFuncionarioTemUmaOcorrencia() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		Funcionario funcionario = empresa.obterFuncionarioPeloNome(nomeFuncionario);

		Integer contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		
		assertThat(0, is(contadorOcorrencias));
		
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;

		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(1, is(contadorOcorrencias));
	}
	
	@Test
	public void umFuncionarioTemMultiplasOcorrencias() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		Funcionario funcionario = empresa.obterFuncionarioPeloNome(nomeFuncionario);

		Integer contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		
		assertThat(0, is(contadorOcorrencias));
		
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;

		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(1, is(contadorOcorrencias));
		
		resumo = "Falha no servi�o Y.";
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);

		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(2, is(contadorOcorrencias));
	}
	
	@Test
	public void umFuncionarioTemMultiplasOcorrenciasCorretamente() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		Funcionario funcionario = empresa.obterFuncionarioPeloNome(nomeFuncionario);

		Integer contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		
		assertThat(0, is(contadorOcorrencias));
		
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;

		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(1, is(contadorOcorrencias));
		
		resumo = "Falha no servi�o Y.";
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);

		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(2, is(contadorOcorrencias));
		
		String resumoOcorrenciaUm = projeto.obterOcorrenciaPeloIdentificador("#XY-1").obterResumo();
		String resumoOcorrenciaDois = projeto.obterOcorrenciaPeloIdentificador("#XY-2").obterResumo();
		Funcionario funcRespOcorrenciaUm = projeto.obterOcorrenciaPeloIdentificador("#XY-1").obterFuncionarioResponsavel();
		Funcionario funcRespOcorrenciaDois = projeto.obterOcorrenciaPeloIdentificador("#XY-2").obterFuncionarioResponsavel();
		
		assertEquals("Falha no servi�o X.", resumoOcorrenciaUm);
		assertEquals("Falha no servi�o Y.", resumoOcorrenciaDois);
		assertEquals(funcionario, funcRespOcorrenciaUm);
		assertEquals(funcionario, funcRespOcorrenciaDois); 
	}
		
	@Test
	public void umFuncionarioPodeSerAlocadoEmOcorrenciasDeProjetosDiferentes() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		String nomeProjetoUm = "XY";
		String nomeProjetoDois = "YZ";

		empresa.adicionarProjeto(nomeProjetoUm);
		empresa.adicionarProjeto(nomeProjetoDois);
		
		Projeto projetoUm = empresa.obterProjetoPeloNome(nomeProjetoUm);
		Projeto projetoDois = empresa.obterProjetoPeloNome(nomeProjetoDois);
		
		projetoUm.adicionarFuncionario(nomeFuncionario);
		projetoDois.adicionarFuncionario(nomeFuncionario);

		Funcionario funcionario = empresa.obterFuncionarioPeloNome(nomeFuncionario);

		Integer contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		
		assertThat(0, is(contadorOcorrencias));
		
		String resumoUm = "Falha no servi�o X do projeto Um.";
		String resumoDois = "Falha no servi�o Y do projeto Dois.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;

		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(1, is(contadorOcorrencias));
		
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);

		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(2, is(contadorOcorrencias));
		
		String resumoOcorrenciaUmProjetoUm = projetoUm.obterOcorrenciaPeloIdentificador("#XY-1").obterResumo();
		String resumoOcorrenciaUmProjetoDois = projetoDois.obterOcorrenciaPeloIdentificador("#YZ-1").obterResumo();
		Funcionario funcRespOcorrenciaUmProjetoUm = projetoUm.obterOcorrenciaPeloIdentificador("#XY-1").obterFuncionarioResponsavel();
		Funcionario funcRespOcorrenciaUmProjetoDois = projetoDois.obterOcorrenciaPeloIdentificador("#YZ-1").obterFuncionarioResponsavel();
		
		assertEquals(resumoUm, resumoOcorrenciaUmProjetoUm);
		assertEquals(resumoDois, resumoOcorrenciaUmProjetoDois);
		assertEquals(funcionario, funcRespOcorrenciaUmProjetoUm);
		assertEquals(funcionario, funcRespOcorrenciaUmProjetoDois); 
	}
	
	@Test
	public void umFuncionarioPodeSerAlocadoEmAteDezOcorrenciasAbertas() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		String nomeProjetoUm = "XY";
		String nomeProjetoDois = "YZ";

		empresa.adicionarProjeto(nomeProjetoUm);
		empresa.adicionarProjeto(nomeProjetoDois);
		
		Projeto projetoUm = empresa.obterProjetoPeloNome(nomeProjetoUm);
		Projeto projetoDois = empresa.obterProjetoPeloNome(nomeProjetoDois);
		
		projetoUm.adicionarFuncionario(nomeFuncionario);
		projetoDois.adicionarFuncionario(nomeFuncionario);

		Funcionario funcionario = empresa.obterFuncionarioPeloNome(nomeFuncionario);

		String resumoUm = "Falha no servi�o X do projeto Um.";
		String resumoDois = "Falha no servi�o Y do projeto Dois.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;

		Integer contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(0, is(contadorOcorrencias));

		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
	
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(10, is(contadorOcorrencias));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void umFuncionarioNaoDeveSerAlocadoEmMaisDeDEZOcorrenciasAbertas() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		String nomeProjetoUm = "XY";
		String nomeProjetoDois = "YZ";

		empresa.adicionarProjeto(nomeProjetoUm);
		empresa.adicionarProjeto(nomeProjetoDois);
		
		Projeto projetoUm = empresa.obterProjetoPeloNome(nomeProjetoUm);
		Projeto projetoDois = empresa.obterProjetoPeloNome(nomeProjetoDois);
		
		projetoUm.adicionarFuncionario(nomeFuncionario);
		projetoDois.adicionarFuncionario(nomeFuncionario);

		Funcionario funcionario = empresa.obterFuncionarioPeloNome(nomeFuncionario);

		String resumoUm = "Falha no servi�o X do projeto Um.";
		String resumoDois = "Falha no servi�o Y do projeto Dois.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;

		Integer contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(0, is(contadorOcorrencias));

		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
	
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(10, is(contadorOcorrencias));
		
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
	}

	@Test
	public void umFuncionarioDeveReduzirSuaContagemDeOcorrenciasAbertasAoCompletarOcorrencia() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		String nomeProjetoUm = "XY";
		String nomeProjetoDois = "YZ";

		empresa.adicionarProjeto(nomeProjetoUm);
		empresa.adicionarProjeto(nomeProjetoDois);
		
		Projeto projetoUm = empresa.obterProjetoPeloNome(nomeProjetoUm);
		Projeto projetoDois = empresa.obterProjetoPeloNome(nomeProjetoDois);
		
		projetoUm.adicionarFuncionario(nomeFuncionario);
		projetoDois.adicionarFuncionario(nomeFuncionario);

		Funcionario funcionario = empresa.obterFuncionarioPeloNome(nomeFuncionario);

		String resumoUm = "Falha no servi�o X do projeto Um.";
		String resumoDois = "Falha no servi�o Y do projeto Dois.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;

		Integer contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(0, is(contadorOcorrencias));

		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
	
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(4, is(contadorOcorrencias));
		
		Ocorrencia ocorrenciaParaCompletar = projetoUm.obterOcorrenciaPeloIdentificador("#XY-2");
		ocorrenciaParaCompletar.completarOcorrencia();
		
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(3, is(contadorOcorrencias));
	}
}
