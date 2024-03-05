package sml;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public interface IInstructionFactory {
    Instruction getInstruction(String label, Machine machine, Class<? extends Instruction> instructionClass, String[] splits, String opcode);
}
