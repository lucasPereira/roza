package testes;

import static org.junit.Assert.*;
import org.junit.Test;
import domain.Empresa;
import domain.Ocorrencia;
import domain.Projeto;
import domain.enums.*;

public class OcorrenciaTestes {

	@Test
	public void umaOcorrenciaCriadaDeveTerUmIdentificador() {	
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);
	
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		
		assertEquals("1" , primeiraOcorrencia.obterIdentificador());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void umaOcorrenciaNaoPodeTerUmIdentificadorNulo() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = null;
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
	}

	@Test(expected=IllegalArgumentException.class)
	public void umaOcorrenciaNaoPodeTerUmIdentificadorVazio() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
	}
	
	@Test
	public void umaOcorrenciaCriadaDeveTerUmResumo() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		
		assertEquals(resumo, primeiraOcorrencia.obterResumo());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void umaOcorrenciaNaoPodeTerUmResumoNulo() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "1";
		String resumo = null;
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
	}

	@Test(expected=IllegalArgumentException.class)
	public void umaOcorrenciaNaoPodeTerUmResumoVazio() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "1";
		String resumo = "";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
	}
	
	// ESTADO
	@Test
	public void umaOcorrenciaCriadaDeveIniciarComEstadoAberta() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		
		assertEquals(ESTADO.ABERTA, primeiraOcorrencia.obterEstado());
	}
	
	@Test
	public void umaOcorrenciaAbertaPodeSerCompletada() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);

		assertEquals(ESTADO.ABERTA, primeiraOcorrencia.obterEstado());

		primeiraOcorrencia.completarOcorrencia();
		
		assertEquals(ESTADO.COMPLETADA, primeiraOcorrencia.obterEstado());
	}

	@Test(expected=IllegalArgumentException.class)
	public void umaOcorrenciaCompletadaNaoDeveSerMaisAlterada() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);

		assertEquals(ESTADO.ABERTA, primeiraOcorrencia.obterEstado());
		primeiraOcorrencia.completarOcorrencia();
		assertEquals(ESTADO.COMPLETADA, primeiraOcorrencia.obterEstado());

		primeiraOcorrencia.completarOcorrencia();
	}

	// TIPO
	@Test(expected=IllegalArgumentException.class)
	public void umaOcorrenciaCriadaNaoDeveSerCriadaComTipoNulo() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = null;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
	}

	@Test
	public void umaOcorrenciaCriadaDeveIniciarComTipoBug() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		
		assertEquals(TIPO.BUG, primeiraOcorrencia.obterTipo());
	}

	@Test
	public void umaOcorrenciaCriadaDeveIniciarComTipoMelhoria() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.MELHORIA;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		
		assertEquals(TIPO.MELHORIA, primeiraOcorrencia.obterTipo());
	}

	@Test
	public void umaOcorrenciaCriadaDeveIniciarComTipoTarefa() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.TAREFA;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		
		assertEquals(TIPO.TAREFA, primeiraOcorrencia.obterTipo());
	}
	
	// PRIORIDADE
	@Test(expected=IllegalArgumentException.class)
	public void umaOcorrenciaNaoDeveSerCriadaComPrioridadeNula() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = null;
		new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
	}
	
	@Test
	public void umaOcorrenciaDeveSerCriadaComPrioridadeBaixa() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.BAIXA;
		
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		
		assertEquals(PRIORIDADE.BAIXA, primeiraOcorrencia.obterPrioridade());
	}

	@Test
	public void umaOcorrenciaDeveSerCriadaComPrioridadeMedia() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.MEDIA;
		
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		
		assertEquals(PRIORIDADE.MEDIA, primeiraOcorrencia.obterPrioridade());
	}

	@Test
	public void umaOcorrenciaDeveSerCriadaComPrioridadeAlta() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		
		assertEquals(PRIORIDADE.ALTA, primeiraOcorrencia.obterPrioridade());
	}

	@Test(expected=IllegalArgumentException.class)
	public void umaOcorrenciaNaoPodeReceberAlteracaoDeUmaPrioridadeNulo() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;

		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);

		assertEquals(PRIORIDADE.ALTA, primeiraOcorrencia.obterPrioridade());

		primeiraOcorrencia.alterarPrioridade(null);
	}
	
	@Test
	public void podeSerAlteradaAPrioridadeDeUmaOcorrenciaParaBaixa() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);

		assertEquals(PRIORIDADE.ALTA, primeiraOcorrencia.obterPrioridade());

		primeiraOcorrencia.alterarPrioridade(PRIORIDADE.BAIXA);
		
		assertEquals(PRIORIDADE.BAIXA, primeiraOcorrencia.obterPrioridade());
	}
	
	@Test
	public void podeSerAlteradaAPrioridadeDeUmaOcorrenciaParaMedia() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);

		assertEquals(PRIORIDADE.ALTA, primeiraOcorrencia.obterPrioridade());

		primeiraOcorrencia.alterarPrioridade(PRIORIDADE.MEDIA);
		
		assertEquals(PRIORIDADE.MEDIA, primeiraOcorrencia.obterPrioridade());
	}
	
	@Test
	public void podeSerAlteradaAPrioridadeDeUmaOcorrenciaParaAlta() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.MEDIA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);

		assertEquals(PRIORIDADE.MEDIA, primeiraOcorrencia.obterPrioridade());

		primeiraOcorrencia.alterarPrioridade(PRIORIDADE.ALTA);
		
		assertEquals(PRIORIDADE.ALTA, primeiraOcorrencia.obterPrioridade());
	}
	
	@Test
	public void podeSerAlteradaAPrioridadeDeUmaOcorrenciaAberta() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);
		
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);

		assertEquals(ESTADO.ABERTA, primeiraOcorrencia.obterEstado());
		assertEquals(PRIORIDADE.ALTA, primeiraOcorrencia.obterPrioridade());

		primeiraOcorrencia.alterarPrioridade(PRIORIDADE.MEDIA);
		
		assertEquals(ESTADO.ABERTA, primeiraOcorrencia.obterEstado());
		assertEquals(PRIORIDADE.MEDIA, primeiraOcorrencia.obterPrioridade());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void naoPodeSerAlteradaAPrioridadeDeUmaOcorrenciaCompletada() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);

		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);

		assertEquals(ESTADO.ABERTA, primeiraOcorrencia.obterEstado());
		primeiraOcorrencia.completarOcorrencia();
		assertEquals(ESTADO.COMPLETADA, primeiraOcorrencia.obterEstado());

		primeiraOcorrencia.alterarPrioridade(PRIORIDADE.MEDIA);
	}

	// FUNCIONARIO RESPONSAVEL
	@Test
	public void umaOcorrenciaDeveAdicionarOFuncionarioResponsavel() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);
		
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);

		assertEquals(nomeFuncionario, primeiraOcorrencia.obterFuncionarioResponsavel().obterNome());
	}
	
	@Test
	public void PodeSerAlteradoOFuncionarioResponsavelDeUmaOcorrenciaAberta() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		java.util.List<String> nomesFuncs = java.util.List.of("Fabio", "Bruno");
		empresa.adicionarFuncionario(nomesFuncs.get(0));
		empresa.adicionarFuncionario(nomesFuncs.get(1));
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomesFuncs.get(0));
		projeto.adicionarFuncionario(nomesFuncs.get(1));
		
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;

		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomesFuncs.get(0));
		assertEquals(nomesFuncs.get(0), primeiraOcorrencia.obterFuncionarioResponsavel().obterNome());

		primeiraOcorrencia.alterarFuncionarioResponsavel(nomesFuncs.get(1));
		assertEquals(empresa.obterFuncionarioPeloNome(nomesFuncs.get(1)), primeiraOcorrencia.obterFuncionarioResponsavel());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void umaOcorrenciaNaoPodeReceberAlteracaoComUmFuncionarioResponsavelInexistente() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		java.util.List<String> nomesFuncs = java.util.List.of("Fabio", "Bruno");
		empresa.adicionarFuncionario(nomesFuncs.get(0));
		empresa.adicionarFuncionario(nomesFuncs.get(1));
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomesFuncs.get(0));
		projeto.adicionarFuncionario(nomesFuncs.get(1));
		
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;

		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomesFuncs.get(0));
		assertEquals(nomesFuncs.get(0), primeiraOcorrencia.obterFuncionarioResponsavel().obterNome());

		primeiraOcorrencia.alterarFuncionarioResponsavel("Jos�");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void umaOcorrenciaNaoPodeReceberAlteracaoDeUmFuncionarioResponsavelNulo() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		java.util.List<String> nomesFuncs = java.util.List.of("Fabio", "Bruno");
		empresa.adicionarFuncionario(nomesFuncs.get(0));
		empresa.adicionarFuncionario(nomesFuncs.get(1));
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomesFuncs.get(0));
		projeto.adicionarFuncionario(nomesFuncs.get(1));
		
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;

		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomesFuncs.get(0));
		assertEquals(nomesFuncs.get(0), primeiraOcorrencia.obterFuncionarioResponsavel().obterNome());

		primeiraOcorrencia.alterarFuncionarioResponsavel(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void umaOcorrenciaNaoPodeReceberAlteracaoDeUmFuncionarioResponsavelVazio() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		java.util.List<String> nomesFuncs = java.util.List.of("Fabio", "Bruno");
		empresa.adicionarFuncionario(nomesFuncs.get(0));
		empresa.adicionarFuncionario(nomesFuncs.get(1));
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomesFuncs.get(0));
		projeto.adicionarFuncionario(nomesFuncs.get(1));
		
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;

		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomesFuncs.get(0));
		assertEquals(nomesFuncs.get(0), primeiraOcorrencia.obterFuncionarioResponsavel().obterNome());

		primeiraOcorrencia.alterarFuncionarioResponsavel("");
	}

	@Test(expected=IllegalArgumentException.class)
	public void naoPodeSerAlteradoOFuncionarioResponsavelDeUmaOcorrenciaCompletada() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		
		String nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);

		java.util.List<String> nomesFuncs = java.util.List.of("Fabio", "Bruno");
		empresa.adicionarFuncionario(nomesFuncs.get(0));
		empresa.adicionarFuncionario(nomesFuncs.get(1));
		
		Projeto projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomesFuncs.get(0));
		projeto.adicionarFuncionario(nomesFuncs.get(1));
		
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;

		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomesFuncs.get(0));
		assertEquals(nomesFuncs.get(0), primeiraOcorrencia.obterFuncionarioResponsavel().obterNome());

		assertEquals(ESTADO.ABERTA, primeiraOcorrencia.obterEstado());
		primeiraOcorrencia.completarOcorrencia();
		assertEquals(ESTADO.COMPLETADA, primeiraOcorrencia.obterEstado());

		primeiraOcorrencia.alterarFuncionarioResponsavel(nomesFuncs.get(1));
	}
}
