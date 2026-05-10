import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void testeFuncionarioComMaisDeDezOcorrencias() {
		Funcionario einstein = new Funcionario("Albert Einstein");
		Ocorrencia criarBomba = new Ocorrencia("1", "Criar a bomba", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		new Ocorrencia("2", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		new Ocorrencia("3", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		new Ocorrencia("4", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		new Ocorrencia("5", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		new Ocorrencia("6", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		new Ocorrencia("7", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		new Ocorrencia("8", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		new Ocorrencia("9", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		new Ocorrencia("10", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		new Ocorrencia("11", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
	}
}
