import org.junit.Test;

public class RefactoredTestClass14 {

	@Test()
	public void unicIdTest() {
		Ocorrencia ocorrencia = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		Ocorrencia ocorrencia1 = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		Ocorrencia ocorrencia2 = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		Ocorrencia ocorrencia3 = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		assertNotEquals(ocorrencia1.getID(), ocorrencia2.getID());
		assertNotEquals(ocorrencia1.getID(), ocorrencia3.getID());
		assertNotEquals(ocorrencia3.getID(), ocorrencia2.getID());
	}
}
