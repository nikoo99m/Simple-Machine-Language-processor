package sml;

import sml.instruction.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class InstructionFactory {

    public static Instruction createDualOperandInstructions(String firstOperand, String secondOperand, String label, Machine machine, Class<? extends Instruction> instructionClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<? extends Instruction> constructor;
        constructor = instructionClass.getDeclaredConstructor(String.class, InstructionDestination.class, InstructionSource.class);
        return constructor.newInstance(label, getDestination(firstOperand, machine), getSource(secondOperand, machine));
    }

    public static Instruction createSingleOperandInstruction(String operand, String label, Machine machine, Class<? extends Instruction> instructionClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<? extends Instruction> constructor;
        constructor = instructionClass.getDeclaredConstructor(String.class, InstructionDestination.class);
        return constructor.newInstance(label, getDestination(operand, machine));
    }

    public static Instruction createJumpInstruction(String operand, String label, Class<? extends Instruction> instructionClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<? extends Instruction> constructor;
        constructor = instructionClass.getDeclaredConstructor(String.class, String.class);
        return constructor.newInstance(label, operand);
    }

    
    private static InstructionSource getSource(String s, Machine machine) {
        return Optional.<InstructionSource>empty()
                .or(() -> OperandImmediate.parseOperandImmediate(s))
                .or(() -> OperandMemory.parseOperandMemory(s, machine.getMemory()))
                .or(() -> OperandMemoryWithBase.parseOperandMemoryWithBase(s, machine.getMemory(), machine.getRegisters()))
                .or(() -> OperandRegister.parseOperandRegister(s, machine.getRegisters()))
                .orElseThrow(() -> new IllegalArgumentException("invalid instruction source: " + s));
    }

    private static InstructionDestination getDestination(String s, Machine machine) {
        return Optional.<InstructionDestination>empty()
                .or(() -> OperandMemory.parseOperandMemory(s, machine.getMemory()))
                .or(() -> OperandMemoryWithBase.parseOperandMemoryWithBase(s, machine.getMemory(), machine.getRegisters()))
                .or(() -> OperandRegister.parseOperandRegister(s, machine.getRegisters()))
                .orElseThrow(() -> new IllegalArgumentException("invalid instruction destination: " + s));
    }

}
