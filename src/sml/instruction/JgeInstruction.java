package sml.instruction;

import sml.Flags;
import sml.Instruction;
import sml.Labels;
import sml.Machine;

public class JgeInstruction extends Instruction {
    public static final String OP_CODE = "jge";
    private final String GOTOLABEL;

    public JgeInstruction(String label, String goToLabel) {
        super(label, OP_CODE);
        this.GOTOLABEL = goToLabel;
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public int execute(Machine m) {
        Flags flag = m.getFlags();
        if (!flag.getSF() || flag.getZF()){
         return m.getOffset(GOTOLABEL);
    }
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
        if (o instanceof JgeInstruction other) {
            return this.GOTOLABEL.equals(other.GOTOLABEL)
                    && super.equals(other);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
