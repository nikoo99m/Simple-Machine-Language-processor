package sml;

import sml.instruction.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 * This class ....
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author ...
 */
public final class Translator {

    private final String fileName; // source file of SML code
    private final OpcodeProviderInterface opcodeProvider;

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

    public Translator(String fileName, OpcodeProviderInterface opcodeProvider ) {
        this.fileName = fileName;
        this.opcodeProvider = opcodeProvider;
    }

    public void readAndTranslate(Machine machine) throws IOException {
        Labels labels = machine.getLabels();
        Map<Integer, Instruction> program = machine.getProgram();

        // translate the small program in the file into
        // the labels and the program

        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            labels.reset();
            program.clear();
            int programCounter = 0;

            // each iteration processes the contents of line
            // and reads the next input line into "line"
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String label = getLabel();

                Instruction instruction = getInstruction(label, machine);
                if (instruction != null) {
                    if (label != null)
                        labels.addLabel(label, programCounter);
                    program.put(programCounter, instruction);
                    programCounter += instruction.getSize();
                }
            }
        }
    }



    /**
     * Translates the current line into an instruction with the given label
     *
     * @param label the instruction label
     * @return the new instruction
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */

    public Instruction getInstruction(String label, Machine machine) {
        if (line.isEmpty())
            return null;

        String opcode = scan(false);
        Class<? extends Instruction> instructionClass = opcodeProvider.getInstructionClass(opcode);
        if (instructionClass != null) {
            try {
                Constructor<? extends Instruction> constructor;
                if (opcode.equals(JgeInstruction.OP_CODE) || opcode.equals(JneInstruction.OP_CODE) || opcode.equals(JleInstruction.OP_CODE)) {
                    constructor = instructionClass.getDeclaredConstructor(String.class, String.class);
                    return constructor.newInstance(label, scan(false));
                } else if (opcode.equals(MulInstruction.OP_CODE) || opcode.equals(DivInstruction.OP_CODE)) {
                    constructor = instructionClass.getDeclaredConstructor(String.class, InstructionDestination.class);
                    return constructor.newInstance(label, getDestination(scan(false), machine));
                } else {
                    constructor = instructionClass.getDeclaredConstructor(String.class, InstructionDestination.class, InstructionSource.class);
                    return constructor.newInstance(label, getDestination(scan(true), machine), getSource(scan(false), machine));
                }
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Unknown instruction: " + opcode);
        }
        return null;
            // TODO: add code for all other types of instructions // DONE

            // TODO: Then, replace the switch by using the Reflection API // DONE

            // TODO: Next, use dependency injection to allow this machine class
            //       to work with different sets of opcodes (different CPUs) // DONE
    }

    private InstructionSource getSource(String s, Machine machine) {
        return Optional.<InstructionSource>empty()
                .or(() -> OperandImmediate.parseOperandImmediate(s))
                .or(() -> OperandMemory.parseOperandMemory(s, machine.getMemory()))
                .or(() -> OperandMemoryWithBase.parseOperandMemoryWithBase(s, machine.getMemory(), machine.getRegisters()))
                .or(() -> OperandRegister.parseOperandRegister(s, machine.getRegisters()))
                .orElseThrow(() -> new IllegalArgumentException("invalid instruction source: " + s));
    }

    private InstructionDestination getDestination(String s, Machine machine) {
        return Optional.<InstructionDestination>empty()
                .or(() -> OperandMemory.parseOperandMemory(s, machine.getMemory()))
                .or(() -> OperandMemoryWithBase.parseOperandMemoryWithBase(s, machine.getMemory(), machine.getRegisters()))
                .or(() -> OperandRegister.parseOperandRegister(s, machine.getRegisters()))
                .orElseThrow(() -> new IllegalArgumentException("invalid instruction destination: " + s));
    }

    private String getLabel() {
        String word = scan(false);
        if (word.endsWith(":"))
            return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }

    /**
     * Return the first word of line and remove it from line.
     * If there is no word, return "".
     *
     * @param comma remove the trailing comma if set to true
     */
    private String scan(boolean comma) {
        line = line.trim();

        int whiteSpacePosition = 0;
        while (whiteSpacePosition < line.length()) {
            if (Character.isWhitespace(line.charAt(whiteSpacePosition)))
                break;
            whiteSpacePosition++;
        }

        String word = line.substring(0, whiteSpacePosition);
        line = line.substring(whiteSpacePosition);
        if (comma) {
            if (word.endsWith(","))
                return word.substring(0, word.length() - 1);
            throw new IllegalArgumentException("Expected a comma after " + word);
        }
        return word;
    }
}