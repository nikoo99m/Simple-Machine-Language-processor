package sml;

/** The source component of instructions:
 * an instruction only reads the value from the source.
 * <p>
 * Different types of sources require different number
 * of bytes in the instruction code.
 */

public interface InstructionSource {

    int getValue();

    int getSize();
}
