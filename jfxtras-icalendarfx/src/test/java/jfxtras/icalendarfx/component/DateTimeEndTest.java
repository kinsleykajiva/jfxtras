package jfxtras.icalendarfx.component;

import static org.junit.Assert.assertEquals;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import jfxtras.icalendarfx.components.VComponent;
import jfxtras.icalendarfx.components.VDateTimeEnd;
import jfxtras.icalendarfx.components.VEvent;
import jfxtras.icalendarfx.components.VFreeBusy;
import jfxtras.icalendarfx.properties.component.time.DateTimeEnd;
import jfxtras.icalendarfx.properties.component.time.DateTimeStart;

/**
 * Test following components:
 * @see VEvent
 * @see VFreeBusy
 * 
 * for the following properties:
 * @see DateTimeEnd
 * 
 * @author David Bal
 *
 */
public class DateTimeEndTest
{
    @Test
    public void canBuildLastModified() throws InstantiationException, IllegalAccessException
    {
        List<VDateTimeEnd<?>> components = Arrays.asList(
                new VEvent()
                        .withDateTimeEnd(DateTimeEnd.parse("20160306T080000Z")),
                new VFreeBusy()
                        .withDateTimeEnd(DateTimeEnd.parse("20160306T080000Z"))
                );
        
        for (VDateTimeEnd<?> builtComponent : components)
        {
            String componentName = builtComponent.name();            
            String expectedContent = "BEGIN:" + componentName + System.lineSeparator() +
                    "DTEND:20160306T080000Z" + System.lineSeparator() +
                    "END:" + componentName;
                    
            VComponent parsedComponent = builtComponent.getClass().newInstance();
            parsedComponent.addChild(expectedContent);
            
            assertEquals(parsedComponent, builtComponent);
            assertEquals(expectedContent, builtComponent.toString());            
        }
    }
    
    @Test (expected = DateTimeException.class)
    public void canCatchWrongDateType()
    {
        new VEvent()
                .withDateTimeStart(LocalDate.of(1997, 3, 1))
                .withDateTimeEnd("20160306T080000Z");
    }
    
    @Test (expected = DateTimeException.class)
    public void canCatchWrongDateType2()
    {
       VEvent v = new VEvent()
                .withDateTimeEnd("20160306T080000Z")
                .withDateTimeStart(LocalDate.of(1997, 3, 1));
    }
    
    @Test (expected = DateTimeException.class)
    public void canCatchWrongDateType3()
    {
        VEvent builtComponent = new VEvent();
        builtComponent.setDateTimeEnd(new DateTimeEnd(LocalDateTime.of(2016, 3, 6, 8, 0)));
        builtComponent.setDateTimeStart(new DateTimeStart(LocalDate.of(1997, 3, 1)));
    }
}
