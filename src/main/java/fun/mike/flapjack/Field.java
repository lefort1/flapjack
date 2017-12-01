package fun.mike.flapjack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Field {
    private final String id;
    private final Integer start;
    private final Integer end;
    private final String type;

    @JsonCreator
    public Field( @JsonProperty( "id" ) String id,
                  @JsonProperty( "start" ) Integer start,
                  @JsonProperty( "end" ) Integer end,
                  @JsonProperty( "type" ) String type ) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.type = type;
    }

    public static Field with( String id, Integer start, Integer end, String type) {
        return new Field( id, start, end, type );
    }

    public String getId() {
        return this.id;
    }

    public Integer getStart() {
        return this.start;
    }

    public Integer getEnd() {
        return this.end;
    }

    public String getType() { return this.type; }
}
