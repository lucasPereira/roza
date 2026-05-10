package testes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelos.Empresa;
import modelos.Projeto;

public class TesteProjeto {
	private Empresa empresa;

	@BeforeEach
	public void configurar() {
		empresa = new Empresa("Empresa do Joao");
	}
	
	@Test
	public void testeCriaProjetoX() {
		empresa.criaProjeto("Projeto X");
		assertEquals("Projeto X", empresa.obterProjeto(0));
	}
	
	@Test
	public void testeCriaProjetoY() {
		empresa.criaProjeto("Projeto Y");
		assertEquals("Projeto Y", empresa.obterProjeto(0));
	}
	
	@Test
	public void testeCriaProjetoXeY() {
		Projeto projetoX = empresa.criaProjeto("Projeto X");
		Projeto projetoY = empresa.criaProjeto("Projeto Y");
		List<Projeto> lista = new ArrayList<>();
		lista.add(projetoX);
		lista.add(projetoY);
		
		assertEquals("Projeto X", empresa.obterProjeto(0));
		assertEquals("Projeto Y", empresa.obterProjeto(1));
		assertEquals(lista, empresa.obterProjetos());
		assertEquals(2, empresa.obterProjetos().size());
	}
	
}
