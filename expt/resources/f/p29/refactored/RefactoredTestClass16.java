import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass16 {

	private Funcionario joaoFuncionario;

	private Ocorrencia newOcorrencia;

	@Before()
	public void setup() {
		joaoFuncionario = new Funcionario(newEmpresa, "Joao santana", 2);
		newOcorrencia = new Ocorrencia(newProjeto, stefanoFuncionario, "bug", "alta", 1);
	}

	@Test()
	public void mudarFuncionarioOcorrencia() {
		newOcorrencia.changeFuncionario(joaoFuncionario);
		assertEquals(joaoFuncionario, newOcorrencia.getFuncionario());
	}

	@Test()
	public void mudarFuncionarioOcorrenciaAposFechamento() {
		newOcorrencia.finalizarOcorrencia(1);
		newOcorrencia.changeFuncionario(joaoFuncionario);
		assertEquals(stefanoFuncionario, newOcorrencia.getFuncionario());
	}
}
