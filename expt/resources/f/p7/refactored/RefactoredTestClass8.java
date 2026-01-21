import org.junit.Test;

public class RefactoredTestClass8 {

	@Test()
	public void newOcorrencia_2() {
		Funcionario meuResponsavel = new Funcionario();
		Ocorrencia primeiraOcorrencia = new Ocorrencia("", new Funcionario(), Tipo.TAREFA);
		Tipo meuTipo = Tipo.TAREFA;
		Ocorrencia segundaOcorrencia = new Ocorrencia(meuResumo, meuResponsavel, meuTipo);
		assertEquals(primeiraOcorrencia.getChave() + 1, segundaOcorrencia.getChave());
		assertEquals(meuResumo, segundaOcorrencia.getResumo());
		assertEquals(meuResponsavel, segundaOcorrencia.getFuncionarioResponsavel());
		assertEquals(meuTipo, segundaOcorrencia.getTipo());
		assertEquals(Estado.ABERTA, segundaOcorrencia.getEstado());
		assertEquals(Prioridade.MEDIA, segundaOcorrencia.getPrioridade());
	}
}
