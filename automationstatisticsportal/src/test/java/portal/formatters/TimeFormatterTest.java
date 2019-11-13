package portal.formatters;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

public class TimeFormatterTest {

	@Test
	public void oneHour() {
		TimeFormatter t = new TimeFormatter();
		assertEquals("01h 00m 00s 00ms", t.print((long)3600000, Locale.UK));
	}
	@Test
	public void oneMinute() {
		TimeFormatter t = new TimeFormatter();
		assertEquals("00h 01m 00s 00ms", t.print((long)60000, Locale.UK));
	}
	@Test
	public void oneSecond() {
		TimeFormatter t = new TimeFormatter();
		assertEquals("00h 00m 01s 00ms", t.print((long)1000, Locale.UK));
	}
	@Test
	public void oneMillisecond() {
		TimeFormatter t = new TimeFormatter();
		assertEquals("00h 00m 00s 01ms", t.print((long)1, Locale.UK));
	}
	@Test
	public void fiftyNines() {
		TimeFormatter t = new TimeFormatter();
		assertEquals("59h 59m 59s 59ms", t.print((long)215999059, Locale.UK));
	}
	
	
}
