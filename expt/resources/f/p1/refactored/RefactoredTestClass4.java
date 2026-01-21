import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass4 {

	@Before()
	public void setup() {
		this.joao = new Funcionario();
		this.projeto = new Projeto();
	}

	@Test()
	public void ResponsavelPorProjetosMenorQue10() {
		List<Projeto> projetos = joao.getProjetosResponsavel();
		assertEquals(0, projetos.size());
	}

	@Test()
	public void additionarProjetoNoFunctionario() {
		joao.addProjetoResponsavel(projeto);
		List<Projeto> responsabilidadesDoJoao = joao.getProjetosResponsavel();
		assertEquals(1, responsabilidadesDoJoao.size());
		assertEquals(projeto, responsabilidadesDoJoao.get(0));
	}

	@Test()
	public void criarEFecharCom12Ocorrencias() {
		criar11OcorrenciasParaJoao();
	}

	@Test()
	public void criarFonctionario() {
		assertNotEquals(null, joao);
	}

	@Test()
	public void getProjetos() {
		joao.addProjetoQueParticipa(projeto);
		List<Projeto> projetos = joao.getProjetos();
		assertEquals(1, projetos.size());
		assertEquals(projeto, projetos.get(0));
	}

	@Test()
	public void getResponsavelDoProjeto() {
		this.projeto.setResponsavel(this.joao);
		assertEquals(this.joao, this.projeto.getResponsavel());
	}

	@Test()
	public void obterOcorrencia() {
		Ocorrencia ocorrencia = new Ocorrencia(null, null, joao);
		projeto.addOcorrencia(ocorrencia);
		assertEquals(ocorrencia, projeto.getOcorrencia(0));
	}
}
