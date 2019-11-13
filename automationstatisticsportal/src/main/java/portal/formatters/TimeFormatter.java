package portal.formatters;

import java.text.ParseException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class TimeFormatter implements Formatter<Long> {
	
	@Override
	public String print(Long input, Locale locale) {
    	String output = String.format("%02dh %02dm %02ds %02dms", 
    		    TimeUnit.MILLISECONDS.toHours(input),
    		    TimeUnit.MILLISECONDS.toMinutes(input) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(input)),
    		    TimeUnit.MILLISECONDS.toSeconds(input) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(input)),
    		    TimeUnit.MILLISECONDS.toMillis(input) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(input)));
        return output;
	}

	@Override
	public Long parse(String text, Locale locale) throws ParseException {
		return null;
	}
}