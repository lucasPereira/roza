package br.ufsc.ine.leb.roz.expirement1;

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

		List<String> produciontImport = new LinkedList<String>();
		produciontImport.add("paginaImportacaoDeProducoes");
		produciontImport.add("importarProducoesComSucesso");
		produciontImport.add("importarProducoesDuasVezesComSucesso");
		produciontImport.add("importarProducoesSemArquivo");
		produciontImport.add("importarProducoesArquivoVazio");
		produciontImport.add("importarProducoesArquivoSemCabecalho");
		produciontImport.add("importarProducoesArquivoSemIdentificador");
		produciontImport.add("verSubmissoesDeImportacaoComSucesso");
		produciontImport.add("verSubmissoesDeDuasImportacoesDiferentesComSucesso");
		populateMatrix(matrix, produciontImport, 19);
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
		populateMatrix(matrix, reviewerImport, 11);

		List<String> evaluationForm = new LinkedList<String>();
		evaluationForm.add("paginaConfiguracaoDeFichaDeAvaliacaoFichaNaoCadastrada");
		evaluationForm.add("cadastrarFichaDeAvaliacao");
		evaluationForm.add("cadastrarFichaDeAvaliacaoSomatorioDePontosAcimaDe100");
		evaluationForm.add("recadastrarFichaDeAvaliacao");
		evaluationForm.add("verFichaDeAvaliacaoCadastrada");
		evaluationForm.add("verFormularioDaFichaDeAvaliacaoCadastrada");
		evaluationForm.add("verFichaDeAvaliacaoRecadastrada");
		populateMatrix(matrix, evaluationForm, 19);

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

		List<String> assessment = new LinkedList<String>();
		assessment.add("avaliadorAvalia");
		assessment.add("coordenadorDeAreaRatifica");
		populateMatrix(matrix, assessment, 19);

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
