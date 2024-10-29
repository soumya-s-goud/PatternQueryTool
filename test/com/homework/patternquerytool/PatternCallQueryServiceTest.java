package com.homework.patternquerytool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Test class for PatternCallQueryService.
 * 
 * @author soumya-s-goud
 */
public class PatternCallQueryServiceTest {
	private PatternCallRepository mockRepository;
	private PatternCallQueryService queryService;

	@BeforeEach
	public void setUp() {
		mockRepository = Mockito.mock(PatternCallRepository.class);
		queryService = new PatternCallQueryService(mockRepository);
	}

	/**
	 * Positive test case for retrieving a PatternCall by ID.
	 */
	@Test
	public void testGetPatternCallById_Positive() {
		PatternCall expectedPatternCall = new PatternCall(1, "TestPattern", "test/path.pat", true);
		when(mockRepository.getPatternCallById(1)).thenReturn(expectedPatternCall);

		PatternCall actualPatternCall = queryService.getPatternCallById(1);
		assertEquals(expectedPatternCall, actualPatternCall,
				"The retrieved PatternCall should match the expected PatternCall.");
	}

	/**
	 * Negative test case for retrieving a PatternCall by a non-existent ID.
	 */
	@Test
	public void testGetPatternCallById_Negative() {
		when(mockRepository.getPatternCallById(999)).thenReturn(null);

		PatternCall actualPatternCall = queryService.getPatternCallById(999);
		assertNull(actualPatternCall, "The retrieved PatternCall should be null for a non-existent ID.");
	}

	/**
	 * Test case for retrieving PatternCalls by name with multiple matches.
	 */
	@Test
	public void testGetPatternCallsByName_MultipleMatches() {
		// Given
		PatternCall patternCall1 = new PatternCall(1, "TestPattern", "test/path1.pat", true);
		PatternCall patternCall2 = new PatternCall(2, "TestPattern", "test/path2.pat", false);
		when(mockRepository.getPatternCallsByName("TestPattern")).thenReturn(Arrays.asList(patternCall1, patternCall2));

		// When
		List<PatternCall> actualPatternCalls = queryService.getPatternCallsByName("TestPattern");

		// Then
		assertEquals(2, actualPatternCalls.size(), "Should return 2 PatternCalls for the name 'TestPattern'.");

		// Check the contents of the returned list
		assertTrue(actualPatternCalls.contains(patternCall1), "Should contain the first pattern call.");
		assertTrue(actualPatternCalls.contains(patternCall2), "Should contain the second pattern call.");

		// Verify the attributes of the retrieved PatternCalls
		PatternCall retrievedCall1 = actualPatternCalls.get(0);
		PatternCall retrievedCall2 = actualPatternCalls.get(1);

		assertEquals(1, retrievedCall1.getId(), "ID of the first pattern call should be 1.");
		assertEquals("TestPattern", retrievedCall1.getName(), "Name of the first pattern call should match.");
		assertEquals("test/path1.pat", retrievedCall1.getPatternFile(),
				"File path of the first pattern call should match.");
		assertTrue(retrievedCall1.isCalled(), "First pattern call should be marked as called.");

		assertEquals(2, retrievedCall2.getId(), "ID of the second pattern call should be 2.");
		assertEquals("TestPattern", retrievedCall2.getName(), "Name of the second pattern call should match.");
		assertEquals("test/path2.pat", retrievedCall2.getPatternFile(),
				"File path of the second pattern call should match.");
		assertFalse(retrievedCall2.isCalled(), "Second pattern call should not be marked as called.");
	}

	/**
	 * Test case for retrieving PatternCalls by name with no matches.
	 */
	@Test
	public void testGetPatternCallsByName_NoMatches() {
		when(mockRepository.getPatternCallsByName("NonExistentPattern")).thenReturn(Collections.emptyList());

		List<PatternCall> actualPatternCalls = queryService.getPatternCallsByName("NonExistentPattern");
		assertTrue(actualPatternCalls.isEmpty(), "Should return an empty list for a non-existent pattern name.");
	}

	/**
	 * Positive test case for retrieving PatternCalls by file path.
	 */
	@Test
	public void testGetPatternCallsByPath_Positive() {
		PatternCall patternCall = new PatternCall(1, "TestPattern", "test/path.pat", true);
		when(mockRepository.getPatternCallsByPath("test/path.pat")).thenReturn(Collections.singletonList(patternCall));

		List<PatternCall> actualPatternCalls = queryService.getPatternCallsByPath("test/path.pat");
		assertEquals(1, actualPatternCalls.size(), "Should return 1 PatternCall for the specified file path.");
		assertEquals(patternCall, actualPatternCalls.get(0),
				"The returned PatternCall should match the expected PatternCall.");
	}

	/**
	 * Negative test case for retrieving PatternCalls by a non-existent file path.
	 */
	@Test
	public void testGetPatternCallsByPath_NoMatches() {
		when(mockRepository.getPatternCallsByPath("nonexistent/path.pat")).thenReturn(Collections.emptyList());

		List<PatternCall> actualPatternCalls = queryService.getPatternCallsByPath("nonexistent/path.pat");
		assertTrue(actualPatternCalls.isEmpty(), "Should return an empty list for a non-existent file path.");
	}

	/**
	 * Test for retrieving skipped PatternCalls.
	 */
	@Test
	public void testGetSkippedPatternCalls() {
		PatternCall patternCall1 = new PatternCall(1, "SkippedPattern", "test/path1.pat", false);
		PatternCall patternCall2 = new PatternCall(2, "CalledPattern", "test/path2.pat", true);
		when(mockRepository.getPatternCalls()).thenReturn(Arrays.asList(patternCall1, patternCall2));

		List<PatternCall> skippedPatternCalls = queryService.getSkippedPatternCalls();
		assertEquals(1, skippedPatternCalls.size(), "Should return 1 skipped PatternCall.");
		assertEquals(patternCall1, skippedPatternCalls.get(0),
				"The skipped PatternCall should match the expected PatternCall.");
	}

	/**
	 * Test for retrieving called PatternCalls.
	 */
	@Test
	public void testGetCalledPatternCalls() {
		PatternCall patternCall1 = new PatternCall(1, "CalledPattern", "test/path1.pat", true);
		PatternCall patternCall2 = new PatternCall(2, "SkippedPattern", "test/path2.pat", false);
		when(mockRepository.getPatternCalls()).thenReturn(Arrays.asList(patternCall1, patternCall2));

		List<PatternCall> calledPatternCalls = queryService.getCalledPatternCalls();
		assertEquals(1, calledPatternCalls.size(), "Should return 1 called PatternCall.");
		assertEquals(patternCall1, calledPatternCalls.get(0),
				"The called PatternCall should match the expected PatternCall.");
	}
}
