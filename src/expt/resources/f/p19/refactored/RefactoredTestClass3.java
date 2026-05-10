import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass3 {

	private Funcionario funcionario;

	private Projeto projetoId1;

	@Before()
	public void setup() {
		funcionario = new Funcionario(1, "Joao");
		projetoId1 = new Projeto(1, "Mudança na interface da home");
	}

	@Test()
	public void testAdicionarOcorrencia() {
		Ocorrencia ocorrencia = new Ocorrencia(1, "Notificação de sugestão de amigos", Tipo.TAREFA, Prioridade.MEDIA, funcionario);
		projetoId1.adicionarOcorrencia(ocorrencia);
		assertEquals(1, projetoId1.getOcorrencias().size());
		assertEquals(1, funcionario.getQuantidadeDeOcorrencias());
		assertTrue(projetoId1.getOcorrencias().contains(ocorrencia));
	}

	@Test()
	public void testCriarProjeto() {
		Projeto projetoTeste = new Projeto(2, "Recomendação de amigos");
		assertEquals("Recomendação de amigos", projetoTeste.getNome());
		assertEquals(2, projetoTeste.getId());
		assertEquals(0, projetoTeste.getOcorrencias().size());
	}

	@Test()
	public void testDesigualdadeDeProjetos() {
		Projeto projetoId3 = new Projeto(3, "Recomendação de propaganda");
		assertNotEquals(projetoId3, projetoId1);
	}

	@Test()
	public void testIgualdadeDeProjetos() {
		assertEquals(projetoId1, projetoId1);
	}

	@Test()
	public void testIgualdadeDeProjetosInstanciasDiferentes() {
		Projeto projetoInstancia2 = new Projeto(1, "Mudança na interface da home");
		assertEquals(projetoId1, projetoInstancia2);
	}
}
