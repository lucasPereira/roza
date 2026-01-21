import org.junit.Test;

public class RefactoredTestClass6 {

	@Test()
	public void projetoCriarOcorrenciaMelhoria() {
		Funcionario joao = empresa.criarFuncionario("Joao");
		Ocorrencia melhoria = projeto.criarMelhoria("Atualizar Botao na tela", joao, PrioridadeOcorrencia.alta);
		Ocorrencia esperado = new Ocorrencia(0, 0, "Atualizar Botao na tela", joao, TipoOcorrencia.melhoria, PrioridadeOcorrencia.alta);
		assertEquals(esperado, melhoria);
	}
}
