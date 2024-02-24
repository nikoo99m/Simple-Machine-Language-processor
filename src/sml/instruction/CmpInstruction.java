package sml.instruction;

import sml.*;

import java.util.Objects;

import static sml.Registers.RegisterNameImpl.AX;
import static sml.Registers.RegisterNameImpl.CX;

public class CmpInstruction extends Instruction {
    private final InstructionDestination result;
    private final InstructionSource source;
    private Flags flag;
    public static final String OP_CODE = "cmp";

    public CmpInstruction(String label, InstructionDestination result, InstructionSource source, Flags flag) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
        this.flag = flag;
    }


    @Override
    public int getSize() {
        {
            return 1 + source.getSize() + result.getSize();
        }
    }

    @Override
    public int execute(Machine m) {
        int register1 = m.getRegisters().get(CX);
        int register2 = m.getRegisters().get(AX);

        if (register1 == register2)
            flag.setZF(true);
        else flag.setSF(register1 < register2);

        return getSize();
    }

    @Override
    public String toString() {
            return getLabelString() + getOpcode() + " " + result + ", " + source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CmpInstruction that)) return false;
        return Objects.equals(result, that.result) && Objects.equals(source, that.source) && Objects.equals(flag, that.flag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, source, flag);
    }
}

