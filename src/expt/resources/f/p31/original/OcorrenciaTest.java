package tests;

import domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OcorrenciaTest {

    Empresa empresa;
    Projeto projeto;
    Funcionario funcionario;
    @Before
    public void setup(){
        empresa = new Empresa("Empresa Spotify");
        projeto = new Projeto("Projeto Primmer");
        funcionario = new Funcionario("Maria");

        empresa.cadastrarProjeto(projeto);
    }

    @Test
    public void alterarResponsavelQuandoOcorrenciaEstaAbertaTest(){
        Funcionario joao= new Funcionario("João");
        Ocorrencia ocorrencia = new Ocorrencia("Titulo", "Descrição", EnumTipoOcorencia.BUG, EnumPrioridadeOcorrencia.BAIXA,funcionario);

        String resultado = ocorrencia.alterarResponsavel(joao);

        assertThat(ocorrencia.obterFuncionarioResponsavel().obterId(), equalTo(joao.obterId()));
        assertThat(ocorrencia.obterFuncionarioResponsavel().obterNome(), equalTo(joao.obterNome()));
        assertThat(resultado, equalTo("Funcionario responsavel alterado com sucesso."));
    }

    @Test
    public void alterarResponsavelQuandoOcorrenciaEstaAFechadoTest(){

        Ocorrencia ocorrencia = new Ocorrencia("Titulo", "Descrição", EnumTipoOcorencia.BUG, EnumPrioridadeOcorrencia.BAIXA,funcionario);
        ocorrencia.alterarSituacaoOcorrencia(EnumSituacaoOcorrencia.FECHADO);
        String resultado = ocorrencia.alterarResponsavel(new Funcionario("João"));

        assertThat(ocorrencia.obterFuncionarioResponsavel().obterId(), equalTo(funcionario.obterId()));
        assertThat(ocorrencia.obterFuncionarioResponsavel().obterNome(), equalTo(funcionario.obterNome()));
        assertThat(resultado, equalTo("Não é possivel alterar o funcionario responsavel quando a ocorrencia esta fechada."));
    }

    @Test
    public void alterarPrioridadeQuandoOcorrenciaEstaAbertaTest(){
        Ocorrencia ocorrencia = new Ocorrencia("Titulo", "Descrição", EnumTipoOcorencia.BUG, EnumPrioridadeOcorrencia.BAIXA,funcionario);

        String resultado = ocorrencia.alterarPrioridadeOcorrencia(EnumPrioridadeOcorrencia.ALTA);

        assertThat(ocorrencia.obterPrioridadeOcorrencia(), equalTo(EnumPrioridadeOcorrencia.ALTA));
        assertThat(resultado, equalTo("Prioridade da ocorrencia alterada com sucesso."));
    }

    @Test
    public void alterarPrioridadeQuandoOcorrenciaEstaAFechadoTest(){

        Ocorrencia ocorrencia = new Ocorrencia("Titulo", "Descrição",  EnumTipoOcorencia.BUG, EnumPrioridadeOcorrencia.BAIXA,funcionario);
        ocorrencia.alterarSituacaoOcorrencia(EnumSituacaoOcorrencia.FECHADO);
        String resultado = ocorrencia.alterarPrioridadeOcorrencia(EnumPrioridadeOcorrencia.ALTA);

        assertThat(ocorrencia.obterPrioridadeOcorrencia(), equalTo(EnumPrioridadeOcorrencia.BAIXA));
        assertThat(resultado, equalTo("Não é possivel alterar prioridade da ocorrencia quando a ocorrencia esta fechada."));
    }
    @Test
    public void criarOcorrencioDoTipoTarefaTest(){
        Ocorrencia ocorrencia = new Ocorrencia("Titulo", "Descrição",  EnumTipoOcorencia.TAREFA, EnumPrioridadeOcorrencia.BAIXA, funcionario);

        assertThat(ocorrencia.obterTipoOcorencia(), equalTo(EnumTipoOcorencia.TAREFA));
    }
    @Test
    public void criarOcorrencioDoTipoBugTest(){
        Ocorrencia ocorrencia = new Ocorrencia("Titulo", "Descrição",  EnumTipoOcorencia.BUG, EnumPrioridadeOcorrencia.BAIXA, funcionario);

        assertThat(ocorrencia.obterTipoOcorencia(), equalTo(EnumTipoOcorencia.BUG));
    }
    @Test
    public void criarOcorrencioDoTipoMelhoriaTest(){
        Ocorrencia ocorrencia = new Ocorrencia("Titulo", "Descrição",  EnumTipoOcorencia.MELHORIA, EnumPrioridadeOcorrencia.BAIXA, funcionario);

        assertThat(ocorrencia.obterTipoOcorencia(), equalTo(EnumTipoOcorencia.MELHORIA));
    }

    @Test
    public void criarOcorrencioComPrioridadeAltaTest(){
        Ocorrencia ocorrencia = new Ocorrencia("Titulo", "Descrição",  EnumTipoOcorencia.MELHORIA, EnumPrioridadeOcorrencia.ALTA, funcionario);

        assertThat(ocorrencia.obterPrioridadeOcorrencia(), equalTo(EnumPrioridadeOcorrencia.ALTA));
    }

    @Test
    public void criarOcorrencioComPrioridadeMediaTest(){
        Ocorrencia ocorrencia = new Ocorrencia("Titulo", "Descrição",  EnumTipoOcorencia.MELHORIA, EnumPrioridadeOcorrencia.MEDIA, funcionario);

        assertThat(ocorrencia.obterPrioridadeOcorrencia(), equalTo(EnumPrioridadeOcorrencia.MEDIA));
    }

    @Test
    public void criarOcorrencioComPrioridadeBaixaTest(){
        Ocorrencia ocorrencia = new Ocorrencia("Titulo", "Descrição",  EnumTipoOcorencia.MELHORIA, EnumPrioridadeOcorrencia.BAIXA, funcionario);

        assertThat(ocorrencia.obterPrioridadeOcorrencia(), equalTo(EnumPrioridadeOcorrencia.BAIXA));
    }

    @Test
    public void criarOcorrencioComSituacaoBaixaTest(){
        Ocorrencia ocorrencia = new Ocorrencia("Titulo", "Descrição",  EnumTipoOcorencia.MELHORIA, EnumPrioridadeOcorrencia.BAIXA, funcionario);

        assertThat(ocorrencia.obterSituacaoOcorrencia(), equalTo(EnumSituacaoOcorrencia.ABERTO));
    }

    @Test
    public void verificarSeOcorrencioTemDescricaoTest(){
        Ocorrencia ocorrencia = new Ocorrencia("Titulo", "Descrição",  EnumTipoOcorencia.MELHORIA, EnumPrioridadeOcorrencia.BAIXA, funcionario);

        assertThat(ocorrencia.obterDescricao(), equalTo("Descrição"));
    }

    @Test
    public void verificarSeOcorrencioTemResponsavelTest(){
        Ocorrencia ocorrencia = new Ocorrencia("Titulo", "Descrição",  EnumTipoOcorencia.MELHORIA, EnumPrioridadeOcorrencia.BAIXA, funcionario);

        assertThat(ocorrencia.obterFuncionarioResponsavel(), equalTo(funcionario));
        assertThat(ocorrencia.obterFuncionarioResponsavel().obterNome(), equalTo(funcionario.obterNome()));
        assertThat(ocorrencia.obterFuncionarioResponsavel().obterId(), equalTo(funcionario.obterId()));
    }
    @Test
    public void verificarIdDaOcorrenciaSemEstarAtribuidaAoObjetoTest(){
        Ocorrencia ocorrencia = new Ocorrencia("Titulo", "Descrição",  EnumTipoOcorencia.MELHORIA, EnumPrioridadeOcorrencia.BAIXA, funcionario);
        assertThat(ocorrencia.obterId(), equalTo(0L));
    }
    @Test
    public void verificarSeOcorrencioTemIdTest(){
        Ocorrencia ocorrencia = new Ocorrencia("Titulo", "Descrição",  EnumTipoOcorencia.MELHORIA, EnumPrioridadeOcorrencia.BAIXA, funcionario);
        projeto.cadastrarOcorrencia(ocorrencia);
        assertThat(ocorrencia.obterId(), equalTo(1L));
    }

    @Test
    public void verificarSeOcorrencioTemIdUnicoTest(){
        projeto.cadastrarOcorrencia(new Ocorrencia("Titulo", "Descrição",  EnumTipoOcorencia.MELHORIA, EnumPrioridadeOcorrencia.BAIXA, funcionario));
        projeto.cadastrarOcorrencia(new Ocorrencia("Titulo", "Descrição",  EnumTipoOcorencia.MELHORIA, EnumPrioridadeOcorrencia.BAIXA, funcionario));

        assertThat(projeto.obterOcorrencias().get(0).obterId(), equalTo(1L));
        assertThat(projeto.obterOcorrencias().get(1).obterId(), equalTo(2L));
    }

    @Test
    public void verificarSeFuncionarioPodeParticiparDeMaisUmProjetoTest(){
        Projeto projetoY = new Projeto("Projeto Y");
        empresa.cadastrarProjeto(projetoY);

        projeto.cadastrarOcorrencia(new Ocorrencia("Titulo", "Descrição",  EnumTipoOcorencia.MELHORIA, EnumPrioridadeOcorrencia.BAIXA, funcionario));
        projetoY.cadastrarOcorrencia(new Ocorrencia("Titulo", "Descrição",  EnumTipoOcorencia.MELHORIA, EnumPrioridadeOcorrencia.BAIXA, funcionario));

        ArrayList<RlFuncionarioOcorrencia> rlFuncionarioOcorrencias = funcionario.obterRlFuncionarioOcorrencias();
        assertThat(rlFuncionarioOcorrencias.get(0).obterIdProjeto(), equalTo(projeto.obterId()));
        assertThat(rlFuncionarioOcorrencias.get(1).obterIdProjeto(), equalTo(projetoY.obterId()));
    }
}
