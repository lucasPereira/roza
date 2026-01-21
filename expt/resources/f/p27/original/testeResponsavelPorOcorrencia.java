import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class testeResponsavelPorOcorrencia {
	
	@Test
	public void testeCriaOcorrenciaFuncionario() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Funcionario func = new Funcionario("João");
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.TAREFA, Prioridade.BAIXA); 
		boolean result = func.adicionaOcorrencia(oc);
		
		assertEquals(true, result);
	}
	
	@Test
	public void testeComparaOcorrenciaCriada() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Funcionario func = new Funcionario("Maria");
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.TAREFA, Prioridade.BAIXA); 
		func.adicionaOcorrencia(oc);
		
		List<Ocorrencia> result = func.retornaOcorrencias();
		
		assertEquals(oc, result.get(0));
	}
	
	@Test
	public void testeComparaOcorrenciaCriadaDois() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Funcionario func = new Funcionario("Maria");
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.TAREFA, Prioridade.BAIXA); 
		Ocorrencia oc2 = new Ocorrencia(1, "resumo", Tipo.TAREFA, Prioridade.BAIXA); 
		func.adicionaOcorrencia(oc);
		func.adicionaOcorrencia(oc2);
		
		List<Ocorrencia> result = func.retornaOcorrencias();
		
		assertEquals(2, result.size());
		assertEquals(oc, result.get(0));
		assertEquals(oc2, result.get(1));
	}
	
	
	@Test
	public void testeDezOcorrencias() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Funcionario func = ClasseAuxiliar.preencheOcorrencias(9);
		Ocorrencia oc = new Ocorrencia(10, "resumo", Tipo.TAREFA, Prioridade.BAIXA);
		
		boolean result = func.adicionaOcorrencia(oc);
		
		assertEquals(true, result);
	}
	
	@Test
	public void testeOnzeOcorrencias() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Funcionario func = ClasseAuxiliar.preencheOcorrencias(10);
		Ocorrencia oc = new Ocorrencia(10, "resumo", Tipo.TAREFA, Prioridade.BAIXA);
		
		boolean result = func.adicionaOcorrencia(oc);
		
		assertEquals(false, result);
	}
	
	@Test
	public void testeFuncionarioAtribuido() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Funcionario func = new Funcionario("Joao");
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.TAREFA, Prioridade.BAIXA);
		func.adicionaOcorrencia(oc);
		
		Funcionario result = oc.retornaResponsavel();
		
		assertEquals(func, result);
	}
	
	@Test
	public void testeTentaAtribuirDoisResponsaveis() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Funcionario func = new Funcionario("Joao");
		Funcionario func2 = new Funcionario("Maria");
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.TAREFA, Prioridade.BAIXA);
		func.adicionaOcorrencia(oc);
		
		boolean result = func2.adicionaOcorrencia(oc);
		
		assertEquals(false, result);
	}
	
	@Test
	public void testePrimeiroResponsavelAtribuido() throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Funcionario func = new Funcionario("Joao");
		Funcionario func2 = new Funcionario("Maria");
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.TAREFA, Prioridade.BAIXA);
		func.adicionaOcorrencia(oc);		
		func2.adicionaOcorrencia(oc);
		
		Funcionario result = oc.retornaResponsavel();
		
		assertEquals(func, result);
		
	}
}
