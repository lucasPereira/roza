import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void ProjetoPreSalDaPetrobrasCriaOcorrencia() {
		Empresa empresa = new Empresa("Petrobras");
		Projeto projetoPreSal = new Projeto("Pré-Sal");
		projetoPreSal.criaOcorrencia("Problema 1 no Pré-Sal");
		empresa.criarProjeto(projetoPreSal);
		assertEquals("Problema 1 no Pré-Sal", projetoPreSal.getOcorrenciaByName("Problema 1 no Pré-Sal").getNome());
	}
}
