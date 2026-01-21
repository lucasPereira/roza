import org.junit.Test;

public class RefactoredTestClass6 {

	@Test()
	public void testeAdicionaDuasOcorrencias() {
		Projeto proj = new Projeto("projeto x");
		String resumo = "ocorrencia que ocorreu no projeto x";
		String resumo2 = "outra ocorrencia que ocorreu no projeto x";
		proj.criaOcorrencia(resumo, Tipo.BUG, Prioridade.BAIXA);
		Ocorrencia result = proj.criaOcorrencia(resumo2, Tipo.BUG, Prioridade.BAIXA);
		assertEquals(1, result.retornaChave());
		assertEquals(resumo2, result.retornaResumo());
		assertEquals(Tipo.BUG, result.retornaTipo());
		assertEquals(Prioridade.BAIXA, result.retornaPrioridade());
		assertEquals(Estado.ABERTO, result.retornaEstado());
	}
}
