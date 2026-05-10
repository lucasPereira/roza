import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ProjetoTest {

	@Test
	public void testAdicionarOcorrenciaAoProjeto() {
		Projeto hotel = new Projeto("Hotel");
		Funcionario pedro = new Funcionario("Pedro");
		Ocorrencia o = new Ocorrencia("Hall", Ocorrencia.Tipo.TAREFA, pedro, Ocorrencia.Prioridade.MEDIA);
		
		hotel.adicionarOcorrencia(o);
		
		assertEquals(1, hotel.ocorrencias().size());
		assertTrue(hotel.ocorrencias().contains(o));
	}
}
