package br;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OcorrenciaTest {

    private Ocorrencia ocorrencia;

    @BeforeEach
    public void setup()
    {
        // empty empresa
        this.ocorrencia  = new Ocorrencia("Resumo teste", ETIPO_TAREFA.TAREFA, EPRIORIDADE_TAREFA.ALTA);
    }
    @Test
    public void shouldHaveTheCorrectResumo() {
        assertEquals(this.ocorrencia.getResumo(), "Resumo teste");
    }
    @Test
    public void shouldGetCorrectTypeOcorrencia() {
        assertEquals(this.ocorrencia.getTipoTarefa(), ETIPO_TAREFA.TAREFA);
    }
    @Test
    public void shouldGetCorrectKindOfPryority() {
        assertEquals(this.ocorrencia.getPrioridade(), EPRIORIDADE_TAREFA.ALTA);
    }
    @Test
    public void shouldCorrectlyChangePriority() {
        this.ocorrencia.setPrioridade(EPRIORIDADE_TAREFA.MEDIA);
        assertEquals(this.ocorrencia.getPrioridade(), EPRIORIDADE_TAREFA.MEDIA);

    }
    public void shouldCorrectlyChangeType() {
        this.ocorrencia.setTarefa(ETIPO_TAREFA.BUG);
        assertEquals(this.ocorrencia.getTipoTarefa(), ETIPO_TAREFA.BUG);
    }
    
}
