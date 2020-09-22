package com.cgi.laps.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * @author balekundrim
 *
 */
public class CustomerAccountDAOTest {
	
	@Test
	public void simpleTest() {
		final String expected = "abc";
		assertThat(expected).isEqualTo("abc");
	}

}
