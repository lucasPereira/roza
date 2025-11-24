import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void agenciaTrindadeErro() {
		Agencia bbTrindade = this.novaAgencia();
		assertNotEquals("002", bbTrindade.obterIdentificador());
		assertNotEquals("Campeche", bbTrindade.obterNome());
		assertNotEquals("Banco Brasil", bbTrindade.obterBanco().obterNome());
	}
}
