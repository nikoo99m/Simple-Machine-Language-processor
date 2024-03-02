package sml.instruction;

import sml.Flags;
import sml.Instruction;
import sml.Machine;

import java.util.Objects;

public class JleInstruction extends Instruction {
    public static final String OP_CODE = "jle";
    private final String GOTOLABEL;

    public JleInstruction(String label,  String gotolabel) {
        super(label, OP_CODE);
        GOTOLABEL = gotolabel;
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public int execute(Machine m) {
        Flags flag = m.getFlags();
        if (flag.getSF() || flag.getZF()){
            return m.getOffset(GOTOLABEL);}
        return getSize();
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
        if (o instanceof JleInstruction other) {
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
