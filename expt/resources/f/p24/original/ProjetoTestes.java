package testes;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.junit.Test;
import domain.Empresa;
import domain.Funcionario;
import domain.Ocorrencia;
import domain.Projeto;
import domain.enums.TIPO;
import domain.enums.PRIORIDADE;

public class ProjetoTestes {
	// PROJETO
	@Test
	public void umProjetoCriadoDeveTerUmNome() {
		String nomeProjeto = "XY";
		Projeto projeto = new Projeto(nomeProjeto);
		
		assertEquals(nomeProjeto, projeto.obterNome());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void umProjetoNaoPodeTerUmNomeNulo() {
		String nomeProjeto = null;
		new Projeto(nomeProjeto);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void umProjetoNaoPodeTerUmNomeVazio() {
		String nomeProjeto = "";
		new Projeto(nomeProjeto);
	}

	@Test
	public void umProjetoCriadoIniciaComUltimoIDValorZero() {
		String nomeProjeto = "XY";
		Projeto projeto = new Projeto(nomeProjeto);
	
		assertThat(0, is(projeto.obterUltimoID()));
	}
	
	@Test
	public void deveLocalizarEObterProjetoPeloNome() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);

		java.util.List<String> nomesFunc = java.util.List.of("XY", "YZ");
		empresa.adicionarFuncionario(nomesFunc.get(0));
		empresa.adicionarFuncionario(nomesFunc.get(1));
		
		assertEquals(nomesFunc.get(0), empresa.obterFuncionarioPeloNome(nomesFunc.get(0)).obterNome());
	}
	
	@Test
	public void deveLocalizarEObterOutroProjetoPeloNome() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		java.util.List<String> nomesFunc = java.util.List.of("XY", "YZ");
		empresa.adicionarFuncionario(nomesFunc.get(0));
		empresa.adicionarFuncionario(nomesFunc.get(1));
		
		assertEquals(nomesFunc.get(1), empresa.obterFuncionarioPeloNome(nomesFunc.get(1)).obterNome());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void aBuscaAoProjetoPeloNomeNaoPodeSerComNomeDeProjetoInexistente() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);

		String nomeProjeto = "XY";
		
		assertEquals(0, empresa.obterProjetos().size());
		empresa.adicionarProjeto(nomeProjeto);
		assertEquals(1, empresa.obterProjetos().size());

		empresa.obterProjetoPeloNome("ObjetivoZ").obterNome();
	}

	@Test(expected=IllegalArgumentException.class)
	public void aBuscaAoProjetoPeloNomeNaoPodeSerComNomeVazio() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);

		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);
		
		assertEquals(1, empresa.obterProjetos().size());

		empresa.obterProjetoPeloNome("");
	}

	@Test(expected=IllegalArgumentException.class)
	public void aBuscaAoProjetoPeloNomeNaoPodeSerComNomeNulo() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);

		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);
		
		assertEquals(1, empresa.obterProjetos().size());

		empresa.obterProjetoPeloNome(null);
	}	
	
	// OCORRENCIAS DO PROJETO
	@Test
	public void umProjetoTemZeroOcorrencias() {
		String nomeProjeto = "XY";
		Projeto projeto = new Projeto(nomeProjeto);
	
		assertEquals(0, projeto.obterOcorrencias().size());
	}
	
	@Test
	public void umProjetoPodeCriarSuaPrimeiraOcorrencia() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);
		
		String resumo = "Testando a ocorrencia";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		
		assertEquals(0, projeto.obterOcorrencias().size());
		
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		
		assertEquals(1, projeto.obterOcorrencias().size());
	}
	
	@Test
	public void umProjetoPodeCriarMultiplasOcorrencias() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);
		
		String resumo = "Testando a ocorrencia";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		
		assertEquals(0, projeto.obterOcorrencias().size());
		
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		
		assertEquals(1, projeto.obterOcorrencias().size());
		
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		
		assertEquals(2, projeto.obterOcorrencias().size());
	}
	
	@Test
	public void asMultiplasOcorrenciasDevemSerCriadasCorretamente() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);
		
		String resumo = "Testando a ocorrencia";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		
		assertEquals(0, projeto.obterOcorrencias().size());
		
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		// ID da ocorrencia esperada: "#XY-1"
		
		assertEquals(1, projeto.obterOcorrencias().size());
		
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		// ID da ocorrencia esperada: "#XY-2"
		
		assertEquals(2, projeto.obterOcorrencias().size());
		
		java.util.List<String> idsOcorrencias = java.util.List.of("#XY-1", "#XY-2");
		
		Iterator<Ocorrencia> itera = projeto.obterOcorrencias().iterator();
	
		assertEquals(idsOcorrencias.get(0), itera.next().obterIdentificador());
		assertEquals(idsOcorrencias.get(1), itera.next().obterIdentificador());
	}

	@Test
	public void asOcorrenciasDevemTerIdentificadoresUnicos() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);
		
		String resumo = "Testando a ocorrencia";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		
		assertEquals(0, projeto.obterOcorrencias().size());
		
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		
		assertEquals(1, projeto.obterOcorrencias().size());
		
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		
		assertEquals(2, projeto.obterOcorrencias().size());
		
		// https://stackoverflow.com/questions/20870879/why-set-is-not-allowed-duplicate-value-which-kind-of-mechanism-used-behind-them
		// Set naturalmente n�o aceita duplicados.
		Set<Ocorrencia> setOcorrencias = new HashSet<Ocorrencia>(projeto.obterOcorrencias());
		
		assertEquals(projeto.obterOcorrencias().size(), setOcorrencias.size());
	}

	@Test
	public void deveLocalizarEObterOcorrenciaPeloIdentificador() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);

		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		
		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
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
		
		assertEquals(resumo , projeto.obterOcorrenciaPeloIdentificador("#XY-1").obterResumo());
	}

	@Test
	public void deveLocalizarEObterOutraOcorrenciaPeloIdentificador() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);

		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		
		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		projeto.adicionarFuncionario(nomeFuncionario);
		Funcionario funcionario = empresa.obterFuncionarioPeloNome(nomeFuncionario);
		Integer contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		
		assertThat(0, is(contadorOcorrencias));
		
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);

		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(2, is(contadorOcorrencias));
		
		assertEquals(resumo , projeto.obterOcorrenciaPeloIdentificador("#XY-2").obterResumo());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void aBuscaDeUmaOcorrenciaPeloIdentificadorNaoPodeSerComIdentificadorInexistente() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);

		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		
		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
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
		
		projeto.obterOcorrenciaPeloIdentificador("#ABC-142");
	}

	@Test(expected=IllegalArgumentException.class)
	public void aBuscaDaOcorrenciaPeloIdentificadorNaoPodeSerComIdentificadorVazio() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);

		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		
		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
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
		
		projeto.obterOcorrenciaPeloIdentificador("");
	}

	@Test(expected=IllegalArgumentException.class)
	public void aBuscaDaOcorrenciaPeloIdentificadorNaoPodeSerComIdentificadorNulo() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);

		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		
		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
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
		
		projeto.obterOcorrenciaPeloIdentificador(null);
	}
	
	// FUNCIONARIOS DO PROJETO
	@Test
	public void umProjetoCriadoDeveTerUmaListaDeFuncionariosVazia() {
		String nomeProjeto = "XY";
		Projeto projeto = new Projeto(nomeProjeto);
	
		assertEquals(0, projeto.obterFuncionarios().size());
	}
	
	@Test
	public void adicionarUmFuncionarioDaEmpresaAoProjeto() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		Projeto projeto = new Projeto(nomeProjeto);
		
		String nomeFuncionario = "Fabio";
		assertEquals(0, empresa.obterFuncionarios().size());
		
		empresa.adicionarFuncionario(nomeFuncionario);
		
		assertEquals(1, empresa.obterFuncionarios().size());

		assertEquals(0, projeto.obterFuncionarios().size());

		projeto.adicionarFuncionario(nomeFuncionario);
		
		assertEquals(1, projeto.obterFuncionarios().size());
		assertEquals(empresa.obterFuncionarioPeloNome(nomeFuncionario), projeto.obterFuncionarioPeloNome(nomeFuncionario));
	}

	@Test
	public void adicionarUmFuncionarioDaEmpresaEmMultiplosProjetos() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		java.util.List<String> nomesProj = java.util.List.of("XY", "YZ");
		Projeto projetoUm = new Projeto(nomesProj.get(0));
		Projeto projetoDois = new Projeto(nomesProj.get(1));
		
		String nomeFuncionario = "Fabio";
		assertEquals(0, empresa.obterFuncionarios().size());
		
		empresa.adicionarFuncionario(nomeFuncionario);
		
		assertEquals(1, empresa.obterFuncionarios().size());

		assertEquals(0, projetoUm.obterFuncionarios().size());
		assertEquals(0, projetoDois.obterFuncionarios().size());

		projetoUm.adicionarFuncionario(nomeFuncionario);
		projetoDois.adicionarFuncionario(nomeFuncionario);
		
		assertEquals(1, projetoUm.obterFuncionarios().size());
		assertEquals(empresa.obterFuncionarioPeloNome(nomeFuncionario), projetoUm.obterFuncionarioPeloNome(nomeFuncionario));
		assertEquals(1, projetoDois.obterFuncionarios().size());
		assertEquals(empresa.obterFuncionarioPeloNome(nomeFuncionario), projetoDois.obterFuncionarioPeloNome(nomeFuncionario));
	}
	
}
