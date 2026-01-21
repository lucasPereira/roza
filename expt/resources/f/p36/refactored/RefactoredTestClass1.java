import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void fecharOcorrencia() {
		Funcionario joao = empresa.criarFuncionario("Joao");
		Ocorrencia melhoria = projetoA.criarMelhoria("Atualizar Botao na tela", joao, PrioridadeOcorrencia.alta);
		joao.fecharOcorrencia(melhoria);
		assertEquals(EstadoOcorrencia.fechado, melhoria.getEstado());
		assertEquals(0, joao.getOcorrenciasAbertas().size());
	}
}
