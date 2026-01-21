import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void criarEFecharCom10Ocorrencias() {
		this.joao = new Funcionario();
		this.projeto = new Projeto();
		criar10OcorrenciasParaJoao();
		joao.fecharOcorrencia(0);
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		;
		assertEquals(10, joao.getOcorrenciasAbertas().size());
		assertEquals(9, joao.getOcorrenciasAbertas().size());
		assertEquals(10, joao.getOcorrenciasAbertas().size());
	}
}
