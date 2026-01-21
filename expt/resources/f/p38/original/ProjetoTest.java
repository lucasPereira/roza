package br;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProjetoTest {

    public Projeto projeto;
    @BeforeEach
    public void setup()
    {
        // empty empresa
        this.projeto  = new Projeto();
    }
    @Test
    public void shouldBeEmptyListOcorrencias() {
        assertEquals(projeto.getSizeListaOcorrencias(), 0);
    }
}
