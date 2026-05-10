package test.gerenciadorEmpresa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import gerenciadorEmpresa.Funcionario;
import gerenciadorEmpresa.Prioridades;
import gerenciadorEmpresa.Projeto;

public class TesteFuncionario {

	private Funcionario joao;
	private Projeto projeto;

	@Before
	public void setUp() throws Exception {
		this.joao = new Funcionario();
		this.projeto = new Projeto();
	}

	@Test
	public void criarFonctionario() throws Exception {
		assertNotEquals(null, joao);
	}

	@Test
	public void additionarProjetoNoFunctionario() throws Exception {
		joao.addProjetoResponsavel(projeto);
		List<Projeto> responsabilidadesDoJoao = joao.getProjetosResponsavel();
		
		assertEquals(1, responsabilidadesDoJoao.size());
		assertEquals(projeto, responsabilidadesDoJoao.get(0));
	}

	@Test
	public void getProjetos() throws Exception {
		
		joao.addProjetoQueParticipa(projeto);
		List<Projeto> projetos = joao.getProjetos();
		
		assertEquals(1, projetos.size());
		assertEquals(projeto, projetos.get(0));

	}

	@Test
	public void ResponsavelPorProjetosMenorQue10() throws Exception {
		List<Projeto> projetos = joao.getProjetosResponsavel();
		assertEquals(0, projetos.size());
	}


	@Test(expected = Exception.class)
	public void criarEFecharCom12Ocorrencias() throws Exception {
		
		criar11OcorrenciasParaJoao();
	}
	
	private void criar11OcorrenciasParaJoao() throws Exception {
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
	}


	@Test
	public void criarEFecharCom10Ocorrencias() throws Exception {
		criar10OcorrenciasParaJoao();
		assertEquals( 10, joao.getOcorrenciasAbertas().size());
		
		joao.fecharOcorrencia(0);
		assertEquals( 9, joao.getOcorrenciasAbertas().size());
		
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");;
		assertEquals( 10, joao.getOcorrenciasAbertas().size());
	}
	
	private void criar10OcorrenciasParaJoao() throws Exception {
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
		joao.addOcorrenciasResponsavel(Prioridades.ALTA, "resumo");
	}
}
