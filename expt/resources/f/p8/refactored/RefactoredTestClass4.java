import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass4 {

	private Empresa empresa;

	private Funcionario bob;

	private GeradorDeOcorrencias gerador;

	private Projeto calculadora;

	@Before()
	public void setup() {
		empresa = new Empresa();
		bob = new Funcionario();
		gerador = new GeradorDeOcorrencias();
		empresa.addFuncionario(bob);
		calculadora = new Projeto();
	}

	@Test()
	public void dezOcorrencias() {
		List<Ocorrencia> ocorrencias = gerador.gerarOcorrencias(bob, 10);
		empresa.addProjeto(calculadora);
		calculadora.addOcorrencias(ocorrencias);
		assertEquals(10, bob.numOcorrencias());
	}

	@Test()
	public void onzeOcorrencias() {
		List<Ocorrencia> ocorrencias = gerador.gerarOcorrencias(bob, 11);
		empresa.addProjeto(calculadora);
		calculadora.addOcorrencias(ocorrencias);
	}
}
