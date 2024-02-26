package sml;

public interface OpcodeProviderInterface {
    Class<? extends Instruction> getInstructionClass(String opcode);
}
