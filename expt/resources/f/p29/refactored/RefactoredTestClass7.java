import org.junit.Test;

public class RefactoredTestClass7 {

	@Test()
	public void mudarPropriedadeOcorrenciaAposFechamento() {
		Ocorrencia newOcorrencia = new Ocorrencia(newProjeto, stefanoFuncionario, "bug", "alta", 1);
		newOcorrencia.finalizarOcorrencia(1);
		newOcorrencia.changePrioridade("baixa");
		assertEquals("alta", newOcorrencia.getPrioridade());
	}
}
