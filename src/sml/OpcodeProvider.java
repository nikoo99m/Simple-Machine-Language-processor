package sml;

import sml.instruction.*;

import java.util.HashMap;
import java.util.Map;

public class OpcodeProvider implements IOpcodeProvider {
    private final Map<String, Class<? extends Instruction>> instructionMap = new HashMap<>();

    public OpcodeProvider() {
        setInstructionMap();
    }

    public void setInstructionMap() {
        instructionMap.put(MovInstruction.OP_CODE, MovInstruction.class);
        instructionMap.put(AddInstruction.OP_CODE, AddInstruction.class);
        instructionMap.put(SubInstruction.OP_CODE, SubInstruction.class);
        instructionMap.put(MulInstruction.OP_CODE, MulInstruction.class);
        instructionMap.put(DivInstruction.OP_CODE, DivInstruction.class);
        instructionMap.put(CmpInstruction.OP_CODE, CmpInstruction.class);
        instructionMap.put(JgeInstruction.OP_CODE, JgeInstruction.class);
        instructionMap.put(JneInstruction.OP_CODE, JneInstruction.class);
        instructionMap.put(JleInstruction.OP_CODE, JleInstruction.class);
    }

    @Override
    public Class<? extends Instruction> getInstructionClass(String opcode)
    {
        return instructionMap.get(opcode);
    }
}
