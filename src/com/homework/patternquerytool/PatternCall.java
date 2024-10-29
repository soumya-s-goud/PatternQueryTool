package com.homework.patternquerytool;

/**
 * This Class represents a pattern call with an ID, name, file path, and a flag
 * to indicate if it's called.
 * 
 * @author soumya-s-goud
 */
public class PatternCall {

	private final int id;
	private final String name;
	private final String patternFile;
	private final boolean called;

	/**
	 * Constructor creates a PatternCall with the specified details.
	 *
	 * @param id          unique ID of the pattern call
	 * @param name        name of the pattern call
	 * @param patternFile project-relative path to the pattern file
	 * @param called      true if the pattern should be called, false otherwise
	 */
	public PatternCall(int id, String name, String patternFile, boolean called) {
		this.id = id;
		this.name = name;
		this.patternFile = patternFile;
		this.called = called;
	}

	/**
	 * Gets the ID of the pattern call.
	 *
	 * @return the pattern call ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the name of the pattern call.
	 *
	 * @return the pattern call name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the file path of the pattern call.
	 *
	 * @return the file path
	 */
	public String getPatternFile() {
		return patternFile;
	}

	/**
	 * Checks if the pattern is called.
	 *
	 * @return true if called, false if skipped
	 */
	public boolean isCalled() {
		return called;
	}

	/**
	 * Returns a string with the details of the pattern call.
	 *
	 * @return a string representation of this pattern call
	 */
	@Override
	public String toString() {
		return "PatternCall{" + "id=" + id + ", name='" + name + '\'' + ", patternFile='" + patternFile + '\''
				+ ", called=" + called + '}';
	}
}
