package sml;

public interface IOpcodeProvider {
    Class<? extends Instruction> getInstructionClass(String opcode);
}
