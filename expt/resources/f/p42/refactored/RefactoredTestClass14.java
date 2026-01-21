import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass14 {

	private Funcionario joao;

	@Before()
	public void setup() {
		joao = empresa.criaFuncionario("Joao da Silva");
	}

	@Test()
	public void testeDefineResponsabildiadeDeDezOcorrenciasParaFuncionario() {
		criaOcorrenciasEDefineResponsavel(10, joao);
		assertEquals(10, joao.obterOcorrencias().size());
	}

	@Test()
	public void testeDefineResponsabildiadeDeOnzeOcorrenciasParaFuncionario() {
		assertThrows(OcorrenciaException.class, () -> {
			criaOcorrenciasEDefineResponsavel(11, joao);
		});
	}
}
