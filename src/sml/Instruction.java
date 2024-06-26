package sml;


import java.util.Objects;

/**
 * Represents an abstract instruction.
 * Every instruction in SML must have an operation code (opcode), and may also include an optional label.
 * The subclasses of this class implement specific types of instructions.
 *
 * @author nikoo99m
 */
public abstract class Instruction {
    protected final String label;
    protected final String opcode;

    /**
     * Constructor: an instruction with a label and an opcode
     * (opcode must be an operation of the language)
     *
     * @param label  optional label (can be null)
     * @param opcode operation name
     */
    public Instruction(String label, String opcode) {
        this.label = label;
        this.opcode = opcode;
    }

    /**
     * Gets the label of the instruction.
     *
     * @return the label of the instruction
     */
    public String getLabel() {
        return label;
    }

    /**
     * Gets the opcode of the instruction.
     *
     * @return the opcode of the instruction
     */
    public String getOpcode() {
        return opcode;
    }

    /**
     * Gets the size of the instruction.
     * This method must be implemented by subclasses to return the size of the instruction.
     *
     * @return the size of the instruction
     */
    public abstract int getSize();

    /**
     * Executes the instruction in the given machine.
     *
     * @param machine the machine the instruction runs on
     * @return the new program counter
     */

    public abstract int execute(IMachine machine);

    protected String getLabelString() {
        return (getLabel() == null) ? "" : getLabel() + ": ";
    }


    // answer : these abstract methods do not have implementation which means
    // subclasses are responsible for providing their own implementations for the required
    // methods based on their attributes and behaviour.

    @Override
    public abstract String toString();



    /**
     * Checks if this instruction is equal to another object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Instruction other) {
            return this.label.equals(other.label)
                    && this.opcode.equals(other.opcode);
        }
        return false;
    }

    /**
     * Generates the hash code of this instruction.
     *
     * @return the hash code of this instruction
     */
    public int hashCode() {
        return Objects.hash(label, opcode);
    }
}
