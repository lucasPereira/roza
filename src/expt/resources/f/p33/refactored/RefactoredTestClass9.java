import org.junit.Test;

public class RefactoredTestClass9 {

	@Test()
	public void limiteOcorrencia() {
		criarOcorrencia.criar10ocorrencias();
		assertThrows(LimiteOcorrencias.class, () -> new Ocorrencia(Tipo.BUG, Prioridade.BAIXA, resumo, funcionario1, projeto1));
	}
}
