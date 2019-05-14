package br.ufsc.ine.leb.roz.expirement;

import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TextFile;
import br.ufsc.ine.leb.roza.extractor.Junit4TestCaseExtractor;
import br.ufsc.ine.leb.roza.extractor.TestCaseExtractor;
import br.ufsc.ine.leb.roza.loader.RecursiveTextFileLoader;
import br.ufsc.ine.leb.roza.loader.TextFileLoader;
import br.ufsc.ine.leb.roza.measurer.matrix.Matrix;
import br.ufsc.ine.leb.roza.measurer.matrix.MatrixElementToKeyConverter;
import br.ufsc.ine.leb.roza.measurer.matrix.MatrixIntegerValueFactory;
import br.ufsc.ine.leb.roza.measurer.matrix.MatrixTestCaseToStringConverter;
import br.ufsc.ine.leb.roza.measurer.matrix.MatrixValueFactory;
import br.ufsc.ine.leb.roza.parser.Junit4TestClassParser;
import br.ufsc.ine.leb.roza.parser.TestClassParser;

public class GroundTruth {

	public GroundTruth() {
		TextFileLoader loader = new RecursiveTextFileLoader("tests");
		TestClassParser parser = new Junit4TestClassParser();
		TestCaseExtractor extractor = new Junit4TestCaseExtractor();
		List<TextFile> files = loader.load();
		List<TestClass> classes = parser.parse(files);
		List<TestCase> testCases = extractor.extract(classes);
		MatrixElementToKeyConverter<TestCase, String> converter = new MatrixTestCaseToStringConverter();
		MatrixValueFactory<TestCase, Integer> factory = new MatrixIntegerValueFactory();
		Matrix<TestCase, String, Integer> matrix = new Matrix<>(testCases, converter, factory);

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
		all.add("paginaImportacaoDeProducoes");
		all.add("importarProducoesComSucesso");
		all.add("importarProducoesDuasVezesComSucesso");
		all.add("importarProducoesSemArquivo");
		all.add("importarProducoesArquivoVazio");
		all.add("importarProducoesArquivoSemCabecalho");
		all.add("importarProducoesArquivoSemIdentificador");
		all.add("verSubmissoesDeImportacaoComSucesso");
		all.add("verSubmissoesDeDuasImportacoesDiferentesComSucesso");
		all.add("paginaImportacaoAvaliadoresCoordenador");
		all.add("importarAvaliadoresCoordenadorComSucesso");
		all.add("importarAvaliadoresCoordenadorDuasVezesComSucesso");
		all.add("importarAvaliadoresCoordenadorSemArquivo");
		all.add("importarAvaliadoresCoordenadorArquivoVazio");
		all.add("importarAvaliadoresCoordenadorArquivoSemCabecalho");
		all.add("importarAvaliadoresCoordenadorArquivoSemDocumento");
		all.add("verAvaliadoresDeImportacao");
		all.add("verCoordenadorDeImportacao");
		all.add("verAvaliadoresDeSegundaImportacaoComDadosAtualizados");
		all.add("verCoordenadorDeSegundaImportacaoComDadosAtualizados");
		all.add("verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados");
		all.add("verCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados");
		all.add("verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados");
		all.add("verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados");
		all.add("paginaConfiguracaoDeFichaDeAvaliacaoFichaNaoCadastrada");
		all.add("cadastrarFichaDeAvaliacao");
		all.add("cadastrarFichaDeAvaliacaoSomatorioDePontosAcimaDe100");
		all.add("recadastrarFichaDeAvaliacao");
		all.add("verFichaDeAvaliacaoCadastrada");
		all.add("verFormularioDaFichaDeAvaliacaoCadastrada");
		all.add("verFichaDeAvaliacaoRecadastrada");
		all.add("paginaAtribuicaoDeAvaliadoresSemProducoes");
		all.add("distribuirAvaliadoresComSucesso");
		all.add("distribuirAvaliadoresSemPreencherPrazoDeAvaliacao");
		all.add("distribuirAvaliadoresSemCoordenadorDeArea");
		all.add("distribuirAvaliadoresComMaisDeUmCoordenadorDeArea");
		all.add("distribuirAvaliadoresSemAvaliadores");
		all.add("distribuirAvaliadoresSemFichaDeAvaliacao");
		all.add("verDistribuirAvaliadoresComSucessoPrimeiraProducao");
		all.add("verDistribuirAvaliadoresComSucessoSegundaProducao");
		all.add("verDistribuirAvaliadoresComSucessoPelaSegundaVezTendoDadosAtualizadosPrimeiraProducao");
		all.add("verDistribuirAvaliadoresComSucessoPelaSegundaVezTendoDadosAtualizadosSegundaProducao");
		all.add("avaliadorAvalia");
		all.add("coordenadorDeAreaRatifica");
		all.add("paginaComPlugins");
		all.add("entrarNaPaginaDoPlugin");
		populateMatrix(matrix, all, 3);
	}

	private void productionImport(Matrix<TestCase, String, Integer> matrix) {
		List<String> productionImport = new LinkedList<String>();
		productionImport.add("paginaImportacaoDeProducoes");
		productionImport.add("importarProducoesComSucesso");
		productionImport.add("importarProducoesDuasVezesComSucesso");
		productionImport.add("importarProducoesSemArquivo");
		productionImport.add("importarProducoesArquivoVazio");
		productionImport.add("importarProducoesArquivoSemCabecalho");
		productionImport.add("importarProducoesArquivoSemIdentificador");
		productionImport.add("verSubmissoesDeImportacaoComSucesso");
		productionImport.add("verSubmissoesDeDuasImportacoesDiferentesComSucesso");
		populateMatrix(matrix, productionImport, 19);
		matrix.set("paginaImportacaoDeProducoes", "paginaImportacaoDeProducoes", 21);
		matrix.set("importarProducoesComSucesso", "importarProducoesComSucesso", 25);
		matrix.set("importarProducoesComSucesso", "importarProducoesDuasVezesComSucesso", 22);
		matrix.set("importarProducoesComSucesso", "verSubmissoesDeImportacaoComSucesso", 22);
		matrix.set("importarProducoesComSucesso", "verSubmissoesDeDuasImportacoesDiferentesComSucesso", 22);
		matrix.set("importarProducoesDuasVezesComSucesso", "importarProducoesDuasVezesComSucesso", 30);
		matrix.set("importarProducoesDuasVezesComSucesso", "importarProducoesComSucesso", 22);
		matrix.set("importarProducoesDuasVezesComSucesso", "verSubmissoesDeImportacaoComSucesso", 23);
		matrix.set("importarProducoesDuasVezesComSucesso", "verSubmissoesDeDuasImportacoesDiferentesComSucesso", 27);
		matrix.set("importarProducoesSemArquivo", "importarProducoesSemArquivo", 20);
		matrix.set("importarProducoesArquivoVazio", "importarProducoesArquivoVazio", 22);
		matrix.set("importarProducoesArquivoSemCabecalho", "importarProducoesArquivoSemCabecalho", 22);
		matrix.set("importarProducoesArquivoSemIdentificador", "importarProducoesArquivoSemIdentificador", 22);
		matrix.set("verSubmissoesDeImportacaoComSucesso", "verSubmissoesDeImportacaoComSucesso", 25);
		matrix.set("verSubmissoesDeImportacaoComSucesso", "importarProducoesComSucesso", 22);
		matrix.set("verSubmissoesDeImportacaoComSucesso", "importarProducoesDuasVezesComSucesso", 23);
		matrix.set("verSubmissoesDeImportacaoComSucesso", "verSubmissoesDeDuasImportacoesDiferentesComSucesso", 23);
		matrix.set("verSubmissoesDeDuasImportacoesDiferentesComSucesso", "verSubmissoesDeDuasImportacoesDiferentesComSucesso", 30);
		matrix.set("verSubmissoesDeDuasImportacoesDiferentesComSucesso", "importarProducoesComSucesso", 22);
		matrix.set("verSubmissoesDeDuasImportacoesDiferentesComSucesso", "importarProducoesDuasVezesComSucesso", 27);
		matrix.set("verSubmissoesDeDuasImportacoesDiferentesComSucesso", "verSubmissoesDeImportacaoComSucesso", 23);
	}

	private void reviewerImport(Matrix<TestCase, String, Integer> matrix) {
		List<String> reviewerImport = new LinkedList<String>();
		reviewerImport.add("paginaImportacaoAvaliadoresCoordenador");
		reviewerImport.add("importarAvaliadoresCoordenadorComSucesso");
		reviewerImport.add("importarAvaliadoresCoordenadorDuasVezesComSucesso");
		reviewerImport.add("importarAvaliadoresCoordenadorSemArquivo");
		reviewerImport.add("importarAvaliadoresCoordenadorArquivoVazio");
		reviewerImport.add("importarAvaliadoresCoordenadorArquivoSemCabecalho");
		reviewerImport.add("importarAvaliadoresCoordenadorArquivoSemDocumento");
		reviewerImport.add("verAvaliadoresDeImportacao");
		reviewerImport.add("verCoordenadorDeImportacao");
		reviewerImport.add("verAvaliadoresDeSegundaImportacaoComDadosAtualizados");
		reviewerImport.add("verCoordenadorDeSegundaImportacaoComDadosAtualizados");
		reviewerImport.add("verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados");
		reviewerImport.add("verCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados");
		reviewerImport.add("verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados");
		reviewerImport.add("verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados");
		populateMatrix(matrix, reviewerImport, 19);
		matrix.set("paginaImportacaoAvaliadoresCoordenador", "paginaImportacaoAvaliadoresCoordenador", 22);
		matrix.set("importarAvaliadoresCoordenadorComSucesso", "importarAvaliadoresCoordenadorComSucesso", 28);
		matrix.set("importarAvaliadoresCoordenadorDuasVezesComSucesso", "importarAvaliadoresCoordenadorDuasVezesComSucesso", 33);
		matrix.set("importarAvaliadoresCoordenadorDuasVezesComSucesso", "verAvaliadoresDeImportacao", 23);
		matrix.set("importarAvaliadoresCoordenadorDuasVezesComSucesso", "verCoordenadorDeImportacao", 23);
		matrix.set("importarAvaliadoresCoordenadorDuasVezesComSucesso", "verAvaliadoresDeSegundaImportacaoComDadosAtualizados", 23);
		matrix.set("importarAvaliadoresCoordenadorDuasVezesComSucesso", "verCoordenadorDeSegundaImportacaoComDadosAtualizados", 23);
		matrix.set("importarAvaliadoresCoordenadorDuasVezesComSucesso", "verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("importarAvaliadoresCoordenadorDuasVezesComSucesso", "verCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("importarAvaliadoresCoordenadorDuasVezesComSucesso", "verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("importarAvaliadoresCoordenadorDuasVezesComSucesso", "verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("importarAvaliadoresCoordenadorSemArquivo", "importarAvaliadoresCoordenadorSemArquivo", 20);
		matrix.set("importarAvaliadoresCoordenadorArquivoVazio", "importarAvaliadoresCoordenadorArquivoVazio", 22);
		matrix.set("importarAvaliadoresCoordenadorArquivoSemCabecalho", "importarAvaliadoresCoordenadorArquivoSemCabecalho", 22);
		matrix.set("importarAvaliadoresCoordenadorArquivoSemDocumento", "importarAvaliadoresCoordenadorArquivoSemDocumento", 22);
		matrix.set("verAvaliadoresDeImportacao", "verAvaliadoresDeImportacao", 28);
		matrix.set("verAvaliadoresDeImportacao", "importarAvaliadoresCoordenadorDuasVezesComSucesso", 23);
		matrix.set("verAvaliadoresDeImportacao", "verCoordenadorDeImportacao", 26);
		matrix.set("verAvaliadoresDeImportacao", "verAvaliadoresDeSegundaImportacaoComDadosAtualizados", 23);
		matrix.set("verAvaliadoresDeImportacao", "verCoordenadorDeSegundaImportacaoComDadosAtualizados", 23);
		matrix.set("verAvaliadoresDeImportacao", "verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("verAvaliadoresDeImportacao", "verCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("verAvaliadoresDeImportacao", "verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("verAvaliadoresDeImportacao", "verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("verCoordenadorDeImportacao", "verCoordenadorDeImportacao", 28);
		matrix.set("verCoordenadorDeImportacao", "importarAvaliadoresCoordenadorDuasVezesComSucesso", 23);
		matrix.set("verCoordenadorDeImportacao", "verAvaliadoresDeImportacao", 26);
		matrix.set("verCoordenadorDeImportacao", "verAvaliadoresDeSegundaImportacaoComDadosAtualizados", 23);
		matrix.set("verCoordenadorDeImportacao", "verCoordenadorDeSegundaImportacaoComDadosAtualizados", 23);
		matrix.set("verCoordenadorDeImportacao", "verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("verCoordenadorDeImportacao", "verCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("verCoordenadorDeImportacao", "verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("verCoordenadorDeImportacao", "verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 23);
		matrix.set("verAvaliadoresDeSegundaImportacaoComDadosAtualizados", "verAvaliadoresDeSegundaImportacaoComDadosAtualizados", 33);
		matrix.set("verAvaliadoresDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorDuasVezesComSucesso", 23);
		matrix.set("verAvaliadoresDeSegundaImportacaoComDadosAtualizados", "verAvaliadoresDeImportacao", 23);
		matrix.set("verAvaliadoresDeSegundaImportacaoComDadosAtualizados", "verCoordenadorDeImportacao", 23);
		matrix.set("verAvaliadoresDeSegundaImportacaoComDadosAtualizados", "verCoordenadorDeSegundaImportacaoComDadosAtualizados", 31);
		matrix.set("verAvaliadoresDeSegundaImportacaoComDadosAtualizados", "verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("verAvaliadoresDeSegundaImportacaoComDadosAtualizados", "verCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("verAvaliadoresDeSegundaImportacaoComDadosAtualizados", "verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("verAvaliadoresDeSegundaImportacaoComDadosAtualizados", "verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("verCoordenadorDeSegundaImportacaoComDadosAtualizados", "verCoordenadorDeSegundaImportacaoComDadosAtualizados", 33);
		matrix.set("verCoordenadorDeSegundaImportacaoComDadosAtualizados", "importarAvaliadoresCoordenadorDuasVezesComSucesso", 23);
		matrix.set("verCoordenadorDeSegundaImportacaoComDadosAtualizados", "verAvaliadoresDeImportacao", 23);
		matrix.set("verCoordenadorDeSegundaImportacaoComDadosAtualizados", "verCoordenadorDeImportacao", 23);
		matrix.set("verCoordenadorDeSegundaImportacaoComDadosAtualizados", "verAvaliadoresDeSegundaImportacaoComDadosAtualizados", 31);
		matrix.set("verCoordenadorDeSegundaImportacaoComDadosAtualizados", "verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("verCoordenadorDeSegundaImportacaoComDadosAtualizados", "verCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("verCoordenadorDeSegundaImportacaoComDadosAtualizados", "verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("verCoordenadorDeSegundaImportacaoComDadosAtualizados", "verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 25);
		matrix.set("verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 39);
		matrix.set("verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorDuasVezesComSucesso", 23);
		matrix.set("verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verAvaliadoresDeImportacao", 23);
		matrix.set("verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verCoordenadorDeImportacao", 23);
		matrix.set("verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verAvaliadoresDeSegundaImportacaoComDadosAtualizados", 25);
		matrix.set("verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verCoordenadorDeSegundaImportacaoComDadosAtualizados", 25);
		matrix.set("verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 37);
		matrix.set("verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 34);
		matrix.set("verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 34);
		matrix.set("verCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 39);
		matrix.set("verCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorDuasVezesComSucesso", 23);
		matrix.set("verCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verAvaliadoresDeImportacao", 23);
		matrix.set("verCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verCoordenadorDeImportacao", 23);
		matrix.set("verCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verAvaliadoresDeSegundaImportacaoComDadosAtualizados", 25);
		matrix.set("verCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verCoordenadorDeSegundaImportacaoComDadosAtualizados", 25);
		matrix.set("verCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 37);
		matrix.set("verCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 34);
		matrix.set("verCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 34);
		matrix.set("verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 41);
		matrix.set("verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorDuasVezesComSucesso", 23);
		matrix.set("verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verAvaliadoresDeImportacao", 23);
		matrix.set("verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verCoordenadorDeImportacao", 23);
		matrix.set("verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verAvaliadoresDeSegundaImportacaoComDadosAtualizados", 25);
		matrix.set("verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verCoordenadorDeSegundaImportacaoComDadosAtualizados", 25);
		matrix.set("verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 34);
		matrix.set("verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 34);
		matrix.set("verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 39);
		matrix.set("verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 41);
		matrix.set("verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "importarAvaliadoresCoordenadorDuasVezesComSucesso", 23);
		matrix.set("verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verAvaliadoresDeImportacao", 23);
		matrix.set("verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verCoordenadorDeImportacao", 23);
		matrix.set("verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verAvaliadoresDeSegundaImportacaoComDadosAtualizados", 25);
		matrix.set("verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verCoordenadorDeSegundaImportacaoComDadosAtualizados", 25);
		matrix.set("verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 34);
		matrix.set("verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 34);
		matrix.set("verCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", "verAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados", 39);
	}

	private void evaluationForm(Matrix<TestCase, String, Integer> matrix) {
		List<String> evaluationForm = new LinkedList<String>();
		evaluationForm.add("paginaConfiguracaoDeFichaDeAvaliacaoFichaNaoCadastrada");
		evaluationForm.add("cadastrarFichaDeAvaliacao");
		evaluationForm.add("cadastrarFichaDeAvaliacaoSomatorioDePontosAcimaDe100");
		evaluationForm.add("recadastrarFichaDeAvaliacao");
		evaluationForm.add("verFichaDeAvaliacaoCadastrada");
		evaluationForm.add("verFormularioDaFichaDeAvaliacaoCadastrada");
		evaluationForm.add("verFichaDeAvaliacaoRecadastrada");
		populateMatrix(matrix, evaluationForm, 19);
	}

	private void reviewerDistribution(Matrix<TestCase, String, Integer> matrix) {
		List<String> reviewerDistribution = new LinkedList<String>();
		reviewerDistribution.add("paginaAtribuicaoDeAvaliadoresSemProducoes");
		reviewerDistribution.add("distribuirAvaliadoresComSucesso");
		reviewerDistribution.add("distribuirAvaliadoresSemPreencherPrazoDeAvaliacao");
		reviewerDistribution.add("distribuirAvaliadoresSemCoordenadorDeArea");
		reviewerDistribution.add("distribuirAvaliadoresComMaisDeUmCoordenadorDeArea");
		reviewerDistribution.add("distribuirAvaliadoresSemAvaliadores");
		reviewerDistribution.add("distribuirAvaliadoresSemFichaDeAvaliacao");
		reviewerDistribution.add("verDistribuirAvaliadoresComSucessoPrimeiraProducao");
		reviewerDistribution.add("verDistribuirAvaliadoresComSucessoSegundaProducao");
		reviewerDistribution.add("verDistribuirAvaliadoresComSucessoPelaSegundaVezTendoDadosAtualizadosPrimeiraProducao");
		reviewerDistribution.add("verDistribuirAvaliadoresComSucessoPelaSegundaVezTendoDadosAtualizadosSegundaProducao");
		populateMatrix(matrix, reviewerDistribution, 19);
	}

	private void assessment(Matrix<TestCase, String, Integer> matrix) {
		List<String> assessment = new LinkedList<String>();
		assessment.add("avaliadorAvalia");
		assessment.add("coordenadorDeAreaRatifica");
		populateMatrix(matrix, assessment, 19);
	}

	private void plugin(Matrix<TestCase, String, Integer> matrix) {
		List<String> plugin = new LinkedList<String>();
		plugin.add("paginaComPlugins");
		plugin.add("entrarNaPaginaDoPlugin");
		populateMatrix(matrix, plugin, 19);
	}

	private void populateMatrix(Matrix<TestCase, String, Integer> matrix, List<String> elements, Integer reusedFixtures) {
		for (String source : elements) {
			for (String target : elements) {
				if (!source.equals(target)) {
					matrix.set(source, target, reusedFixtures);
				}
			}
		}
	}

}
