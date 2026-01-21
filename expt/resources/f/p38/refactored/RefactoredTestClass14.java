import org.junit.Test;

public class RefactoredTestClass14 {

	@Test()
	public void shouldHaveUpTo10Ocorrencias() {
		assertThat(funcionario.getNumOcorrencias(), equalTo(0));
	}
}
