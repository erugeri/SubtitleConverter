package fr.rugeri.subtitleconverter;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

	@Test
	public void testPosition() throws Exception {
		assertEquals("example", Main.cleanLine("{\\pos(250,270)}example"));
	}

	@Test
	public void testFont() throws Exception {
		assertEquals("example", Main.cleanLine("<font color=#fff00>example</font>"));
		assertEquals("example", Main.cleanLine(" <font color=#fff00> example </font> "));
		assertEquals("example", Main.cleanLine("<font color=\"#fff00\">example</font>"));
		assertEquals("example", Main.cleanLine("<font  color=#fff00>example</font>"));
	}

	@Test
	public void testTags() throws Exception {
		assertEquals("example", Main.cleanLine("<i>example</i>"));
		assertEquals("example", Main.cleanLine(" < i > example < / i > "));
		assertEquals("example", Main.cleanLine("<I>example</ I>"));
		assertEquals("example", Main.cleanLine("<b>example</b>"));
		assertEquals("example", Main.cleanLine("<a>example</d>"));
	}

	@Test
	public void testSpaces() throws Exception {
		assertEquals("example", Main.cleanLine("   example  "));
	}

	@Test
	public void testComplex() throws Exception {
		assertEquals("complex example", Main.cleanLine("{\\pos(250,270)}  < i ><font color>complex example</font></i> "));
	}
}
