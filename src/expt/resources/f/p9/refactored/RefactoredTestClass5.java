import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void testCriacaoDeOcorrenciasGeraChavesUnicas() {
		Funcionario pedro = new Funcionario("Pedro");
		Ocorrencia o1 = new Ocorrencia("o1", Ocorrencia.Tipo.TAREFA, pedro, Ocorrencia.Prioridade.MEDIA);
		Ocorrencia o2 = new Ocorrencia("o2", Ocorrencia.Tipo.TAREFA, pedro, Ocorrencia.Prioridade.MEDIA);
		Ocorrencia o3 = new Ocorrencia("o3", Ocorrencia.Tipo.TAREFA, pedro, Ocorrencia.Prioridade.MEDIA);
		assertNotEquals(o1.chave(), o2.chave());
		assertNotEquals(o1.chave(), o3.chave());
		assertNotEquals(o2.chave(), o3.chave());
	}
}
