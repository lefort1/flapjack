package mike706574;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Field {
    private final String id;
    private final Integer start;
    private final Integer end;

    @JsonCreator
    public Field( @JsonProperty( "id" ) String id,
                  @JsonProperty( "start" ) Integer start,
                  @JsonProperty( "end" ) Integer end ) {
        this.id = id;
        this.start = start;
        this.end = end;
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
}
