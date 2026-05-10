package testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import modelo.Funcionario;

public class TesteFuncionario 
{
	private Funcionario funcionario;
	
	@Before
	public void setup()
	{
		this.funcionario = new Funcionario("Betinho");
	}
	
	@Test
	public void testeNomeFuncionario()
	{
		String nomeFuncionario = "Betinho";
		assertEquals(nomeFuncionario, this.funcionario.getNome());
	}
	
	@Test
	public void testeNomeErradoFuncionario()
	{
		String nomeFuncionarioErrado = "Marcos";
		assertNotEquals(nomeFuncionarioErrado, this.funcionario.getNome());
	}
	
	@Test
	public void testeOcorrenciaFuncionarioZero()
	{
		int numOcorrenciasEsperada = 0;
		int numOcorrenciasFuncionario = this.funcionario.getQuantiaOcorrencias();
		assertEquals(numOcorrenciasEsperada, numOcorrenciasFuncionario);
	}
	
	@Test
	public void testeOcorrenciasFuncionarioMaiorQueZeroMasMenorDoQueLimite()
	{
		int numOcorrenciasEsperada = 1;
		this.funcionario.adicioneOcorrencia();
		int numOcorrenciasFuncionario = this.funcionario.getQuantiaOcorrencias();
		assertEquals(numOcorrenciasEsperada, numOcorrenciasFuncionario);
	}
	
	@Test
	public void testeOcorrenciasFuncionarioMaiorQueZeroMasMaiorDoQueLimite()
	{
		int numOcorrenciasEsperada = 10;
		for (int i = 0; i < 11; i++)
			this.funcionario.adicioneOcorrencia();
		int numOcorrenciasFuncionario = this.funcionario.getQuantiaOcorrencias();
		assertEquals(numOcorrenciasEsperada, numOcorrenciasFuncionario);
	}
	
}
