package info.maxbehr.bpm.externaltaskworker.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Input {

    private boolean aBoolean;
    private int anInt;
    private String aString;
}
