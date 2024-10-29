package com.homework.patternquerytool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for PatternCallRepository.
 * 
 * @author soumya-s-goud
 */

public class PatternCallRepositoryTest {

	private PatternCallRepository repository;
	private Path testFilePath;

	@BeforeEach
	public void setUp() throws IOException {
		repository = new PatternCallRepository();
		testFilePath = Files.createTempFile("patternCallsTest", ".txt");
	}

	@AfterEach
	public void tearDown() throws IOException {
		Files.deleteIfExists(testFilePath);
	}

	/**
	 * Valid test case for adding and retrieving a PatternCall by ID, name, and
	 * path.
	 */
	@Test
	public void testAddAndRetrievePatternCall_ValidCase() {
		PatternCall patternCall = new PatternCall(1, "TestPattern", "src/patterns/TestPattern.pat", true);
		repository.addPatternCall(patternCall);

		// Verify retrieval by ID
		PatternCall retrievedById = repository.getPatternCallById(1);
		assertNotNull(retrievedById);
		assertEquals("TestPattern", retrievedById.getName());

		// Verify retrieval by name
		List<PatternCall> retrievedByName = repository.getPatternCallsByName("TestPattern");
		assertEquals(1, retrievedByName.size());
		assertEquals("src/patterns/TestPattern.pat", retrievedByName.get(0).getPatternFile());

		// Verify retrieval by path
		List<PatternCall> retrievedByPath = repository.getPatternCallsByPath("src/patterns/TestPattern.pat");
		assertEquals(1, retrievedByPath.size());
		assertEquals("TestPattern", retrievedByPath.get(0).getName());
	}

	/**
	 * Edge test case for adding multiple PatternCalls with the same name but
	 * different IDs and paths.
	 */
	@Test
	public void testAddPatternCalls_SameNameDifferentIdAndPath() {
		PatternCall patternCall1 = new PatternCall(1, "CommonPattern", "src/patterns/Path1.pat", true);
		PatternCall patternCall2 = new PatternCall(2, "CommonPattern", "src/patterns/Path2.pat", false);
		repository.addPatternCall(patternCall1);
		repository.addPatternCall(patternCall2);

		// Retrieve by name and check both PatternCalls are returned
		List<PatternCall> retrievedByName = repository.getPatternCallsByName("CommonPattern");
		assertEquals(2, retrievedByName.size());
		assertTrue(retrievedByName.stream().anyMatch(pc -> pc.getId() == 1));
		assertTrue(retrievedByName.stream().anyMatch(pc -> pc.getId() == 2));
	}

	/**
	 * Negative test case for attempting to add duplicate PatternCall by ID.
	 */
	@Test
	public void testAddPatternCall_DuplicateId() {
		PatternCall patternCall1 = new PatternCall(1, "UniquePattern", "src/patterns/Unique.pat", true);
		PatternCall patternCall2 = new PatternCall(1, "AnotherPattern", "src/patterns/Another.pat", false);

		// Add first PatternCall
		repository.addPatternCall(patternCall1);
		// Attempt to add second PatternCall with the same ID
		repository.addPatternCall(patternCall2);

		// Verify only one entry exists in the repository with that ID
		assertEquals(1, repository.getPatternCalls().size(),
				"Repository should only contain one PatternCall with a unique ID.");
		assertEquals("UniquePattern", repository.getPatternCallById(1).getName(),
				"The name should be from the first PatternCall added.");
	}

	/**
	 * Edge test case for retrieving PatternCalls when no PatternCalls exist in the
	 * repository.
	 */
	@Test
	public void testRetrieveFromEmptyRepository() {
		assertNull(repository.getPatternCallById(1));
		assertTrue(repository.getPatternCallsByName("NonExistentPattern").isEmpty());
		assertTrue(repository.getPatternCallsByPath("src/patterns/NonExistent.pat").isEmpty());
	}

	/**
	 * Test for retrieving all PatternCalls to verify repository holds all added
	 * entries.
	 */
	@Test
	public void testGetAllPatternCalls() {
		PatternCall patternCall1 = new PatternCall(1, "PatternOne", "src/patterns/PatternOne.pat", true);
		PatternCall patternCall2 = new PatternCall(2, "PatternTwo", "src/patterns/PatternTwo.pat", false);
		repository.addPatternCall(patternCall1);
		repository.addPatternCall(patternCall2);

		List<PatternCall> allPatternCalls = repository.getPatternCalls();
		assertEquals(2, allPatternCalls.size());
	}

	/**
	 * Edge test case for adding and retrieving a PatternCall with special
	 * characters in the name and path.
	 */
	@Test
	public void testAddPatternCall_SpecialCharacters() {
		PatternCall patternCall = new PatternCall(1, "Pattern@Name#", "src/patterns/Pattern#1.pat", true);
		repository.addPatternCall(patternCall);

		// Verify retrieval by name with special characters
		List<PatternCall> retrievedByName = repository.getPatternCallsByName("Pattern@Name#");
		assertEquals(1, retrievedByName.size());
		assertEquals("src/patterns/Pattern#1.pat", retrievedByName.get(0).getPatternFile());
	}

	/**
	 * Edge test case for attempting to retrieve a PatternCall with an ID that does
	 * not exist.
	 */
	@Test
	public void testGetPatternCallByNonExistentId() {
		PatternCall patternCall = new PatternCall(1, "PatternOne", "src/patterns/PatternOne.pat", true);
		repository.addPatternCall(patternCall);

		assertNull(repository.getPatternCallById(999)); // Non-existent ID
	}

	/**
	 * Test for handling case sensitivity in names and paths.
	 */
	@Test
	public void testCaseSensitivity() {
		PatternCall patternCall = new PatternCall(1, "PatternCase", "src/patterns/CaseSensitive.pat", true);
		repository.addPatternCall(patternCall);

		assertTrue(repository.getPatternCallsByName("patterncase").isEmpty());
		assertTrue(repository.getPatternCallsByPath("src/PATTERNS/CASESENSITIVE.PAT").isEmpty());
	}

	/**
	 * Test for reading a valid file with multiple pattern entries.
	 */
	@Test
	public void testReadFromFile_MultiplePatterns() throws IOException {
		Path filePath = Path.of("test/resources/multiple_patterns.txt");
		Files.writeString(filePath,
				"1000,myPattern,src/patterns/Functional.pat,true\n"
						+ "2000,anotherPattern,src/patterns/Another.pat,false\n"
						+ "3000,thirdPattern,src/patterns/Third.pat,true",
				StandardOpenOption.CREATE);

		repository.readFromFile(filePath.toString());

		List<PatternCall> patterns = repository.getPatternCalls();
		assertEquals(3, patterns.size());
	}

	/**
	 * Test for reading an empty file to verify no patterns are added.
	 */
	@Test
	public void testReadFromFile_EmptyFile() throws IOException {
		Path filePath = Path.of("test/resources/empty_file.txt");
		Files.writeString(filePath, "", StandardOpenOption.CREATE);

		repository.readFromFile(filePath.toString());

		assertTrue(repository.getPatternCalls().isEmpty(), "Expected no patterns in the repository for an empty file.");
	}

	/**
	 * Test for reading a file with invalid data to ensure handling is correct.
	 */
	@Test
	public void testReadFromFile_InvalidFileFormat() throws IOException {
		Path filePath = Path.of("test/resources/invalid_file.txt");
		Files.writeString(filePath, "Invalid,Data,Format", StandardOpenOption.CREATE);

		repository.readFromFile(filePath.toString());

		assertTrue(repository.getPatternCalls().isEmpty(), "Expected no patterns for invalid file format.");
	}

	/**
	 * Test for reading a file with a very large number of patterns.
	 */
	@Test
	public void testReadFromFile_LargeFile() throws IOException {
		Path filePath = Path.of("test/resources/large_file.txt");
		StringBuilder data = new StringBuilder();
		for (int i = 1; i <= 10000; i++) {
			data.append(i).append(",pattern").append(i).append(",src/patterns/pattern").append(i).append(".pat,true\n");
		}
		Files.writeString(filePath, data.toString(), StandardOpenOption.CREATE);

		repository.readFromFile(filePath.toString());

		assertEquals(10000, repository.getPatternCalls().size(), "Expected 10,000 patterns in the repository.");
	}

	/**
	 * Test for reading a file where ID fields are duplicated.
	 */
	@Test
	public void testReadFromFile_DuplicateIDs() throws IOException {
		Path filePath = Path.of("test/resources/duplicate_ids.txt");
		Files.writeString(filePath, "42,myPattern,src/patterns/Functional.pat,true\n"
				+ "42,myPatternDuplicate,src/patterns/Duplicate.pat,false", StandardOpenOption.CREATE);

		repository.readFromFile(filePath.toString());

		assertEquals(1, repository.getPatternCalls().size(), "Expected only one entry for duplicate ID.");
	}

	/**
	 * Test for reading a file with non-boolean values in the 'called' field.
	 */
	@Test
	public void testReadFromFile_InvalidBooleanValue() throws IOException {
		Path filePath = Path.of("test/resources/invalid_boolean_value.txt");
		Files.writeString(filePath, "42,myPattern,src/patterns/Functional.pat,notBoolean", StandardOpenOption.CREATE);

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			repository.readFromFile(filePath.toString());
		});
		assertTrue(exception.getMessage().contains("notBoolean"));
	}

	/**
	 * Valid test case for writing pattern calls to a file.
	 */
	@Test
	public void testWriteToFile_ValidCase() throws IOException {
		repository.addPatternCall(new PatternCall(1, "patternOne", "src/patterns/PatternOne.pat", true));
		repository.addPatternCall(new PatternCall(2, "patternTwo", "src/patterns/PatternTwo.pat", false));

		repository.writeToFile(testFilePath.toString());

		List<String> lines = Files.readAllLines(testFilePath);
		assertEquals(2, lines.size());
		assertEquals("1,patternOne,src/patterns/PatternOne.pat,true", lines.get(0));
		assertEquals("2,patternTwo,src/patterns/PatternTwo.pat,false", lines.get(1));
	}

	/**
	 * Edge case test for writing to a file when there are no pattern calls in
	 * memory.
	 */
	@Test
	public void testWriteToFile_EmptyPatternCalls() throws IOException {
		repository.writeToFile(testFilePath.toString());

		List<String> lines = Files.readAllLines(testFilePath);
		assertTrue(lines.isEmpty(), "The file should be empty when no pattern calls are in memory.");
	}

	/**
	 * Test case for writing to a file with a very large number of pattern calls.
	 */
	@Test
	public void testWriteToFile_LargeNumberOfPatternCalls() throws IOException {
		for (int i = 0; i < 1000; i++) {
			repository
					.addPatternCall(new PatternCall(i, "pattern" + i, "src/patterns/Pattern" + i + ".pat", i % 2 == 0));
		}

		repository.writeToFile(testFilePath.toString());

		List<String> lines = Files.readAllLines(testFilePath);
		assertEquals(1000, lines.size());
		assertEquals("0,pattern0,src/patterns/Pattern0.pat,true", lines.get(0));
		assertEquals("999,pattern999,src/patterns/Pattern999.pat,false", lines.get(999));
	}

	/**
	 * Edge case test for verifying that special characters in pattern names and
	 * paths are correctly written to file.
	 */
	@Test
	public void testWriteToFile_SpecialCharacters() throws IOException {
		repository.addPatternCall(new PatternCall(1, "pattern@Name$", "src/patterns/Pattern#1.pat", true));

		repository.writeToFile(testFilePath.toString());

		List<String> lines = Files.readAllLines(testFilePath);
		assertEquals(1, lines.size());
		assertEquals("1,pattern@Name$,src/patterns/Pattern#1.pat,true", lines.get(0));
	}

}
