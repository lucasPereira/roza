package Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Main.Empresa;
import Main.Funcionario;
import Main.Ocorrencia;
import Main.Projeto;

public class TestOcorrencia {

	Empresa empresa;
	Funcionario f;
	Projeto p;
	@BeforeEach
	void beforeEach() throws Exception {
		empresa = Empresa.Instance();
		empresa.Reset();
		f = new Funcionario("Godofredo", "0000001");
		empresa.addFuncionario(f);
		p = new Projeto("Tibia 2", "001",f.id());
		empresa.addProjeto(p);
		
	}
	
	@Test
	void testAdicionarOcorrenciaEmProjeto() throws Exception {
		Ocorrencia o = new Ocorrencia("0000001","001", Ocorrencia.Tipo.Melhoria, Ocorrencia.Prioridade.Alta, "000001");
		assertTrue(p.addOcorrencia(o));
		assertEquals(p.ocorrencias().size(), 1);
		assertEquals(empresa.contarOcorrencias("0000001"), 1);
	}
	
	@Test
	void testAdicionarOcorrenciaAUmProjetoInexistente() throws Exception{
		Ocorrencia o = new Ocorrencia("0000001","002", Ocorrencia.Tipo.Melhoria, Ocorrencia.Prioridade.Alta, "000001");
		assertThrows(Exception.class, ()->{p.addOcorrencia(o);});
	}
	
	@Test
	void testAdicionarOcorrenciaDeMesmoID() throws Exception {
		Ocorrencia o = new Ocorrencia("0000001","001", Ocorrencia.Tipo.Melhoria, Ocorrencia.Prioridade.Alta, "000001");
		Ocorrencia o1 = new Ocorrencia("0000001", "001", Ocorrencia.Tipo.Melhoria, Ocorrencia.Prioridade.Alta, "000001");
		assertTrue(p.addOcorrencia(o));
		assertThrows(Exception.class, ()->{p.addOcorrencia(o1);});
	}
	
	@Test
	void testAdicionarOnzeOcorrencias() throws Exception {
		for(int i = 0; i < 11; i++) {
			Ocorrencia o = new Ocorrencia("0000001","001", Ocorrencia.Tipo.Melhoria, Ocorrencia.Prioridade.Alta, "0000"+ (i < 10? "0" + i :  i));
			if(i < 10) {
				assertTrue(p.addOcorrencia(o));
			} else {
				assertFalse(p.addOcorrencia(o));
			}
		}
	}
	
	@Test
	void testAdicionarEModificarOcorrênciaAberta() throws Exception {
		Ocorrencia o = new Ocorrencia("0000001","001", Ocorrencia.Tipo.Melhoria, Ocorrencia.Prioridade.Alta, "000001");
		o.alterarPrioridade(Ocorrencia.Prioridade.Baixa);
		assertEquals(Ocorrencia.Prioridade.Baixa, o.prioridade());
		o.fechar("0000001");
		assertEquals(Ocorrencia.Estado.Fechado, o.estado());
	}
	
	@Test
	void testAdicionarEModificarOcorrênciaFechada() throws Exception {
		Ocorrencia o = new Ocorrencia("0000001","001", Ocorrencia.Tipo.Melhoria, Ocorrencia.Prioridade.Alta, "000001");
		o.fechar("0000001");
		assertEquals(Ocorrencia.Estado.Fechado, o.estado());
		
		assertThrows(Exception.class, ()->{o.alterarPrioridade(Ocorrencia.Prioridade.Baixa);});
		
	}
	
}
