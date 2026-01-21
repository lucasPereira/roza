import org.junit.Test;

public class RefactoredTestClass12 {

	@Test()
	public void shouldHaveCreatedName() {
		assertThat(funcionario.getName(), equalTo("Bob"));
	}
}
