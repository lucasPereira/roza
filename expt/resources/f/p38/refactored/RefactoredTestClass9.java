import org.junit.Test;

public class RefactoredTestClass9 {

	@Test()
	public void shouldGetAddedOcorrencia() {
		var tom = new Funcionario("Tom");
		var ocorrenciaBugPrioridadeMedia = new Ocorrencia("Resumao", ETIPO_TAREFA.BUG, EPRIORIDADE_TAREFA.MEDIA);
		var projeto = new Projeto();
		this.empresa.addOcorrencia(tom, ocorrenciaBugPrioridadeMedia, projeto);
		assertThat(this.empresa.getListaOcorrenciasSize(), equalTo(1));
		assertThat(this.empresa.getOcorrenciaOfFuncionario(tom), equalTo(ocorrenciaBugPrioridadeMedia));
		assertThat(projeto.getSizeListaOcorrencias(), equalTo(1));
	}
}
