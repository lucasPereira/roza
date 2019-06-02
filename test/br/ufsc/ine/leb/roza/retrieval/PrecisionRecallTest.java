package br.ufsc.ine.leb.roza.retrieval;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrecisionRecallTest {

	private BigDecimal oneOfTwo;
	private BigDecimal twoOfThree;
	private BigDecimal threeOfFour;
	private BigDecimal fourOfFive;
	private BigDecimal fiveOfSix;
	private BigDecimal sixOfSeven;
	private BigDecimal sevenOfEigth;
	private BigDecimal eigthOfNine;
	private BigDecimal nineOfTen;
	private BigDecimal threeOfFive;
	private BigDecimal fourOfSeven;
	private BigDecimal fiveOfNine;
	private BigDecimal oneOfSix;
	private BigDecimal twoOfSeven;
	private BigDecimal threeOfEigth;
	private BigDecimal fourOfNine;

	private RecallLevel zeroPercent;
	private RecallLevel tenPercent;
	private RecallLevel twentyPercent;
	private RecallLevel thrirtyPercent;
	private RecallLevel fortyPercent;
	private RecallLevel fiftyPercent;
	private RecallLevel sixtyPercent;
	private RecallLevel seventyPercent;
	private RecallLevel eightyPercent;
	private RecallLevel ninetyPercent;
	private RecallLevel oneHundredPercent;

	@BeforeEach
	public void bigDecimals() {
		oneOfTwo = new BigDecimal(1).divide(new BigDecimal(2), MathContext.DECIMAL32);
		twoOfThree = new BigDecimal(2).divide(new BigDecimal(3), MathContext.DECIMAL32);
		threeOfFour = new BigDecimal(3).divide(new BigDecimal(4), MathContext.DECIMAL32);
		fourOfFive = new BigDecimal(4).divide(new BigDecimal(5), MathContext.DECIMAL32);
		fiveOfSix = new BigDecimal(5).divide(new BigDecimal(6), MathContext.DECIMAL32);
		sixOfSeven = new BigDecimal(6).divide(new BigDecimal(7), MathContext.DECIMAL32);
		sevenOfEigth = new BigDecimal(7).divide(new BigDecimal(8), MathContext.DECIMAL32);
		eigthOfNine = new BigDecimal(8).divide(new BigDecimal(9), MathContext.DECIMAL32);
		nineOfTen = new BigDecimal(9).divide(new BigDecimal(10), MathContext.DECIMAL32);
		threeOfFive = new BigDecimal(3).divide(new BigDecimal(5), MathContext.DECIMAL32);
		fourOfSeven = new BigDecimal(4).divide(new BigDecimal(7), MathContext.DECIMAL32);
		fiveOfNine = new BigDecimal(5).divide(new BigDecimal(9), MathContext.DECIMAL32);
		oneOfSix = new BigDecimal(1).divide(new BigDecimal(6), MathContext.DECIMAL32);
		twoOfSeven = new BigDecimal(2).divide(new BigDecimal(7), MathContext.DECIMAL32);
		threeOfEigth = new BigDecimal(3).divide(new BigDecimal(8), MathContext.DECIMAL32);
		fourOfNine = new BigDecimal(4).divide(new BigDecimal(9), MathContext.DECIMAL32);
	}

	@BeforeEach
	public void recallLevels() {
		zeroPercent = new RecallLevel(0);
		tenPercent = new RecallLevel(1);
		twentyPercent = new RecallLevel(2);
		thrirtyPercent = new RecallLevel(3);
		fortyPercent = new RecallLevel(4);
		fiftyPercent = new RecallLevel(5);
		sixtyPercent = new RecallLevel(6);
		seventyPercent = new RecallLevel(7);
		eightyPercent = new RecallLevel(8);
		ninetyPercent = new RecallLevel(9);
		oneHundredPercent = new RecallLevel(10);
	}

	@Test
	void rankingEqualsRelevantSet() throws Exception {
		List<Character> ranking = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
		List<Character> relevantSet = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
		PrecisionRecall<Character> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(zeroPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(tenPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(twentyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(thrirtyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(fortyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(fiftyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(sixtyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(seventyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(eightyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(ninetyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(oneHundredPercent));
	}

	@Test
	void missingFirstElement() throws Exception {
		List<Character> ranking = Arrays.asList('x', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
		List<Character> relevantSet = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
		PrecisionRecall<Character> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(zeroPercent));
		assertEquals(oneOfTwo, precisionRecall.precisionAtRecallLevel(tenPercent));
		assertEquals(twoOfThree, precisionRecall.precisionAtRecallLevel(twentyPercent));
		assertEquals(threeOfFour, precisionRecall.precisionAtRecallLevel(thrirtyPercent));
		assertEquals(fourOfFive, precisionRecall.precisionAtRecallLevel(fortyPercent));
		assertEquals(fiveOfSix, precisionRecall.precisionAtRecallLevel(fiftyPercent));
		assertEquals(sixOfSeven, precisionRecall.precisionAtRecallLevel(sixtyPercent));
		assertEquals(sevenOfEigth, precisionRecall.precisionAtRecallLevel(seventyPercent));
		assertEquals(eigthOfNine, precisionRecall.precisionAtRecallLevel(eightyPercent));
		assertEquals(nineOfTen, precisionRecall.precisionAtRecallLevel(ninetyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(oneHundredPercent));
	}

	@Test
	void missingLastElement() throws Exception {
		List<Character> ranking = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'x');
		List<Character> relevantSet = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
		PrecisionRecall<Character> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(zeroPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(tenPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(twentyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(thrirtyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(fortyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(fiftyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(sixtyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(seventyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(eightyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(ninetyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(oneHundredPercent));
	}

	@Test
	void oneHitOneMiss() throws Exception {
		List<Character> ranking = Arrays.asList('a', 'x', 'c', 'x', 'e', 'x', 'g', 'x', 'i', 'x');
		List<Character> relevantSet = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
		PrecisionRecall<Character> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(zeroPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(tenPercent));
		assertEquals(twoOfThree, precisionRecall.precisionAtRecallLevel(twentyPercent));
		assertEquals(threeOfFive, precisionRecall.precisionAtRecallLevel(thrirtyPercent));
		assertEquals(fourOfSeven, precisionRecall.precisionAtRecallLevel(fortyPercent));
		assertEquals(fiveOfNine, precisionRecall.precisionAtRecallLevel(fiftyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(sixtyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(seventyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(eightyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(ninetyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(oneHundredPercent));
	}

	@Test
	void oneMissOneHit() throws Exception {
		List<Character> ranking = Arrays.asList('x', 'b', 'x', 'd', 'x', 'f', 'x', 'h', 'x', 'j');
		List<Character> relevantSet = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
		PrecisionRecall<Character> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(zeroPercent));
		assertEquals(oneOfTwo, precisionRecall.precisionAtRecallLevel(tenPercent));
		assertEquals(oneOfTwo, precisionRecall.precisionAtRecallLevel(twentyPercent));
		assertEquals(oneOfTwo, precisionRecall.precisionAtRecallLevel(thrirtyPercent));
		assertEquals(oneOfTwo, precisionRecall.precisionAtRecallLevel(fortyPercent));
		assertEquals(oneOfTwo, precisionRecall.precisionAtRecallLevel(fiftyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(sixtyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(seventyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(eightyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(ninetyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(oneHundredPercent));
	}

	@Test
	void rankingHalfOfRelevantSetWithOneMiss() throws Exception {
		List<Character> ranking = Arrays.asList('a', 'b', 'x', 'd', 'e');
		List<Character> relevantSet = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
		PrecisionRecall<Character> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(zeroPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(tenPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(twentyPercent));
		assertEquals(threeOfFour, precisionRecall.precisionAtRecallLevel(thrirtyPercent));
		assertEquals(fourOfFive, precisionRecall.precisionAtRecallLevel(fortyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(fiftyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(sixtyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(seventyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(eightyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(ninetyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(oneHundredPercent));
	}

	@Test
	void rankingTwiceOfRelevantSetWithOneMiss() throws Exception {
		List<Character> ranking = Arrays.asList('a', 'b', 'x', 'd', 'e', 'f', 'g', 'h', 'i', 'j');
		List<Character> relevantSet = Arrays.asList('a', 'b', 'c', 'd', 'e');
		PrecisionRecall<Character> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(zeroPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(tenPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(twentyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(thrirtyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(fortyPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(fiftyPercent));
		assertEquals(threeOfFour, precisionRecall.precisionAtRecallLevel(sixtyPercent));
		assertEquals(threeOfFour, precisionRecall.precisionAtRecallLevel(seventyPercent));
		assertEquals(fourOfFive, precisionRecall.precisionAtRecallLevel(eightyPercent));
		assertEquals(fourOfFive, precisionRecall.precisionAtRecallLevel(ninetyPercent));
		assertEquals(BigDecimal.ZERO, precisionRecall.precisionAtRecallLevel(oneHundredPercent));
	}

	@Test
	void firstHalfOfRankinIncorret() throws Exception {
		List<Character> ranking = Arrays.asList('x', 'x', 'x', 'x', 'x', 'a', 'b', 'c', 'd', 'e');
		List<Character> relevantSet = Arrays.asList('a', 'b', 'c', 'd', 'e');
		PrecisionRecall<Character> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(zeroPercent));
		assertEquals(BigDecimal.ONE, precisionRecall.precisionAtRecallLevel(tenPercent));
		assertEquals(oneOfSix, precisionRecall.precisionAtRecallLevel(twentyPercent));
		assertEquals(oneOfSix, precisionRecall.precisionAtRecallLevel(thrirtyPercent));
		assertEquals(twoOfSeven, precisionRecall.precisionAtRecallLevel(fortyPercent));
		assertEquals(twoOfSeven, precisionRecall.precisionAtRecallLevel(fiftyPercent));
		assertEquals(threeOfEigth, precisionRecall.precisionAtRecallLevel(sixtyPercent));
		assertEquals(threeOfEigth, precisionRecall.precisionAtRecallLevel(seventyPercent));
		assertEquals(fourOfNine, precisionRecall.precisionAtRecallLevel(eightyPercent));
		assertEquals(fourOfNine, precisionRecall.precisionAtRecallLevel(ninetyPercent));
		assertEquals(oneOfTwo, precisionRecall.precisionAtRecallLevel(oneHundredPercent));
	}

}
