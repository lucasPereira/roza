package testes;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import domain.Empresa;
import domain.Projeto;

public class TesteProjeto {
	
	Empresa empresa;
	
	// sugestão do lucas: utilizar @Before nos testes
	@Before
	public void setup() {
		empresa = new Empresa("Test Company");
	}

	// esse teste foi comentado pois o construtor de produto foi alterado para 'protected'
//	@Test
//	public void criaProjeto() {
//		Projeto projeto = new Projeto();
//		assertNotNull(projeto);
//	}
	
	@Test
	public void criaProjetoPorEmpresa() {
		Projeto projeto = empresa.criarProjeto();
		
		List<Projeto> projetos = empresa.getProjetos();
		assertEquals(1, projetos.size());
		assertEquals(projeto, empresa.getProjeto(0));
		
		// sugestão do lucas: testar as coleções vazias quando entidade é instanciada
		assertEquals(0, projeto.getOcorrencias().size());
	}
	
	@Test
	public void criaDoisProjetosPorEmpresa() {
		
		Projeto projeto1 = empresa.criarProjeto();
		Projeto projeto2 = empresa.criarProjeto();
		
		List<Projeto> projetos = empresa.getProjetos();
		assertEquals(2, projetos.size());
		assertEquals(projeto1, empresa.getProjeto(0));
		assertEquals(projeto2, empresa.getProjeto(1));
	}
}
