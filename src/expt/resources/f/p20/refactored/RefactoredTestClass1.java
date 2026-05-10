import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void completaOcorrencia() {
		Empresa empresa = new Empresa("Empresa 1");
		empresa.adicionaFuncionario(new Funcionario("Joao B. da Rosa"));
		empresa.adicionaProjeto("Projeto 1");
		empresa.adicionaFuncionarioNoProjeto("Projeto 1", "Joao B. da Rosa");
		empresa.adicionaOcorrencia("O X", "Projeto 1", "Joao B. da Rosa", PrioridadeOcorrencia.MEDIA, TipoOcorrencia.BUG);
		Ocorrencia ocorrencia = empresa.getOcorrencia("Projeto 1", 1);
		ocorrencia.getFuncionario().completaOcorrencia(ocorrencia);
		assertEquals(1, empresa.getFuncionario("Joao B. da Rosa").getNumOcorrencias());
		assertTrue(empresa.getOcorrencia("Projeto 1", 1).getCompleta());
		assertEquals(0, empresa.getFuncionario("Joao B. da Rosa").getNumOcorrencias());
		assertEquals(1, empresa.getFuncionario("Joao B. da Rosa").getNumOcorrenciasCompletas());
	}
}
