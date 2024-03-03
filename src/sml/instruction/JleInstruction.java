package sml.instruction;

import sml.Flags;
import sml.IFlags;
import sml.IMachine;

public class JleInstruction extends JumpInstruction {
    public static final String OP_CODE = "jle";

    public JleInstruction(String label,  String gotolabel) {
        super(label, OP_CODE, gotolabel);
    }

    @Override
    public int execute(IMachine m) {
        IFlags flag = m.getFlags();
        if (flag.getSF() || flag.getZF()){
            return m.getOffset(GOTOLABEL);}
        return getSize();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && o instanceof JleInstruction;
    }
}
