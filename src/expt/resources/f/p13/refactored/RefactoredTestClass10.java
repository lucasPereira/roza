import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass10 {

	private Ocorrencia o;

	@Before()
	public void setup() {
		o = new Ocorrencia("0000001", "001", Ocorrencia.Tipo.Melhoria, Ocorrencia.Prioridade.Alta, "000001");
	}

	@Test()
	public void testAdicionarEModificarOcorrênciaFechada() {
		o.fechar("0000001");
		assertThrows(Exception.class, () -> {
			o.alterarPrioridade(Ocorrencia.Prioridade.Baixa);
		});
		assertEquals(Ocorrencia.Estado.Fechado, o.estado());
	}

	@Test()
	public void testAdicionarOcorrenciaEmProjeto() {
		assertTrue(p.addOcorrencia(o));
		assertEquals(p.ocorrencias().size(), 1);
		assertEquals(empresa.contarOcorrencias("0000001"), 1);
	}
}
