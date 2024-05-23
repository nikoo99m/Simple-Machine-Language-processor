package sml;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Represents Registers in Machine which contains a collection of registers.
 * and contains behaviours to modify and access the values stored in these registers.
 *
 * @author nikoo99m
 */
public final class Registers {
    private final Map<RegisterNameImpl, Integer> registers = new HashMap<>();
    /**
     * Represents register names.
     */
    public enum RegisterNameImpl implements RegisterName {
        AX, BX, CX, DX, SP, BP;
    }

    public Registers() {

        // answer: Calling methods in constructors is not recommended because
        //         if a method called within the superclass constructor is overridden in the subclass,
        //         the subclass's version of the method might run before the subclass has finished initializing.
        //         so it may cause unexpected behaviour especially when methods are overridden in subclasses.
        //         However, provided that this class is final it cannot be subclassed or extended. Additionally, in this case does not lead to a problem although the method clear is public
        //         as long as it doesn't rely on the subclass's state, there's no risk of unintended behavior due to method overriding.
        //         also it initializes the registers with default values, which is necessary for the Registers class.
        clear();
    }
    /**
     * Clears all registers, setting their values to zero.
     */
    public void clear() {
        for (RegisterNameImpl register : RegisterNameImpl.values())
            registers.put(register, 0);
    }
    /**
     *  parses a string representation of a register name into the corresponding enum constant.
     *
     * @param s the string representation of the register name.
     * @return an Optional containing the corresponding RegisterName, if found.
     */
    public Optional<RegisterName> parseRegisterName(String s) {
        return Stream.of(RegisterNameImpl.values())
                .filter(r -> r.name().equals(s))

                // answer: The purpose of the next line of code, .map(r -> r), is to cast each element of the stream from RegisterNameImpl to RegisterName.
                //         However, since RegisterNameImpl already implements RegisterName,
                //         this mapping operation doesn't change the elements of the stream.
                //         Therefore, it's unnecessary in this context.
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
     * Gets the value stored in the register.
     *
     * @param register register name
     * @return value
     */
    public int get(RegisterName register) {

        // answer: A ClassCastException exception is raised since the given key was cast to a class or subclass of which it is not an instance of.
        //         In this case, provided that the destination type that is RegisterNameImpl is an enum, the given key must only be of the type RegisterNameImpl and nothing else.
        //         A subclass of RegisterNameImpl is also not possible since enums are implicitly final in Java.
        //
        return registers.get((RegisterNameImpl)register);
    }


    /**
     * Checks whether two object of Registers are equal or not.
     *
     * @param o the object which to compare.
     * @return true if this object is the same as the o ; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Registers other) {
            return registers.equals(other.registers);
        }
        return false;
    }
    /**
     * Generates a hash code value for the Registers object.
     *
     * @return a hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(registers);
    }
    /**
     * Represents string representation of the Registers object.
     *
     * @return a string containing register names and their values.
     */
    @Override
    public String toString() {
        return registers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + " = " + e.getValue())
                .collect(Collectors.joining(", ", "[", "]")) ;
    }
}
