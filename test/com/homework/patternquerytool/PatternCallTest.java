package com.homework.patternquerytool;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Test class for PatternCall.
 * 
 * @author soumya-s-goud
 */
public class PatternCallTest {

	/**
	 * Test to verify the initialization of PatternCall.
	 */
	@Test
	public void testPatternCallInitialization() {
		PatternCall patternCall = new PatternCall(42, "myPattern", "src/patterns/Functional.pat", false);

		assertEquals(42, patternCall.getId());
		assertEquals("myPattern", patternCall.getName());
		assertEquals("src/patterns/Functional.pat", patternCall.getPatternFile());
		assertFalse(patternCall.isCalled());
	}

	/**
	 * Negative test case to verify behavior when invalid input values are provided.
	 */
	@Test
	public void testPatternCallNegativeValues() {

		// Test with negative ID (assuming constructor allows this)
		PatternCall negativeIdPatternCall = new PatternCall(-1, "myPattern", "src/patterns/Functional.pat", false);
		assertEquals(-1, negativeIdPatternCall.getId()); // Expecting -1 as there’s no validation

		// Test with null name (assuming constructor allows this)
		PatternCall nullNamePatternCall = new PatternCall(42, null, "src/patterns/Functional.pat", false);
		assertNull(nullNamePatternCall.getName()); // Expecting null as there’s no validation
	}

	/**
	 * Invalid test case to check for edge cases such as empty strings, zero ID, and
	 * unusual paths.
	 */
	@Test
	public void testPatternCallEdgeCases() {
		// Test with ID of zero, which may or may not be allowed based on requirements
		PatternCall zeroIdPatternCall = new PatternCall(0, "edgePattern", "src/patterns/EdgeCase.pat", true);
		assertEquals(0, zeroIdPatternCall.getId());

		// Test with an empty name string
		PatternCall emptyNamePatternCall = new PatternCall(100, "", "src/patterns/EmptyName.pat", false);
		assertEquals("", emptyNamePatternCall.getName());

		// Test with unusual or empty path
		PatternCall unusualPathPatternCall = new PatternCall(101, "unusualPattern", "", true);
		assertEquals("", unusualPathPatternCall.getPatternFile());
	}

}
