package fun.mike.flapjack.alpha;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

import static fun.mike.map.alpha.Factory.mapOf;

public class SerializationTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void fixedWidth() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        List<Field> fields = Arrays.asList(Field.with("foo", 5, "string"),
                Field.with("bar", 5, "string"));
        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);

        String serializedFormat = mapper.writeValueAsString(format);
        // System.out.println(serializedFormat);
        FixedWidthFormat deserializedFormat = mapper.readValue(serializedFormat, FixedWidthFormat.class);
        String reserializedFormat = mapper.writeValueAsString(deserializedFormat);
        assertEquals(serializedFormat, reserializedFormat);
    }

    @Test
    public void fixedWidthNoProps() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        List<Field> fields = Arrays.asList(new Field("foo", 5, "string", null));
        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);

        String serializedFormat = mapper.writeValueAsString(format);
        // System.out.println(serializedFormat);
        FixedWidthFormat deserializedFormat = mapper.readValue(serializedFormat, FixedWidthFormat.class);
        String reserializedFormat = mapper.writeValueAsString(deserializedFormat);
        assertEquals(serializedFormat, reserializedFormat);
    }

    @Test
    public void unframedDelimited() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.unframed("baz", "Baz", ",", columns);

        String serializedFormat = mapper.writeValueAsString(format);
        // System.out.println(serializedFormat);
        DelimitedFormat deserializedFormat = mapper.readValue(serializedFormat, DelimitedFormat.class);
        String reserializedFormat = mapper.writeValueAsString(deserializedFormat);
        assertEquals(serializedFormat, reserializedFormat);
    }

    @Test
    public void framedDelimited() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.framed("baz", "Baz", ",", "\"", columns);

        String serializedFormat = mapper.writeValueAsString(format);
        // System.out.println(serializedFormat);
        DelimitedFormat deserializedFormat = mapper.readValue(serializedFormat, DelimitedFormat.class);
        String reserializedFormat = mapper.writeValueAsString(deserializedFormat);
        assertEquals(serializedFormat, reserializedFormat);
    }

    @Test
    public void delimitedNoProps() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        List<Column> columns = Arrays.asList(new Column("foo", "string", null));

        DelimitedFormat format = DelimitedFormat.unframed("baz", "Baz", ",", columns);

        String serializedFormat = mapper.writeValueAsString(format);
        // System.out.println(serializedFormat);
        DelimitedFormat deserializedFormat = mapper.readValue(serializedFormat, DelimitedFormat.class);
        String reserializedFormat = mapper.writeValueAsString(deserializedFormat);
        assertEquals(serializedFormat, reserializedFormat);
    }

    @Test
    public void typeProblem() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        TypeProblem problem = new TypeProblem("foo", "string", "bar");

        String serialized = mapper.writeValueAsString(problem);
        // System.out.println(serialized);
        TypeProblem deserialized = mapper.readValue(serialized, TypeProblem.class);
        String reserialized = mapper.writeValueAsString(deserialized);
        assertEquals(serialized, reserialized);
    }

    @Test
    public void record() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        Record record = Record.withData(1L, mapOf("foo", "bar"));
        String serialized = mapper.writeValueAsString(record);
        // System.out.println(serialized);
        Record deserialized = mapper.readValue(serialized, Record.class);
        String reserialized = mapper.writeValueAsString(deserialized);
        assertEquals(serialized, reserialized);
    }

    @Test
    public void recordWithProblem() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        TypeProblem problem = new TypeProblem("foo", "string", "bar");

        Record record = Record.withProblem(1L, mapOf("foo", "bar"), problem);

        String serialized = mapper.writeValueAsString(record);
        // System.out.println(serialized);
        Record deserialized = mapper.readValue(serialized, Record.class);
        String reserialized = mapper.writeValueAsString(deserialized);
        assertEquals(serialized, reserialized);
    }

    @Test
    public void fixedWidthViaInterface() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        List<Field> fields = Arrays.asList(Field.with("foo", 5, "string"),
                                           Field.with("bar", 5, "string"));
        FixedWidthFormat format = new FixedWidthFormat("baz", "Baz", fields);

        String serializedFormat = mapper.writeValueAsString(format);
        // System.out.println(serializedFormat);
        Format deserializedFormat = mapper.readValue(serializedFormat, Format.class);

        String reserializedFormat = mapper.writeValueAsString(deserializedFormat);
        assertEquals(serializedFormat, reserializedFormat);
    }

    @Test
    public void delimitedWidthViaInterface() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());

        List<Column> columns = Arrays.asList(Column.with("foo", "string"),
                                             Column.with("bar", "string"));

        DelimitedFormat format = DelimitedFormat.unframed("baz", "Baz", ",", columns);

        String serializedFormat = mapper.writeValueAsString(format);
        // System.out.println(serializedFormat);
        Format deserializedFormat = mapper.readValue(serializedFormat, Format.class);
        String reserializedFormat = mapper.writeValueAsString(deserializedFormat);
        assertEquals(serializedFormat, reserializedFormat);
    }
}
