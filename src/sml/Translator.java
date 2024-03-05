package sml;

import sml.instruction.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
    private final IOpcodeProvider opcodeProvider;
    private final IInstructionFactory instructionFactory;

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

    public Translator(String fileName, IOpcodeProvider opcodeProvider, IInstructionFactory instructionFactory) {
        this.fileName = fileName;
        this.opcodeProvider = opcodeProvider;
        this.instructionFactory = instructionFactory;
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

        var splits = scan();
        String opcode = splits[0];
        Class<? extends Instruction> instructionClass = opcodeProvider.getInstructionClass(opcode);
        return instructionFactory.getInstruction(label, machine, instructionClass, splits, opcode);
        // TODO: add code for all other types of instructions // DONE
        // TODO: Then, replace the switch by using the Reflection API // DONE
        // TODO: Next, use dependency injection to allow this machine class
        //       to work with different sets of opcodes (different CPUs) // DONE
    }

    private String[] scan() {
        return Arrays.stream(line.trim().split(" "))
                .map(x -> x.replace(",", "").trim())
                .toArray(String[]::new);
    }

    private String getLabel() {
        if (line.contains(":")) {
            var label = line.split(":")[0];
            line = line.substring(line.indexOf(":") + 1).trim();
            return label;
        } else {
            return null;
        }
    }
}