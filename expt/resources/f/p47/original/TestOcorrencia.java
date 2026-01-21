package tdd.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import tdd.PrioridadeOcorrencia;
import tdd.TipoOcorrencia;
import tdd.model.Empresa;
import tdd.model.Funcionario;
import tdd.model.Ocorrencia;
import tdd.model.Projeto;

public class TestOcorrencia {
	
	private Projeto projeto;
	private Funcionario funcionario;
	private Funcionario funcionario1;
	private Ocorrencia ocorrencia;
	
	@Before
	public void setup() {
		Empresa empresa = new Empresa("Google");
		this.projeto = empresa.createProjeto("projeto1");
		this.funcionario = new Funcionario("Pedro");
		this.funcionario1 = new Funcionario("Joao");
		this.ocorrencia = this.projeto.createOcorrencia(this.funcionario, "Resolver bug",
				PrioridadeOcorrencia.ALTA, TipoOcorrencia.BUG);
	}
	
	@Test
	public void testCloseWhenOpen() {
		this.ocorrencia.close();
		
		assertFalse(this.ocorrencia.isOpen());
		assertFalse(this.funcionario.getOcorrencias().contains(this.ocorrencia));
	}
	
	@Test
	public void testCloseWhenClosed() {
		this.ocorrencia.close();
		
		this.ocorrencia.close();
		
		assertFalse(this.ocorrencia.isOpen());
		assertFalse(this.funcionario.getOcorrencias().contains(this.ocorrencia));
	}
	
	@Test
	public void testSetResponsavel() {
		this.ocorrencia.setResponsavel(funcionario1);
		
		assertFalse(this.funcionario.getOcorrencias().contains(this.ocorrencia));
		assertEquals(this.ocorrencia.getResponsavel(), this.funcionario1);
		assertTrue(this.funcionario1.getOcorrencias().contains(this.ocorrencia));
	}
	
	@Test(expected = RuntimeException.class)
	public void testSetResponsavelWhenClosed() {
		this.ocorrencia.close();
		this.ocorrencia.setResponsavel(funcionario1);
		
	}

}
