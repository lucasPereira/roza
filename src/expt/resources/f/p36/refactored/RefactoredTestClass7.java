import org.junit.Test;

public class RefactoredTestClass7 {

	@Test()
	public void projetoCriarOcorrenciaTarefa() {
		Funcionario joao = empresa.criarFuncionario("Joao");
		Ocorrencia tarefa = projeto.criarTarefa("Criar Botao na tela", joao, PrioridadeOcorrencia.alta);
		Ocorrencia esperado = new Ocorrencia(0, 0, "Criar Botao na tela", joao, TipoOcorrencia.tarefa, PrioridadeOcorrencia.alta);
		assertEquals(esperado, tarefa);
	}
}
