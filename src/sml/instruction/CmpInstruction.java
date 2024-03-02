package sml.instruction;

import sml.*;

import java.util.Objects;

import static sml.Registers.RegisterNameImpl.AX;
import static sml.Registers.RegisterNameImpl.CX;

public class CmpInstruction extends Instruction {
    private final InstructionDestination result;
    private final InstructionSource source;
    public static final String OP_CODE = "cmp";

    public CmpInstruction(String label, InstructionDestination result, InstructionSource source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }


    @Override
    public int getSize() {
        {
            return 1 + source.getSize() + result.getSize();
        }
    }

    @Override
    public int execute(Machine m) {
        Flags flag = m.getFlags();
        int i = source.getValue();
        int j = result.getValue();

        if (i == j)
            flag.setZF(true);
        else
            flag.setZF(false);

        flag.setSF(j < i);

        return getSize();
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + ", " + source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof CmpInstruction other) {
            return this.source.equals(other.source)
                    && this.result.equals(other.result)
                    && super.equals(other);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), result, source);
    }
}

