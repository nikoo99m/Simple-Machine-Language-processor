package sml.instruction;

import sml.Instruction;

import java.util.Objects;

public abstract class JumpInstruction extends Instruction {
    protected final String GOTOLABEL;

    public JumpInstruction(String label, String opcode, String goToLabel) {
        super(label, opcode);
        this.GOTOLABEL = goToLabel;
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public String toString() {
        return getOpcode() + " " + GOTOLABEL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof JumpInstruction other) {
            return this.GOTOLABEL.equals(other.GOTOLABEL)
                    && super.equals(other);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), GOTOLABEL);
    }
}
