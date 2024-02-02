package sml;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO: write a JavaDoc for the class

/**
 *
 * @author ...
 */
public final class Registers {
    private final Map<RegisterNameImpl, Integer> registers = new HashMap<>();

    public enum RegisterNameImpl implements RegisterName {
        AX, BX, CX, DX, SP, BP;
    }

    public Registers() {
        //TODO: In general, it is not recommended to call
        //      methods of the class in constructors.
        //      Why is it? And why is it safe in this case?
        //      Write a short explanation.
        clear();
    }

    public void clear() {
        for (RegisterNameImpl register : RegisterNameImpl.values())
            registers.put(register, 0);
    }

    public Optional<RegisterName> parseRegisterName(String s) {
        return Stream.of(RegisterNameImpl.values())
                .filter(r -> r.name().equals(s))
                //TODO: The next line of code does not seem to do much
                //      (r is mapped to r).
                //      What is the purpose of the next line of code?
                //      Write a short explanation.
                .<RegisterName>map(r -> r)
                .findAny();
    }

    /**
     * Sets the given register to the value.
     *
     * @param register register name
     * @param value new value
     */
    public void set(RegisterName register, int value) {
        registers.put((RegisterNameImpl)register, value);
    }

    /**
     * Returns the value stored in the register.
     *
     * @param register register name
     * @return value
     */
    public int get(RegisterName register) {
        //TODO: Explain what happens if register is not an instance of RegisterName.
        //      Consider, for example, a call of the form registers.get(() -> "NEW").
        return registers.get((RegisterNameImpl)register);
    }

    // TODO: use pattern matching for instanceof
    // https://docs.oracle.com/en/java/javase/14/language/pattern-matching-instanceof-operator.html
    @Override
    public boolean equals(Object o) {
        if (o instanceof Registers) {
            Registers other = (Registers) o;
            return registers.equals(other.registers);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return registers.hashCode();
    }

    @Override
    public String toString() {
        return registers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + " = " + e.getValue())
                .collect(Collectors.joining(", ", "[", "]")) ;
    }
}
