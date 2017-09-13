package mike706574;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import java.util.stream.Collectors;

public class ParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void valid() {
        List<Field> fields = Arrays.asList( new Field( "foo", 1, 5 ),
                                            new Field( "bar", 6, 10 ) );

        Format format = new Format( "baz", "Baz", 10, fields );

        Parser parser = new Parser( format );

        List<String> lines = Arrays.asList( "1234567890",
                                            "abcdefghij" );

        List<Record> records = parser.stream( lines.stream() )
            .collect( Collectors.toList() );

        assertEquals( 2, records.size() );

        Record record1 = records.get( 0 );
        assertEquals( "12345", record1.get( "foo" ) );
        assertEquals( "67890", record1.get( "bar" ) );

        Record record2 = records.get( 1 );
        assertEquals( "abcde", record2.get( "foo" ) );
        assertEquals( "fghij", record2.get( "bar" ) );
    }

    @Test
    public void lengthMismatch() {
        List<Field> fields = Arrays.asList( Field.with( "foo", 1, 2 ) );
        Format format = new Format( "baz", "Baz", 2, fields );
        Parser parser = new Parser( format );

        List<String> lines = Arrays.asList( "abc" );
        List<Record> records = parser.stream( lines.stream() )
            .collect( Collectors.toList() );

        assertEquals( 1, records.size() );

        Record record1 = records.get( 0 );
        assertEquals( "ab", record1.get( "foo" ) );

        Set<Error> errors = record1.getErrors();
        assertEquals( 1, errors.size() );
        Error error = errors.iterator().next();
        assertEquals( "length-mismatch", error.getId() );
        assertFalse( error.getFieldId().isPresent() );
    }

    @Test
    public void outOfBounds() {
        List<Field> fields = Arrays.asList( Field.with( "foo", 1, 2 ),
                                            Field.with( "bar", 3, 4 ));
        Format format = new Format( "baz", "Baz", 4, fields );
        Parser parser = new Parser( format );

        List<String> lines = Arrays.asList( "abc" );
        List<Record> records = parser.stream( lines.stream() )
            .collect( Collectors.toList() );

        assertEquals( 1, records.size() );

        Record record1 = records.get( 0 );
        assertEquals( "ab", record1.get( "foo" ) );
        assertFalse( record1.containsKey( "bar" ) );

        Set<Error> errors = record1.getErrors();
        assertEquals( 2, errors.size() );

        Iterator<Error> it = errors.iterator();

        Error error1 = it.next();
        assertEquals( "field-out-of-bounds", error1.getId() );
        assertEquals( "bar", error1.getFieldId().get() );

        Error error2 = it.next();
        assertEquals( "length-mismatch", error2.getId() );
        assertFalse( error2.getFieldId().isPresent() );
    }
}
