package com.cgi.laps;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Test
	public void testPalindromePass() {
		App app = new App();
		String input = "POOP";
		boolean actual = app.isPalindrome(input);
		boolean expected = true;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testPalindromeFail() {
		App app = new App();
		String input = "Gumang";
		boolean actual = app.isPalindrome(input);
		boolean expected = false;
		assertEquals(expected, actual);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPalindromeFailByException() {
		App app = new App();
		String input = null;
		boolean actual = app.isPalindrome(input);
		boolean expected = false;
		assertEquals(expected, actual);
	}

}
