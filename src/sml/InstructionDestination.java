package sml;

/**
 * The destination component of instructions:
 * an instruction first reads the value from this component,
 * then performs some calculations and
 * stores the new value in the component.
 * <p>
 * Different types of destinations require different number
 * of bytes in the instruction code.
 */

public interface InstructionDestination {

    int getValue();

    void setValue(int value);

    int getSize();
}
