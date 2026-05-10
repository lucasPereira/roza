import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class testeOcorrencia {
	
	@Test
	public void testeChaveUm() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		int chave = 1;
		Ocorrencia oc = new Ocorrencia(chave, "resumo", Tipo.BUG, Prioridade.BAIXA);
		
		int resultado = oc.retornaChave();
		
		assertEquals(1, resultado);
	}
	
	@Test
	public void testeChaveDois() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		int chave = 2;
		Ocorrencia oc = new Ocorrencia(chave, "resumo", Tipo.BUG, Prioridade.BAIXA);
		
		int resultado = oc.retornaChave();
		
		assertEquals(2, resultado);
	}
	
	@Test
	public void testaChaveZero() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		int chave = 0;
		Ocorrencia oc = new Ocorrencia(chave, "resumo", Tipo.BUG, Prioridade.BAIXA);
		
		int resultado = oc.retornaChave();
		
		assertEquals(0, resultado);
	}
	
	@Test(expected=ExceptionInvalidKey.class)
	public void testeChaveNegativa() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		int chave = -1;
		
		Ocorrencia oc = new Ocorrencia(chave, "resumo", Tipo.BUG, Prioridade.BAIXA);
	}
	
	@Test
	public void testeResumoOcorrencia() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		String resumo = "teste";	
		Ocorrencia oc = new Ocorrencia(0, resumo, Tipo.BUG, Prioridade.BAIXA);
		
		String resultado = oc.retornaResumo();
		
		assertEquals("teste", resultado);
		
	}
	
	@Test
	public void testeResumoDiferenteOcorrencia() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		String resumo = "resumo";
		Ocorrencia oc = new Ocorrencia(0, resumo, Tipo.BUG, Prioridade.BAIXA);
		
		String resultado = oc.retornaResumo();
		
		assertEquals("resumo", resultado);
	}
	
	@Test
	public void testeTipoOcorrencia() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.BUG, Prioridade.BAIXA);
		
		Tipo resultado = oc.retornaTipo();
		
		assertEquals(Tipo.BUG, resultado);
	}
	
	
	@Test(expected = ExceptionTipoNull.class)
	public void testeTipoOcorrenciaNull() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		
		Ocorrencia oc = new Ocorrencia(0, "resumo", null, Prioridade.BAIXA);		
		
	}
	
	@Test
	public void testeCriacaoEstado() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.MELHORIA, Prioridade.BAIXA);
		
		Estado resultado = oc.retornaEstado();
		
		assertEquals(Estado.ABERTO, resultado);
	}
	
	@Test
	public void testeCriacaoPrioridade() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.BUG, Prioridade.BAIXA);
		
		Prioridade resultado = oc.retornaPrioridade();
		
		assertEquals(Prioridade.BAIXA, resultado);
	}
	
	@Test(expected = ExceptionPrioridadeNull.class)
	public void testePrioridadeOcorrenciaNull() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.BUG, null);		
		
	}
	
	@Test
	public void trocaPrioridade() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.BUG, Prioridade.ALTA);
		
		oc.trocaPrioridade(Prioridade.MEDIA);
		
		assertEquals(Prioridade.MEDIA, oc.retornaPrioridade());
	}
	
	@Test
	public void trocaPrioridadeDiferente() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.BUG, Prioridade.ALTA);
		
		oc.trocaPrioridade(Prioridade.BAIXA);
		
		assertEquals(Prioridade.BAIXA, oc.retornaPrioridade());
	}
	
	@Test(expected = ExceptionPrioridadeNull.class)
	public void testeTrocaPrioridadeOcorrenciaNull() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.BUG, Prioridade.ALTA);
		
		oc.trocaPrioridade(null);
		
	}

}
