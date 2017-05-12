package gcode;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Java-GCode
 * Created by Matthew Wood on 12/05/2017.
 */
public abstract class GCode
{
    protected volatile boolean completed = false;
    protected final int lineNumber;
    protected final String code;
    protected final String[] arguments;

    protected GCode(String command, int lineNumber) throws InvalidArgumentException
    {
        String[] parts = command.split("\\s+");
        if(parts.length < 1) throw new InvalidArgumentException(new String[]{"GCode has not arguments"});
        this.code = parts[0].toUpperCase();
        this.arguments = Arrays.copyOfRange(parts, 1, parts.length);
        this.lineNumber = lineNumber;
    }

    protected GCode(String command) throws InvalidArgumentException
    {
        this(command,-1);
    }

    public GCode(GCode gCode)
    {
        this.code = gCode.code;
        this.lineNumber = gCode.lineNumber;
        this.arguments = gCode.arguments;
    }

    public abstract boolean process() throws Exception;

    public boolean isCompleted()
    {
        return completed;
    }

    public boolean hasLineNumber()
    {
        return lineNumber > -1;
    }

    public int getLineNumber()
    {
        return lineNumber;
    }

    public GCodeType getPacketType()
    {
        return GCodeType.getById(code);
    }

    public GCode getPacketTyped() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException
    {
        Class<?> gCodeClass = getPacketType().getGCodeClass();
        Constructor<?> ctor = gCodeClass.getConstructor(GCode.class);
        Object typedGCode = ctor.newInstance(this);
        return (GCode) typedGCode;
    }
}
