import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void projetoCriarOcorrenciaBug() {
		Funcionario joao = empresa.criarFuncionario("Joao");
		Ocorrencia bug = projeto.criarBug("Criar Botao na tela", joao, PrioridadeOcorrencia.alta);
		Ocorrencia esperado = new Ocorrencia(0, 0, "Criar Botao na tela", joao, TipoOcorrencia.bug, PrioridadeOcorrencia.alta);
		assertEquals(esperado, bug);
	}
}
