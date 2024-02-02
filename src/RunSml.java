import sml.Machine;
import sml.Translator;

import java.io.IOException;

public class RunSml {
    /**
     * Initialises the system and executes the program.
     *
     * @param args name of the file containing the program text.
     */
    public static void main(String... args) {
        if (args.length != 1) {
            System.err.println("Incorrect number of arguments - RunSml <file> - required");
            System.exit(-1);
        }

        try {
            Translator t = new Translator(args[0]);
            Machine m = new Machine(0x40_000);
            t.readAndTranslate(m);

            System.out.println("Here is the program; it has " + m.getProgram().size() + " instructions.");
            System.out.println(m);

            System.out.println("Beginning program execution.");
            m.execute();
            System.out.println("Ending program execution.");

            System.out.println("Values of registers at program termination: " + m.getRegisters() + ".");
            System.out.println("Contents of memory after program termination:\n" + m.getMemory());
        }
        catch (IOException e) {
            System.out.println("Error reading the program from " + args[0]);
        }
    }
}
