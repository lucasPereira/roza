package tests;

import main.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestOcorrencia {
    Ocorrencia ocorrencia;
    Funcionario funcionario;
    String resumoOcorrencia;

    @Before
    public void setup(){
        int maxOcorrencias = 10;
        resumoOcorrencia = "Problemas ao visualizar email";
        funcionario = new Funcionario("Gilmar Douglas", maxOcorrencias);
    }

    @Test
    public void criarOcorrencia() throws FuncionarioComMaximoOcorrencias {
        ocorrencia = new Ocorrencia(resumoOcorrencia, funcionario, TipoOcorrenciaEnum.TAREFA, PrioridadeOcorrenciaEnum.ALTA);

        assertEquals(resumoOcorrencia, ocorrencia.getResumo());
        assertEquals(funcionario, ocorrencia.getFuncionario());
        assertEquals(TipoOcorrenciaEnum.TAREFA, ocorrencia.getTipo());
        assertEquals(PrioridadeOcorrenciaEnum.ALTA, ocorrencia.getPrioridade());
    }

    @Test
    public void verificarEstadoOcorrenciaAoCriar() throws FuncionarioComMaximoOcorrencias{
        ocorrencia = new Ocorrencia(resumoOcorrencia, funcionario, TipoOcorrenciaEnum.MELHORIA, PrioridadeOcorrenciaEnum.BAIXA);
        assertTrue(ocorrencia.estaAberta());
    }

    @Test
    public void verificarEstadoOcorrenciaAoFechar() throws FuncionarioComMaximoOcorrencias{
        ocorrencia = new Ocorrencia(resumoOcorrencia, funcionario, TipoOcorrenciaEnum.BUG, PrioridadeOcorrenciaEnum.MEDIA);
        ocorrencia.fecharOcorrencia();
        assertFalse(ocorrencia.estaAberta());
    }

    @Test
    public void trocarFuncionario() throws FuncionarioComMaximoOcorrencias{
        Funcionario novoFuncionario = new Funcionario("Thiago Souza", 10);

        ocorrencia = new Ocorrencia(resumoOcorrencia, funcionario, TipoOcorrenciaEnum.MELHORIA, PrioridadeOcorrenciaEnum.BAIXA);
        ocorrencia.trocarFuncionario(novoFuncionario);

        assertEquals(novoFuncionario, ocorrencia.getFuncionario());
    }

    @Test
    public void alterarPrioridadeOcorrencia() throws FuncionarioComMaximoOcorrencias{
        ocorrencia = new Ocorrencia(resumoOcorrencia, funcionario, TipoOcorrenciaEnum.TAREFA, PrioridadeOcorrenciaEnum.ALTA);

        ocorrencia.alterarPrioridade(PrioridadeOcorrenciaEnum.BAIXA);
        assertEquals(PrioridadeOcorrenciaEnum.BAIXA, ocorrencia.getPrioridade());
    }
}
