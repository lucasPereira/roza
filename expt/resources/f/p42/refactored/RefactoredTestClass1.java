import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void testeCriaDuasOcorrenciasDoProjetoX() {
		Ocorrencia ocorrencia1 = projeto.criaOcorrencia();
		Ocorrencia ocorrencia2 = projeto.criaOcorrencia();
		List<Ocorrencia> lista = new ArrayList<>();
		lista.add(ocorrencia1);
		lista.add(ocorrencia2);
		assertNotEquals(ocorrencia1.obterID(), ocorrencia2.obterID());
		assertEquals(ocorrencia1, projeto.obterOcorrencia(0));
		assertEquals(ocorrencia2, projeto.obterOcorrencia(1));
		assertEquals(lista, projeto.obterOcorrencias());
		assertEquals(2, projeto.obterOcorrencias().size());
	}
}
