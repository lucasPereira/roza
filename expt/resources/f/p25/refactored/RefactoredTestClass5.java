import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void funcionarioNaoPodeTerMaisDe10Ocorrencias() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			criaOcorrenciasProjeto(projeto, funcionario, 11);
		}, "Um funcion�rio n�o pode ser respons�vel por mais de 10 ocorrencias");
	}
}
