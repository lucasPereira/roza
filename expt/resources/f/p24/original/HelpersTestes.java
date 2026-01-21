package testes;

import static org.junit.Assert.*;
import org.junit.Test;
import domain.utils.Helpers;
import domain.enums.ESTADO;

public class HelpersTestes {

	@Test
	public void umaStringValidaDeveSerAprovadaPeloHelpers() {
		String str = "Fabio testando string";
		assertEquals(true, Helpers.stringValido(str));
	}

	@Test(expected=IllegalArgumentException.class)
	public void umaStringNulaNaoDeveSerAprovadaPeloHelpers() {
		String str = null;
		Helpers.stringValido(str);
	}

	@Test(expected=IllegalArgumentException.class)
	public void umaStringVaziaNaoDeveSerAprovadaPeloHelpers() {
		String str = "";
		Helpers.stringValido(str);
	}

	@Test(expected=IllegalArgumentException.class)
	public void umaStringDeSomenteEspacosNaoDeveSerAprovadaPeloHelpers() {
		String str = "  ";
		Helpers.stringValido(str);
	}

	@Test
	public void umEnumValidoDeveSerAprovadaPeloHelpers() {
		assertEquals(true, Helpers.enumValido(ESTADO.ABERTA));
	}

	@Test(expected=IllegalArgumentException.class)
	public void umEnumNuloNaoDeveSerAprovadaPeloHelpers() {
		Helpers.enumValido(null);
	}
}
