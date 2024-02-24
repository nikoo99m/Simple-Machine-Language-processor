package sml.instruction;

import sml.Flags;
import sml.Instruction;
import sml.Machine;

import java.util.Objects;

public class JleInstruction extends Instruction {
    private final Flags FLAG;
    public static final String OP_CODE = "jle";
    private final String GOTOLABEL;

    public JleInstruction(String label, String opcode, Flags flag, String gotolabel) {
        super(label, opcode);
        FLAG = flag;
        GOTOLABEL = gotolabel;
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public int execute(Machine m) {
        if (FLAG.getSF() || FLAG.getZF()){
            return m.getOffset(GOTOLABEL);}
        return getSize();
    }

    @Override
    public String toString() {
        return getOpcode() + " " + GOTOLABEL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JleInstruction that)) return false;
        return Objects.equals(FLAG, that.FLAG);
    }

    @Override
    public int hashCode() {
        return Objects.hash(FLAG);
    }
}
