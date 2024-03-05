package sml;

import sml.instruction.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

// explain this is a factory class which is able to create any instruction which extends jump, single and dual operand instructions. Additionally explain that this factory class consists of multiple factory methods as well.
public class InstructionFactory implements IInstructionFactory {
    private static Instruction createDualOperandInstructions(String firstOperand, String secondOperand, String label, Machine machine, Class<? extends Instruction> instructionClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<? extends Instruction> constructor;
        constructor = instructionClass.getDeclaredConstructor(String.class, InstructionDestination.class, InstructionSource.class);
        return constructor.newInstance(label, getDestination(firstOperand, machine), getSource(secondOperand, machine));
    }

    private static Instruction createSingleOperandInstruction(String operand, String label, Machine machine, Class<? extends Instruction> instructionClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<? extends Instruction> constructor;
        constructor = instructionClass.getDeclaredConstructor(String.class, InstructionDestination.class);
        return constructor.newInstance(label, getDestination(operand, machine));
    }

    private static Instruction createJumpInstruction(String operand, String label, Class<? extends Instruction> instructionClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
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

    private static InstructionFactory factory;

    private InstructionFactory() {}

    public static InstructionFactory getInstance() {
        if (factory == null)
            factory = new InstructionFactory();
        return factory;
    }

    @Override
    public Instruction getInstruction(String label, Machine machine, Class<? extends Instruction> instructionClass, String[] splits, String opcode) {
        if (instructionClass != null) {
            try {
                Constructor<? extends Instruction> constructor;
                if (JumpInstruction.class.isAssignableFrom(instructionClass)) {
                    var operand = splits[1];
                    return createJumpInstruction(operand, label, instructionClass);
                } else if (SingleOperandInstruction.class.isAssignableFrom(instructionClass)) {
                    var operand = splits[1];
                    return createSingleOperandInstruction(operand, label, machine, instructionClass);
                } else if (DualOperandInstruction.class.isAssignableFrom(instructionClass)) {
                    var firstOperand = splits[1];
                    var secondOperand = splits[2];
                    return createDualOperandInstructions(firstOperand, secondOperand, label, machine, instructionClass);
                } else
                    throw new IllegalArgumentException("Unknown instruction class: " + instructionClass.getName());
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     IllegalArgumentException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Unknown instruction: " + opcode);
        }
        return null;
    }

}


