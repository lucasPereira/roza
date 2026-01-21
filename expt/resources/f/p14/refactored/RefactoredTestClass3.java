import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void FuncionarioResponsavelPorOnzeOcorrencias() {
		Empresa empresa = new Empresa("Petrobras");
		Projeto projetoPreSal = new Projeto("Pré-Sal");
		empresa.criarProjeto(projetoPreSal);
		Funcionario joao = new Funcionario("João da Silva");
		empresa.contrataFuncionario(joao);
		projetoPreSal.criaOcorrencia("Problema 1 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 1 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 2 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 2 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 3 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 3 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 4 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 4 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 5 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 5 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 6 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 6 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 7 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 7 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 8 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 8 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 9 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 9 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 10 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 10 no Pré-Sal"));
		projetoPreSal.criaOcorrencia("Problema 111 no Pré-Sal");
		projetoPreSal.setResponsavelOcorrencia(joao, projetoPreSal.getOcorrenciaByName("Problema 11 no Pré-Sal"));
	}
}
