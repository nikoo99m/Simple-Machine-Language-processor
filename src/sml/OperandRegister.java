package sml;

import java.util.Objects;
import java.util.Optional;

public class OperandRegister implements InstructionSource, InstructionDestination {
    private final Registers registers;
    private final RegisterName registerName;

    public static Optional<OperandRegister> parseOperandRegister(String s, Registers registers) {
        return registers.parseRegisterName(s)
                .map(r -> new OperandRegister(r, registers));
    }

    public OperandRegister(RegisterName registerName, Registers registers) {
        this.registers = registers;
        this.registerName = registerName;
    }


    @Override
    public int getValue() {
        return registers.get(registerName);
    }

    @Override
    public void setValue(int value) {
        registers.set(registerName, value);
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public String toString() {
        return registerName.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof OperandRegister other) {
            return this.registers.equals(other.registers)
                    && this.registerName.name().equals(other.registerName.name());
        }
        return false;
    }
    @Override
    public int hashCode() {
        return Objects.hash(registers, registerName);
    }
}
