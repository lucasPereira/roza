package testes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import modelo.Ocorrencia;
import modelo.TipoOcorrencia;

public class TesteOcorrencia 
{
	private String descricaoOcorrencia;
	private TipoOcorrencia tipoOcorrencia;
	private Ocorrencia ocorrencia;
	
	@Before
	public void setup()
	{
		this.descricaoOcorrencia = "Arrumar o método K.";
		this.tipoOcorrencia = TipoOcorrencia.BUG;
		this.ocorrencia = new Ocorrencia(this.descricaoOcorrencia, this.tipoOcorrencia);
	}
	
	@Test
	public void testeDescricaoOcorrencia()
	{
		String resultadoEsperado = "Arrumar o método K.";
		assertEquals(resultadoEsperado, this.ocorrencia.getDescricao());
	}
	
	@Test
	public void testeTipoOcorrencia()
	{
		TipoOcorrencia resultadoEsperado = TipoOcorrencia.BUG;
		assertEquals(resultadoEsperado, this.ocorrencia.getTipoOcorrencia());
	}
}
