import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OcorrenciaTest {

    private Ocorrencia ocorrencia;

    @Before
    public void setup() {
        this.ocorrencia = new Ocorrencia();
    }

    @Test
    public void ocorrenciaSempreCriadaNaoCompletada() {
        Ocorrencia ocorrencia = new Ocorrencia();
        assertFalse(ocorrencia.isCompletada());

        Ocorrencia ocorrencia2 = new Ocorrencia(0L, "", new Funcionario(), TipoOcorrencia.BUG, Prioridade.MEDIA);
        assertFalse(ocorrencia2.isCompletada());
    }

    @Test
    public void nadaAconteceAoFecharOcorrenciaDuasVezes() {
        this.ocorrencia.completar();
        assertTrue(this.ocorrencia.isCompletada());
        this.ocorrencia.completar();
        assertTrue(this.ocorrencia.isCompletada());
    }

    @Test(expected = Exception.class)
    public void naoDevePermitirAlterarResponsavelDepoisDeCompletada() throws Exception {
        this.ocorrencia.completar();
        this.ocorrencia.alterarResponsavel(null);
    }

    @Test
    public void deveAlterarResponsavel() throws Exception {
        Funcionario funcionario = new Funcionario();
        this.ocorrencia.alterarResponsavel(funcionario);

        assertEquals(funcionario, this.ocorrencia.getResponsavel());
    }

    @Test(expected = Exception.class)
    public void naoDevePermitirAlterarPrioridadeDepoisDeCompletada() throws Exception {
        this.ocorrencia.completar();
        this.ocorrencia.alterarPrioridade(null);
    }

    @Test
    public void deveAlterarPrioridade() throws Exception {
        Prioridade prioridade = Prioridade.MEDIA;
        this.ocorrencia.alterarPrioridade(prioridade);

        assertEquals(prioridade, this.ocorrencia.getPrioridade());
    }
}