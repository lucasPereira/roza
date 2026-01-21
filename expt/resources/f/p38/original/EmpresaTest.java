package br;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class EmpresaTest {
    
    public Empresa empresa;

    public void create10OcorrenciasOnFuncionario(Funcionario funcionario) {
        for (int i = 0; i != 10; i++) {
            assertThat(funcionario.getNumOcorrencias(), equalTo(i));
            var ocorrenciaBugPrioridadeMedia = new Ocorrencia("Resumao"+i, ETIPO_TAREFA.BUG, EPRIORIDADE_TAREFA.MEDIA);
            var projeto = new Projeto();
            this.empresa.addOcorrencia(funcionario, ocorrenciaBugPrioridadeMedia, projeto);
        }
    }

    @BeforeEach
    public void setup()
    {
        // empty empresa
        this.empresa  = new Empresa();
    }
    
    @Test
    public void shouldBeCorrectClass() {
        assertEquals(empresa.getClass(), Empresa.class);
    }

    @Test
    public void shouldBeEmptyListFuncionarios() {
        assertEquals(empresa.getListaFuncionariosSize(), 0);
    }

    @Test
    public void shouldBeEmptyListProjetos() {
        assertEquals(empresa.getListaProjetosSize(), 0);
    }

    @Test
    public void shouldCorrectlyAddFuncionario() {
        this.empresa.addFuncionario(new Funcionario("Ana"));
        assertThat(empresa.getListaFuncionariosSize(), equalTo(1));
    }

    @Test
    public void shouldCorrectlyAddProjeto() {
        this.empresa.addProjeto(new Projeto());
        assertThat(this.empresa.getListaProjetosSize(), equalTo(1));
    }

    @Test
    public void shouldGetAddedOcorrencia() {
        var tom = new Funcionario("Tom");
        var ocorrenciaBugPrioridadeMedia = new Ocorrencia("Resumao", ETIPO_TAREFA.BUG, EPRIORIDADE_TAREFA.MEDIA);
        var projeto = new Projeto();
        this.empresa.addOcorrencia(tom, ocorrenciaBugPrioridadeMedia, projeto);
        assertThat(this.empresa.getListaOcorrenciasSize(), equalTo(1));
        assertThat(this.empresa.getOcorrenciaOfFuncionario(tom), equalTo(ocorrenciaBugPrioridadeMedia));
        assertThat(projeto.getSizeListaOcorrencias(), equalTo(1));
    }
    @Test
    public void addMoreThan10OcorrenciasToFuncionario() {
        var tom = new Funcionario("Tom");
        this.create10OcorrenciasOnFuncionario(tom);
        var ocorrenciaBugPrioridadeAlta = new Ocorrencia("Resumao"+10, ETIPO_TAREFA.BUG, EPRIORIDADE_TAREFA.ALTA);
        var projeto = new Projeto();
        this.empresa.addOcorrencia(tom, ocorrenciaBugPrioridadeAlta, projeto);
        assertTrue(tom.getNumOcorrencias() == 10);
    }

    public void testChangeEstadoTarefa() {
        var tom = new Funcionario("Tom");
        var ocorrenciaBugPrioridadeMedia = new Ocorrencia("Resumao", ETIPO_TAREFA.BUG, EPRIORIDADE_TAREFA.MEDIA);
        var projeto = new Projeto();
        this.empresa.addOcorrencia(tom, ocorrenciaBugPrioridadeMedia, projeto);
        this.empresa.closeTarefa(ocorrenciaBugPrioridadeMedia);
        assert(ocorrenciaBugPrioridadeMedia.isFechado() == true);
    }

    public void testCannotChangeAtributesTarefaAfterClosingIt() {
        var tom = new Funcionario("Tom");
        var ocorrenciaBugPrioridadeMedia = new Ocorrencia("Resumao", ETIPO_TAREFA.BUG, EPRIORIDADE_TAREFA.MEDIA);
        var projeto = new Projeto();
        this.empresa.addOcorrencia(tom, ocorrenciaBugPrioridadeMedia, projeto);
        this.empresa.closeTarefa(ocorrenciaBugPrioridadeMedia);
        assert(ocorrenciaBugPrioridadeMedia.isFechado() == true);
        ocorrenciaBugPrioridadeMedia.setTarefa(ETIPO_TAREFA.MELHORIA);
        assertEquals(ocorrenciaBugPrioridadeMedia.getTipoTarefa(), ETIPO_TAREFA.BUG);
    }

}
