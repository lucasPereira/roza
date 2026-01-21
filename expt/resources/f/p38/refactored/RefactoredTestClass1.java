import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void addMoreThan10OcorrenciasToFuncionario() {
		var tom = new Funcionario("Tom");
		this.create10OcorrenciasOnFuncionario(tom);
		var ocorrenciaBugPrioridadeAlta = new Ocorrencia("Resumao" + 10, ETIPO_TAREFA.BUG, EPRIORIDADE_TAREFA.ALTA);
		var projeto = new Projeto();
		this.empresa.addOcorrencia(tom, ocorrenciaBugPrioridadeAlta, projeto);
		assertTrue(tom.getNumOcorrencias() == 10);
	}
}
