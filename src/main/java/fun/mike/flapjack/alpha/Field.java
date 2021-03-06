package fun.mike.flapjack.alpha;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Field {
    private final String id;
    private final int length;
    private final String type;
    private final Map<String, Object> props;

    @JsonCreator
    public Field(@JsonProperty("id") String id,
                 @JsonProperty("length") int length,
                 @JsonProperty("type") String type,
                 @JsonProperty("props") Map<String, Object> props) {
        this.id = id;
        this.length = length;
        this.type = type;
        this.props = props;
    }

    public static Field with(String id, int length, String type) {
        return new Field(id, length, type, new HashMap<>());
    }

    public static Field with(String id, int length, String type, Map<String, Object> props) {
        return new Field(id, length, type, props);
    }

    public String getId() {
        return this.id;
    }

    public int getLength() {
        return this.length;
    }

    public String getType() {
        return this.type;
    }

    public Map<String, Object> getProps() {
        return this.props;
    }

    @Override
    public String toString() {
        return "Field{" +
                "id='" + id + '\'' +
                ", length=" + length +
                ", type='" + type + '\'' +
                ", props=" + props +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        if (length != field.length) return false;
        if (id != null ? !id.equals(field.id) : field.id != null) return false;
        if (type != null ? !type.equals(field.type) : field.type != null) return false;
        return props != null ? props.equals(field.props) : field.props == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + length;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (props != null ? props.hashCode() : 0);
        return result;
    }
}
