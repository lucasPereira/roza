import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void projetoSemOcorrencias() {
		Projeto projeto = new Projeto();
		List<Ocorrencia> listaDeOcorrenciasVazia = new ArrayList<Ocorrencia>();
		assertEquals(listaDeOcorrenciasVazia, projeto.obtemListaDeOcorrencias());
	}
}
