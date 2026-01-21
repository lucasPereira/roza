import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void testAdicionarEModificarOcorrênciaAberta() {
		Ocorrencia o = new Ocorrencia("0000001", "001", Ocorrencia.Tipo.Melhoria, Ocorrencia.Prioridade.Alta, "000001");
		o.alterarPrioridade(Ocorrencia.Prioridade.Baixa);
		o.fechar("0000001");
		assertEquals(Ocorrencia.Prioridade.Baixa, o.prioridade());
		assertEquals(Ocorrencia.Estado.Fechado, o.estado());
	}
}
