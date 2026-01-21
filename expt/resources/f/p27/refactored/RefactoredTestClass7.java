import org.junit.Test;

public class RefactoredTestClass7 {

	@Test()
	public void testeAdicionaOcorrencia() {
		Projeto proj = new Projeto("projeto x");
		String resumo = "ocorrencia do projeto x";
		Ocorrencia result = proj.criaOcorrencia(resumo, Tipo.BUG, Prioridade.BAIXA);
		assertEquals(0, result.retornaChave());
		assertEquals(resumo, result.retornaResumo());
		assertEquals(Tipo.BUG, result.retornaTipo());
		assertEquals(Prioridade.BAIXA, result.retornaPrioridade());
		assertEquals(Estado.ABERTO, result.retornaEstado());
	}
}
