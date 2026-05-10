import org.junit.Test;

public class RefactoredTestClass6 {

	@Test()
	public void criarMaisDeDezOcorrencias() {
		Utilitarios utilitarios = new Utilitarios();
		utilitarios.criarDezOcorrencias(newProjeto, stefanoFuncionario);
		assertEquals(utilitarios.getOcorrencia(1), stefanoFuncionario.getOcorrencia(newProjeto, 1));
		assertEquals(null, stefanoFuncionario.getOcorrencia(newProjeto, 10));
	}
}
