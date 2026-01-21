import org.junit.Test;

public class RefactoredTestClass11 {

	@Test()
	public void testarFuncionarioOcorrencia() {
		String nomeResponsavelEsperado = "Gustavo Kundlatsch";
		Ocorrencia falhaSoma = projetoCalculadora.criarOcorrencia("Falha na soma", gustavoKundlatsch, TipoOcorrencia.BUG);
		String nomeResponsavelObtido = falhaSoma.obterNomeResponsavel();
		assertEquals(nomeResponsavelEsperado, nomeResponsavelObtido);
	}
}
