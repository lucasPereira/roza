import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class testeFinalizaOcorrencia {
	
	@Test
	public void testeFinalizacaoOcorrencia() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.BUG, Prioridade.ALTA);
		
		boolean result = oc.finalizaOcorrencia();
		
		assertEquals(true, result);
	}
	
	@Test
	public void testeOcorrenciaFoiFinalizada() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.BUG, Prioridade.ALTA);	
		oc.finalizaOcorrencia();
		
		Estado result = oc.retornaEstado();
		
		assertEquals(Estado.FECHADO, result);
	}
	
	@Test
	public void testeFinalizacaoOcorrenciaJaFinalizada() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.BUG, Prioridade.ALTA);
		oc.finalizaOcorrencia();
		
		boolean result = oc.finalizaOcorrencia();
		
		assertEquals(false, result);
		
	}
	
	@Test
	public void testeFuncionarioFinalizaOcorrencia() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Funcionario func = new Funcionario("Joao");
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.BUG, Prioridade.ALTA);
		func.adicionaOcorrencia(oc);
		
		boolean result = func.finalizaOcorrencia(oc);
		
		assertEquals(true, result);
	}
	
	@Test
	public void testeVerificaSeOcorrenciaFoiFinalizadaPeloFuncionario() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Funcionario func = new Funcionario("Joao");
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.BUG, Prioridade.ALTA);
		func.adicionaOcorrencia(oc);		
		func.finalizaOcorrencia(oc);
		
		Estado result = oc.retornaEstado();
		
		assertEquals(Estado.FECHADO, result);
	}
	
	@Test
	public void testeFuncionarioFinalizaOcorrenciaJaFinalizada() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Funcionario func = new Funcionario("Joao");
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.BUG, Prioridade.ALTA);
		func.adicionaOcorrencia(oc);		
		func.finalizaOcorrencia(oc);
		
		boolean result = func.finalizaOcorrencia(oc);
		
		assertEquals(false, result);
		
	}
	
	@Test
	public void testeOcorrenciaFinalizadaRemovidaDeFuncionario() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Funcionario func = new Funcionario("Joao");
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.BUG, Prioridade.ALTA);
		func.adicionaOcorrencia(oc);		
		func.finalizaOcorrencia(oc);
		
		List<Ocorrencia> result = func.retornaOcorrencias();
		
		assertEquals(false, result.contains(oc));
	}
	
	@Test
	public void funcionarioFinalizaOcorrenciaQueNaoEDEle() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Funcionario func = new Funcionario("Joao");
		Funcionario func2 = new Funcionario("Maria");
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.BUG, Prioridade.ALTA);		
		func.adicionaOcorrencia(oc);	
		
		boolean result = func2.finalizaOcorrencia(oc);
		
		assertEquals(false, result);
		
	}
	
	@Test
	public void funcionarioNaoAlteraEstadoDeOcorrenciaQueNaoEDele() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Funcionario func = new Funcionario("Joao");
		Funcionario func2 = new Funcionario("Maria");
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.BUG, Prioridade.ALTA);		
		func.adicionaOcorrencia(oc);	
		func2.finalizaOcorrencia(oc);
		
		Estado result = oc.retornaEstado();
		
		assertEquals(Estado.ABERTO, result);
	}
}
