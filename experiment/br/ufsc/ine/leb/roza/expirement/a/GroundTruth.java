package br.ufsc.ine.leb.roza.expirement.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TextFile;
import br.ufsc.ine.leb.roza.extraction.JunitTestCaseExtractor;
import br.ufsc.ine.leb.roza.extraction.TestCaseExtractor;
import br.ufsc.ine.leb.roza.loading.RecursiveTextFileLoader;
import br.ufsc.ine.leb.roza.loading.TextFileLoader;
import br.ufsc.ine.leb.roza.measurement.matrix.Matrix;
import br.ufsc.ine.leb.roza.measurement.matrix.MatrixElementToKeyConverter;
import br.ufsc.ine.leb.roza.measurement.matrix.MatrixIntegerValueFactory;
import br.ufsc.ine.leb.roza.measurement.matrix.MatrixPair;
import br.ufsc.ine.leb.roza.measurement.matrix.MatrixTestCaseToStringConverter;
import br.ufsc.ine.leb.roza.measurement.matrix.MatrixValueFactory;
import br.ufsc.ine.leb.roza.parsing.Junit5TestClassParser;
import br.ufsc.ine.leb.roza.parsing.TestClassParser;

public class GroundTruth {

	private Matrix<TestCase, String, Integer> matrix;
	private List<TestCase> testCases;

	public GroundTruth() {
		TextFileLoader loader = new RecursiveTextFileLoader("experiment-resources/a");
		TestClassParser parser = new Junit5TestClassParser();
		List<String> assercoes = Arrays.asList("assegurarTexto", "assegurarValor", "assegurarQuantidadeDeElementos", "assegurarConteudoDeArquivoBaixado", "assegurarNaoMarcado", "assegurarMarcado", "assegurarMarcacao");
		TestCaseExtractor extractor = new JunitTestCaseExtractor(assercoes);
		List<TextFile> files = loader.load();
		List<TestClass> classes = parser.parse(files);
		testCases = extractor.extract(classes);
		MatrixElementToKeyConverter<TestCase, String> converter = new MatrixTestCaseToStringConverter();
		MatrixValueFactory<TestCase, Integer> factory = new MatrixIntegerValueFactory();
		matrix = new Matrix<>(testCases, converter, factory);

		all(matrix);
		productionImport(matrix);
		reviewerImport(matrix);
		evaluationForm(matrix);
		reviewerDistribution(matrix);
		assessment(matrix);
		plugin(matrix);
	}

	private void all(Matrix<TestCase, String, Integer> matrix) {
		List<String> all = new LinkedList<String>();
		all.add("importarProducoesPagina");
		all.add("importarProducoesComSucesso");
		all.add("importarProducoesDuasVezesComSucesso");
		all.add("importarProducoesSemArquivo");
		all.add("importarProducoesArquivoVazio");
		all.add("importarProducoesArquivoSemCabecalho");
		all.add("importarProducoesArquivoSemIdentificador");
		all.add("importarProducoesVerSubmissoesDeImportacaoComSucesso");
		all.add("importarProducoesVerSubmissoesDeDuasImportacoesDiferentesComSucesso");
		all.add("importarAvaliadoresCoordenadorPagina");
		all.add("importarAvaliadoresCoordenadorComSucesso");
		all.add("importarAvaliadoresCoordenadorDuasVezesComSucesso");
		all.add("importarAvaliadoresCoordenadorSemArquivo");
		all.add("importarAvaliadoresCoordenadorArquivoVazio");
		all.add("importarAvaliadoresCoordenadorArquivoSemCabecalho");
		all.add("importarAvaliadoresCoordenadorArquivoSemDocumento");
		all.add("importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao");
		all.add("importarAvaliadoresCoordenadorVerCoordenadorDeImportacao");
		all.add("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados");
		all.add("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados");
		all.add("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados");
		all.add("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados");
		all.add("importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados");
		all.add("importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados");
		all.add("cadastrarFichaDeAvaliacaoPagina");
		all.add("cadastrarFichaDeAvaliacaoComSucesso");
		all.add("cadastrarFichaDeAvaliacaoSomatorioDePontosAcimaDe100");
		all.add("cadastrarFichaDeAvaliacaoDuasVezesComSucesso");
		all.add("cadastrarFichaDeAvaliacaoVerFichaDeAvaliacao");
		all.add("cadastrarFichaDeAvaliacaoVerFormulario");
		all.add("cadastrarFichaDeAvaliacaoVerFichaDeAvaliacaoRecadastrada");
		all.add("distribuirAvaliadoresPagina");
		all.add("distribuirAvaliadoresComSucesso");
		all.add("distribuirAvaliadoresSemPreencherPrazoDeAvaliacao");
		all.add("distribuirAvaliadoresSemCoordenadorDeArea");
		all.add("distribuirAvaliadoresComMaisDeUmCoordenadorDeArea");
		all.add("distribuirAvaliadoresSemAvaliadores");
		all.add("distribuirAvaliadoresSemFichaDeAvaliacao");
		all.add("distribuirAvaliadoresVerPrimeiraProducao");
		all.add("distribuirAvaliadoresVerSegundaProducao");
		all.add("distribuirAvaliadoresVerPrimeiraProducaoAtualizada");
		all.add("distribuirAvaliadoresVerSegundaProducaoAtualizada");
		all.add("avaliarAvaliador");
		all.add("avaliarCoordenadorDeArea");
		all.add("pluginsPaginaGeral");
		all.add("pluginsPaginaDoPlugin");
		populate(matrix, all, 3);
	}

	private void productionImport(Matrix<TestCase, String, Integer> matrix) {
		List<String> productionImport = new LinkedList<String>();
		productionImport.add("importarProducoesPagina");
		productionImport.add("importarProducoesComSucesso");
		productionImport.add("importarProducoesDuasVezesComSucesso");
		productionImport.add("importarProducoesSemArquivo");
		productionImport.add("importarProducoesArquivoVazio");
		productionImport.add("importarProducoesArquivoSemCabecalho");
		productionImport.add("importarProducoesArquivoSemIdentificador");
		productionImport.add("importarProducoesVerSubmissoesDeImportacaoComSucesso");
		productionImport.add("importarProducoesVerSubmissoesDeDuasImportacoesDiferentesComSucesso");
		populate(matrix, productionImport, 19);
		matrix.set("importarProducoesPagina", "importarProducoesPagina", 21);
		matrix.set("importarProducoesComSucesso", "importarProducoesComSucesso", 25);
		matrix.set("importarProducoesComSucesso", "importarProducoesDuasVezesComSucesso", 22);
		matrix.set("importarProducoesComSucesso", "importarProducoesVerSubmissoesDeImportacaoComSucesso", 22);
		matrix.set("importarProducoesComSucesso", "importarProducoesVerSubmissoesDeDuasImportacoesDiferentesComSucesso", 22);
		matrix.set("importarProducoesDuasVezesComSucesso", "importarProducoesDuasVezesComSucesso", 30);
		matrix.set("importarProducoesDuasVezesComSucesso", "importarProducoesComSucesso", 22);
		matrix.set("importarProducoesDuasVezesComSucesso", "importarProducoesVerSubmissoesDeImportacaoComSucesso", 23);
		matrix.set("importarProducoesDuasVezesComSucesso", "importarProducoesVerSubmissoesDeDuasImportacoesDiferentesComSucesso", 27);
		matrix.set("importarProducoesSemArquivo", "importarProducoesSemArquivo", 20);
		matrix.set("importarProducoesArquivoVazio", "importarProducoesArquivoVazio", 22);
		matrix.set("importarProducoesArquivoSemCabecalho", "importarProducoesArquivoSemCabecalho", 22);
		matrix.set("importarProducoesArquivoSemIdentificador", "importarProducoesArquivoSemIdentificador", 22);
		matrix.set("importarProducoesVerSubmissoesDeImportacaoComSucesso", "importarProducoesVerSubmissoesDeImportacaoComSucesso", 25);
		matrix.set("importarProducoesVerSubmissoesDeImportacaoComSucesso", "importarProducoesComSucesso", 22);
		matrix.set("importarProducoesVerSubmissoesDeImportacaoComSucesso", "importarProducoesDuasVezesComSucesso", 23);
		matrix.set("importarProducoesVerSubmissoesDeImportacaoComSucesso", "importarProducoesVerSubmissoesDeDuasImportacoesDiferentesComSucesso", 23);
		matrix.set("importarProducoesVerSubmissoesDeDuasImportacoesDiferentesComSucesso", "importarProducoesVerSubmissoesDeDuasImportacoesDiferentesComSucesso", 30);
		matrix.set("importarProducoesVerSubmissoesDeDuasImportacoesDiferentesComSucesso", "importarProducoesComSucesso", 22);
		matrix.set("importarProducoesVerSubmissoesDeDuasImportacoesDiferentesComSucesso", "importarProducoesDuasVezesComSucesso", 27);
		matrix.set("importarProducoesVerSubmissoesDeDuasImportacoesDiferentesComSucesso", "importarProducoesVerSubmissoesDeImportacaoComSucesso", 23);
	}

	private void reviewerImport(Matrix<TestCase, String, Integer> matrix) {
		List<String> reviewerImport = new LinkedList<String>();
		reviewerImport.add("importarAvaliadoresCoordenadorPagina");
		reviewerImport.add("importarAvaliadoresCoordenadorComSucesso");
		reviewerImport.add("importarAvaliadoresCoordenadorDuasVezesComSucesso");
		reviewerImport.add("importarAvaliadoresCoordenadorSemArquivo");
		reviewerImport.add("importarAvaliadoresCoordenadorArquivoVazio");
		reviewerImport.add("importarAvaliadoresCoordenadorArquivoSemCabecalho");
		reviewerImport.add("importarAvaliadoresCoordenadorArquivoSemDocumento");
		reviewerImport.add("importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao");
		reviewerImport.add("importarAvaliadoresCoordenadorVerCoordenadorDeImportacao");
		reviewerImport.add("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados");
		reviewerImport.add("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados");
		reviewerImport.add("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados");
		reviewerImport.add("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados");
		reviewerImport.add("importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados");
		reviewerImport.add("importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados");
		populate(matrix, reviewerImport, 19);
		matrix.set("importarAvaliadoresCoordenadorPagina", "importarAvaliadoresCoordenadorPagina", 22);
		matrix.set("importarAvaliadoresCoordenadorComSucesso", "importarAvaliadoresCoordenadorComSucesso", 28);
		matrix.set("importarAvaliadoresCoordenadorDuasVezesComSucesso", "importarAvaliadoresCoordenadorDuasVezesComSucesso", 33);
		matrix.set("importarAvaliadoresCoordenadorDuasVezesComSucesso", "importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao", 23);
		matrix.set("importarAvaliadoresCoordenadorDuasVezesComSucesso", "importarAvaliadoresCoordenadorVerCoordenadorDeImportacao", 23);
		matrix.set("importarAvaliadoresCoordenadorDuasVezesComSucesso", "importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados", 27);
		matrix.set("importarAvaliadoresCoordenadorDuasVezesComSucesso", "importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados", 27);
		matrix.set("importarAvaliadoresCoordenadorDuasVezesComSucesso", "importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorDuasVezesComSucesso", "importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorDuasVezesComSucesso", "importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorDuasVezesComSucesso", "importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorSemArquivo", "importarAvaliadoresCoordenadorSemArquivo", 20);
		matrix.set("importarAvaliadoresCoordenadorArquivoVazio", "importarAvaliadoresCoordenadorArquivoVazio", 22);
		matrix.set("importarAvaliadoresCoordenadorArquivoSemCabecalho", "importarAvaliadoresCoordenadorArquivoSemCabecalho", 22);
		matrix.set("importarAvaliadoresCoordenadorArquivoSemDocumento", "importarAvaliadoresCoordenadorArquivoSemDocumento", 22);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao", "importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao", 28);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao", "importarAvaliadoresCoordenadorDuasVezesComSucesso", 23);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao", "importarAvaliadoresCoordenadorVerCoordenadorDeImportacao", 26);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao", "importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados", 23);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao", "importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados", 23);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao", "importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao", "importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao", "importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao", "importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeImportacao", "importarAvaliadoresCoordenadorVerCoordenadorDeImportacao", 28);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeImportacao", "importarAvaliadoresCoordenadorDuasVezesComSucesso", 23);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeImportacao", "importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao", 26);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeImportacao", "importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados", 23);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeImportacao", "importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados", 23);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeImportacao", "importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeImportacao", "importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeImportacao", "importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeImportacao", "importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados", 33);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorDuasVezesComSucesso", 27);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao", 23);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDeImportacao", 23);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados", 31);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados", 33);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorDuasVezesComSucesso", 27);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao", 23);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDeImportacao", 23);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados", 31);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 39);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorDuasVezesComSucesso", 25);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao", 23);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDeImportacao", 23);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 37);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 34);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 34);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 39);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorDuasVezesComSucesso", 25);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao", 23);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDeImportacao", 23);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 37);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 34);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 34);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 41);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorDuasVezesComSucesso", 25);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao", 23);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDeImportacao", 23);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 34);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 34);
		matrix.set("importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 39);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 41);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorDuasVezesComSucesso", 25);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao", 23);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDeImportacao", 23);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados", 25);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 34);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 34);
		matrix.set("importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 39);
	}

	private void evaluationForm(Matrix<TestCase, String, Integer> matrix) {
		List<String> evaluationForm = new LinkedList<String>();
		evaluationForm.add("cadastrarFichaDeAvaliacaoPagina");
		evaluationForm.add("cadastrarFichaDeAvaliacaoComSucesso");
		evaluationForm.add("cadastrarFichaDeAvaliacaoSomatorioDePontosAcimaDe100");
		evaluationForm.add("cadastrarFichaDeAvaliacaoDuasVezesComSucesso");
		evaluationForm.add("cadastrarFichaDeAvaliacaoVerFichaDeAvaliacao");
		evaluationForm.add("cadastrarFichaDeAvaliacaoVerFormulario");
		evaluationForm.add("cadastrarFichaDeAvaliacaoVerFichaDeAvaliacaoRecadastrada");
		populate(matrix, evaluationForm, 19);
		matrix.set("cadastrarFichaDeAvaliacaoPagina", "cadastrarFichaDeAvaliacaoPagina", 21);
		matrix.set("cadastrarFichaDeAvaliacaoComSucesso", "cadastrarFichaDeAvaliacaoComSucesso", 31);
		matrix.set("cadastrarFichaDeAvaliacaoComSucesso", "cadastrarFichaDeAvaliacaoSomatorioDePontosAcimaDe100", 20);
		matrix.set("cadastrarFichaDeAvaliacaoSomatorioDePontosAcimaDe100", "cadastrarFichaDeAvaliacaoSomatorioDePontosAcimaDe100", 23);
		matrix.set("cadastrarFichaDeAvaliacaoSomatorioDePontosAcimaDe100", "cadastrarFichaDeAvaliacaoComSucesso", 20);
		matrix.set("cadastrarFichaDeAvaliacaoDuasVezesComSucesso", "cadastrarFichaDeAvaliacaoDuasVezesComSucesso", 32);
		matrix.set("cadastrarFichaDeAvaliacaoDuasVezesComSucesso", "cadastrarFichaDeAvaliacaoVerFichaDeAvaliacao", 25);
		matrix.set("cadastrarFichaDeAvaliacaoDuasVezesComSucesso", "cadastrarFichaDeAvaliacaoVerFormulario", 25);
		matrix.set("cadastrarFichaDeAvaliacaoDuasVezesComSucesso", "cadastrarFichaDeAvaliacaoVerFichaDeAvaliacaoRecadastrada", 26);
		matrix.set("cadastrarFichaDeAvaliacaoVerFichaDeAvaliacao", "cadastrarFichaDeAvaliacaoVerFichaDeAvaliacao", 29);
		matrix.set("cadastrarFichaDeAvaliacaoVerFichaDeAvaliacao", "cadastrarFichaDeAvaliacaoDuasVezesComSucesso", 25);
		matrix.set("cadastrarFichaDeAvaliacaoVerFichaDeAvaliacao", "cadastrarFichaDeAvaliacaoVerFormulario", 29);
		matrix.set("cadastrarFichaDeAvaliacaoVerFichaDeAvaliacao", "cadastrarFichaDeAvaliacaoVerFichaDeAvaliacaoRecadastrada", 25);
		matrix.set("cadastrarFichaDeAvaliacaoVerFormulario", "cadastrarFichaDeAvaliacaoVerFormulario", 31);
		matrix.set("cadastrarFichaDeAvaliacaoVerFormulario", "cadastrarFichaDeAvaliacaoDuasVezesComSucesso", 25);
		matrix.set("cadastrarFichaDeAvaliacaoVerFormulario", "cadastrarFichaDeAvaliacaoVerFichaDeAvaliacao", 29);
		matrix.set("cadastrarFichaDeAvaliacaoVerFormulario", "cadastrarFichaDeAvaliacaoVerFichaDeAvaliacaoRecadastrada", 25);
		matrix.set("cadastrarFichaDeAvaliacaoVerFichaDeAvaliacaoRecadastrada", "cadastrarFichaDeAvaliacaoVerFichaDeAvaliacaoRecadastrada", 33);
		matrix.set("cadastrarFichaDeAvaliacaoVerFichaDeAvaliacaoRecadastrada", "cadastrarFichaDeAvaliacaoDuasVezesComSucesso", 26);
		matrix.set("cadastrarFichaDeAvaliacaoVerFichaDeAvaliacaoRecadastrada", "cadastrarFichaDeAvaliacaoVerFichaDeAvaliacao", 25);
		matrix.set("cadastrarFichaDeAvaliacaoVerFichaDeAvaliacaoRecadastrada", "cadastrarFichaDeAvaliacaoVerFormulario", 25);
	}

	private void reviewerDistribution(Matrix<TestCase, String, Integer> matrix) {
		List<String> reviewerDistribution = new LinkedList<String>();
		reviewerDistribution.add("distribuirAvaliadoresPagina");
		reviewerDistribution.add("distribuirAvaliadoresComSucesso");
		reviewerDistribution.add("distribuirAvaliadoresSemPreencherPrazoDeAvaliacao");
		reviewerDistribution.add("distribuirAvaliadoresSemCoordenadorDeArea");
		reviewerDistribution.add("distribuirAvaliadoresComMaisDeUmCoordenadorDeArea");
		reviewerDistribution.add("distribuirAvaliadoresSemAvaliadores");
		reviewerDistribution.add("distribuirAvaliadoresSemFichaDeAvaliacao");
		reviewerDistribution.add("distribuirAvaliadoresVerPrimeiraProducao");
		reviewerDistribution.add("distribuirAvaliadoresVerSegundaProducao");
		reviewerDistribution.add("distribuirAvaliadoresVerPrimeiraProducaoAtualizada");
		reviewerDistribution.add("distribuirAvaliadoresVerSegundaProducaoAtualizada");
		populate(matrix, reviewerDistribution, 18);
		matrix.set("distribuirAvaliadoresPagina", "distribuirAvaliadoresPagina", 21);
		matrix.set("distribuirAvaliadoresComSucesso", "distribuirAvaliadoresComSucesso", 41);
		matrix.set("distribuirAvaliadoresComSucesso", "distribuirAvaliadoresSemFichaDeAvaliacao", 24);
		matrix.set("distribuirAvaliadoresSemPreencherPrazoDeAvaliacao", "distribuirAvaliadoresSemPreencherPrazoDeAvaliacao", 21);
		matrix.set("distribuirAvaliadoresSemCoordenadorDeArea", "distribuirAvaliadoresSemCoordenadorDeArea", 28);
		matrix.set("distribuirAvaliadoresComMaisDeUmCoordenadorDeArea", "distribuirAvaliadoresComMaisDeUmCoordenadorDeArea", 28);
		matrix.set("distribuirAvaliadoresSemAvaliadores", "distribuirAvaliadoresSemAvaliadores", 28);
		matrix.set("distribuirAvaliadoresSemFichaDeAvaliacao", "distribuirAvaliadoresSemFichaDeAvaliacao", 28);
		matrix.set("distribuirAvaliadoresSemFichaDeAvaliacao", "distribuirAvaliadoresComSucesso", 24);
		matrix.set("distribuirAvaliadoresVerPrimeiraProducao", "distribuirAvaliadoresVerPrimeiraProducao", 48);
		matrix.set("distribuirAvaliadoresVerPrimeiraProducao", "distribuirAvaliadoresVerSegundaProducao", 47);
		matrix.set("distribuirAvaliadoresVerPrimeiraProducao", "distribuirAvaliadoresVerPrimeiraProducaoAtualizada", 45);
		matrix.set("distribuirAvaliadoresVerPrimeiraProducao", "distribuirAvaliadoresVerSegundaProducaoAtualizada", 45);
		matrix.set("distribuirAvaliadoresVerSegundaProducao", "distribuirAvaliadoresVerSegundaProducao", 48);
		matrix.set("distribuirAvaliadoresVerSegundaProducao", "distribuirAvaliadoresVerPrimeiraProducao", 47);
		matrix.set("distribuirAvaliadoresVerSegundaProducao", "distribuirAvaliadoresVerPrimeiraProducaoAtualizada", 45);
		matrix.set("distribuirAvaliadoresVerSegundaProducao", "distribuirAvaliadoresVerSegundaProducaoAtualizada", 45);
		matrix.set("distribuirAvaliadoresVerPrimeiraProducaoAtualizada", "distribuirAvaliadoresVerPrimeiraProducaoAtualizada", 61);
		matrix.set("distribuirAvaliadoresVerPrimeiraProducaoAtualizada", "distribuirAvaliadoresVerPrimeiraProducao", 45);
		matrix.set("distribuirAvaliadoresVerPrimeiraProducaoAtualizada", "distribuirAvaliadoresVerSegundaProducao", 45);
		matrix.set("distribuirAvaliadoresVerPrimeiraProducaoAtualizada", "distribuirAvaliadoresVerSegundaProducaoAtualizada", 60);
		matrix.set("distribuirAvaliadoresVerSegundaProducaoAtualizada", "distribuirAvaliadoresVerSegundaProducaoAtualizada", 61);
		matrix.set("distribuirAvaliadoresVerSegundaProducaoAtualizada", "distribuirAvaliadoresVerPrimeiraProducao", 45);
		matrix.set("distribuirAvaliadoresVerSegundaProducaoAtualizada", "distribuirAvaliadoresVerSegundaProducao", 45);
		matrix.set("distribuirAvaliadoresVerSegundaProducaoAtualizada", "distribuirAvaliadoresVerPrimeiraProducaoAtualizada", 60);
	}

	private void assessment(Matrix<TestCase, String, Integer> matrix) {
		List<String> assessment = new LinkedList<String>();
		assessment.add("avaliarAvaliador");
		assessment.add("avaliarCoordenadorDeArea");
		populate(matrix, assessment, 46);
		matrix.set("avaliarAvaliador", "avaliarAvaliador", 58);
		matrix.set("avaliarAvaliador", "avaliarCoordenadorDeArea", 49);
		matrix.set("avaliarCoordenadorDeArea", "avaliarCoordenadorDeArea", 58);
		matrix.set("avaliarCoordenadorDeArea", "avaliarAvaliador", 49);
	}

	private void plugin(Matrix<TestCase, String, Integer> matrix) {
		List<String> plugin = new LinkedList<String>();
		plugin.add("pluginsPaginaGeral");
		plugin.add("pluginsPaginaDoPlugin");
		populate(matrix, plugin, 17);
		matrix.set("pluginsPaginaGeral", "pluginsPaginaGeral", 17);
		matrix.set("pluginsPaginaDoPlugin", "pluginsPaginaDoPlugin", 18);
	}

	private void populate(Matrix<TestCase, String, Integer> matrix, List<String> elements, Integer reusedFixtures) {
		for (String source : elements) {
			for (String target : elements) {
				if (!source.equals(target)) {
					matrix.set(source, target, reusedFixtures);
				}
			}
		}
	}

	public List<TestCase> getRelevanteElements(List<TestCase> testCases, TestCase source) {
		List<TestCase> result = new ArrayList<>();
		for (TestCase target : testCases) {
			if (!source.equals(target) && matrix.get(source.getName(), target.getName()) > 3) {
				result.add(target);
			}
		}
		return result;
	}

	public void findInconsistences() {
		for (MatrixPair<TestCase, Integer> pair : matrix.getPairs()) {
			if (pair.getValue() == null) {
				logMissingPair(pair);
			}
		}
		Integer errors = 0;
		for (TestCase source : testCases) {
			List<Statement> sourceFixtures = source.getFixtures();
			for (TestCase target : testCases) {
				Integer commonFixtures = countCommonFixtures(sourceFixtures, target);
				Integer truth = matrix.get(source.getName(), target.getName());
				Boolean inconsistent = !truth.equals(commonFixtures);
				if (inconsistent) {
					errors++;
				}
				if (inconsistent && errors == 1) {
					logInconsistence(source, target, commonFixtures, truth);
				}
			}
		}
		logSummary(errors);
	}

	private Integer countCommonFixtures(List<Statement> sourceFixtures, TestCase target) {
		Boolean contiguous = true;
		List<Statement> targetFixtures = target.getFixtures();
		Integer commonFixtures = 0;
		for (Integer index = 0; contiguous && index < sourceFixtures.size() && index < targetFixtures.size(); index++) {
			Statement sourceFixture = sourceFixtures.get(index);
			Statement targetFixture = targetFixtures.get(index);
			contiguous = sourceFixture.equals(targetFixture);
			if (contiguous) {
				commonFixtures++;
			}
		}
		return commonFixtures;
	}

	private void logSummary(Integer errors) {
		System.out.println(String.format("Pairs: %d", matrix.getPairs().size()));
		System.out.println(String.format("Errors: %d", errors));
	}

	private void logInconsistence(TestCase source, TestCase target, Integer commonFixtures, Integer truth) {
		System.out.println(String.format("%s --> %s (%d:%d)", source.getName(), target.getName(), truth, commonFixtures));
		System.out.println(source.getFixtures());
		System.out.println(target.getFixtures());
	}

	private void logMissingPair(MatrixPair<TestCase, Integer> pair) {
		System.out.println(String.format("Missing: %s -> %s", pair.getSource().getName(), pair.getTarget().getName()));
	}

}
