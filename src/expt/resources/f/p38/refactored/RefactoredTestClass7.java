import org.junit.Test;

public class RefactoredTestClass7 {

	@Test()
	public void shouldCorrectlyAddProjeto() {
		this.empresa.addProjeto(new Projeto());
		assertThat(this.empresa.getListaProjetosSize(), equalTo(1));
	}
}
