import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass1 {

	private Empresa empresa;

	private ArrayList<Projeto> projetos;

	private ArrayList<Funcionario> funcionarios;

	@Before()
	public void setup() {
		empresa = new Empresa();
		projetos = empresa.getProjetos();
		funcionarios = empresa.getFuncionarios();
	}

	@Test()
	public void conclusaoDeOcorrencia() {
		empresa.getFuncionarios().add(new Funcionario());
		Funcionario funcionario = empresa.getFuncionarios().get(0);
		Ocorrencia ocorrencia = new Ocorrencia("ocorrencia fofinha", Prioridade.BAIXA, funcionario);
		ocorrencia.chave = funcionario.getOcorrencias().indexOf(ocorrencia);
		funcionario.terminarOcorrencia(ocorrencia.chave);
		assertEquals(Estado.COMPLETADA, ocorrencia.getEstado());
	}

	@Test()
	public void empresa() {
		assertNotNull(empresa);
	}

	@Test()
	public void funcionario() {
		empresa.getFuncionarios().add(new Funcionario());
		Funcionario funcionario = empresa.getFuncionarios().get(0);
		assertNotNull(funcionario);
	}

	@Test()
	public void ocorrencia() {
		empresa.getFuncionarios().add(new Funcionario());
		Funcionario funcionario = empresa.getFuncionarios().get(0);
		Ocorrencia ocorrencia = new Ocorrencia("ocorrencia fofinha", Prioridade.BAIXA, funcionario);
		assertNotNull(ocorrencia);
	}

	@Test()
	public void projeto() {
		empresa.getProjetos().add(new Projeto());
		Projeto projeto = empresa.getProjetos().get(0);
		assertNotNull(projeto);
	}

	@Test()
	public void responsavelPorOcorrencia() {
		empresa.getFuncionarios().add(new Funcionario());
		Funcionario funcionario = empresa.getFuncionarios().get(0);
		Ocorrencia ocorrencia = new Ocorrencia("ocorrencia fofinha", Prioridade.BAIXA, funcionario);
		assertEquals(funcionario, ocorrencia.getResposavel());
	}
}
