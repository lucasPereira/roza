package br;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.hamcrest.core.IsEqual;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FuncionarioTest {

    private Funcionario funcionario;
    @BeforeEach
    public void setup()
    {
        // empty empresa
        this.funcionario  = new Funcionario("Bob");
    }
    @Test
    public void shouldHaveUpTo10Ocorrencias() {
        assertThat(funcionario.getNumOcorrencias(), equalTo(0));
    }

    @Test
    public void shouldHaveCreatedName() {
        assertThat(funcionario.getName(), equalTo("Bob"));
    }

    
}
