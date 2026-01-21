import org.junit.Test;

public class RefactoredTestClass10 {

	@Test()
	public void limiteOcorrenciaQuandoMudaFuncionario() {
		criarOcorrencia.criar10ocorrencias();
		Ocorrencia o11 = new Ocorrencia(Tipo.TAREFA, Prioridade.BAIXA, resumo, funcionario2, projeto1);
		assertThrows(LimiteOcorrencias.class, () -> o11.setFuncionario(funcionario1));
	}
}
