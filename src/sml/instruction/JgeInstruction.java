package sml.instruction;

import sml.Flags;
import sml.Instruction;
import sml.Labels;
import sml.Machine;

public class JgeInstruction extends Instruction {
    private final Flags FLAG;
    public static final String OP_CODE = "jge";


    public JgeInstruction(String label, String opcode, Flags flag) {
        super(label, opcode);
        this.FLAG = flag;
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public int execute(Machine m) {
        if (!FLAG.getSF() || FLAG.getZF()){
         return m.getOffset(label);
    }
        return getSize();
    }

    @Override
    public String toString() {
        return getOpcode() + " " + getLabelString();
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}