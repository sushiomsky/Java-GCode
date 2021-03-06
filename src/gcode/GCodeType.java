package gcode;

import gcode.movement.G1_LinearMove;
import gcode.movement.G0_RapidLinearMove;
import gcode.movement.G4_Dwell;
import gcode.reserved.UnknownGCode;

/**
 * Java-Gcode
 * Created by Matthew Wood on 12/05/2017.
 */
public enum GCodeType
{

    G0("G0", "Rapid Linear Move", G0_RapidLinearMove.class),
    G1("G1", "Linear Move", G1_LinearMove.class),
    G4("G4", "Dwell",G4_Dwell.class),

    GENERIC("", "Unknown gcode", UnknownGCode.class);

    private String designator;
    private String name;
    private Class cls;

    GCodeType(String designator, String name, Class cls)
    {
        this.designator = designator;
        this.name = name;
        this.cls = cls;
    }

    public String getDesignator()
    {
        return designator;
    }

    public String getName()
    {
        return name;
    }

    public Class getGCodeClass()
    {
        return cls;
    }

    public static GCodeType getById(String designator) {
        for(GCodeType e : values()) {
            if(e.designator.equals(designator)) return e;
        }
        return GENERIC;
    }
}
