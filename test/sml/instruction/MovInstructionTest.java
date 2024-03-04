package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import sml.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static sml.Registers.RegisterNameImpl.*;

class MovInstructionTest {
    private Machine machine;
    private Registers registers;

    @BeforeEach
    void setUp() {
        machine = new Machine(10);
        registers = machine.getRegisters();
        //...
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
    }

    @Test
    void executeValid() {
        registers.set(AX, 5);
        registers.set(BX, 6);
        Instruction instruction = new MovInstruction(null, new OperandRegister(BX, registers), new OperandRegister(AX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(5, machine.getRegisters().get(BX));
    }

    @Test
    void executeValidTwo() {
        registers.set(AX, 5);
        registers.set(BX, 6);
        Instruction instruction = new MovInstruction(null, new OperandMemoryWithBase(2, machine.getMemory(), BX, registers), new OperandRegister(AX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(5, machine.getMemory().get(8));
    }

    @Test
    void executeValidThree() {
        registers.set(DX, 6);
        Instruction instruction = new MovInstruction(null, new OperandRegister(DX, registers), new OperandImmediate(100));
        instruction.execute(machine);
        Assertions.assertEquals(100, machine.getRegisters().get(DX));
    }


    static Stream<Arguments> provideTestDataForNotEquals() {
        Machine machine = new Machine(10);
        Registers registers = machine.getRegisters();
        return Stream.of(
                Arguments.of(new MovInstruction("Mov1", new OperandRegister(BX, registers), new OperandRegister(AX, registers)),
                        new MovInstruction("Mov2", new OperandRegister(BX, registers), new OperandRegister(AX, registers))),

                Arguments.of((new MovInstruction("Mov1", new OperandRegister(AX, registers), new OperandRegister(AX, registers))),
                        new MovInstruction("Mov1", new OperandRegister(BX, registers), new OperandRegister(AX, registers))),

                Arguments.of((new MovInstruction("Mov1", new OperandRegister(BX, registers), new OperandRegister(AX, registers))),
                        new MovInstruction("Mov1", new OperandRegister(BX, registers), new OperandRegister(BX, registers))),

                Arguments.of((new MovInstruction("Mov1", new OperandRegister(BX, registers), new OperandImmediate(10))),
                        new MovInstruction("Mov1", new OperandRegister(BX, registers), new OperandImmediate(2))),

                Arguments.of((new MovInstruction("Mov1", new OperandMemoryWithBase(2, machine.getMemory(), BX, registers), new OperandRegister(AX, registers))),
                        new MovInstruction("Mov1",new OperandMemoryWithBase(1, machine.getMemory(), BX, registers), new OperandRegister(AX, registers))),

                Arguments.of((new MovInstruction("Mov1", new OperandMemoryWithBase(1, machine.getMemory(), BX, registers), new OperandRegister(AX, registers))),
                        new MovInstruction("Mov1",new OperandMemoryWithBase(1, machine.getMemory(), AX, registers), new OperandRegister(AX, registers)))
        );
    }
    @ParameterizedTest
    @MethodSource("provideTestDataForNotEquals")
    public void testNotEquals(Instruction obj1, Instruction obj2) {
        assertNotEquals(obj1, obj2);
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForNotEquals")
    public void testHashCodeNotEquals(Instruction obj1, Instruction obj2) {
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
    }

    static Stream<Arguments> provideTestDataForEquals() {
        Machine machine = new Machine(10);
        Registers registers = machine.getRegisters();
        return Stream.of(
                Arguments.of(new MovInstruction("Mov1", new OperandRegister(BX, registers), new OperandRegister(AX, registers)),
                        new MovInstruction("Mov1", new OperandRegister(BX, registers), new OperandRegister(AX, registers))),

                Arguments.of((new MovInstruction("Mov1", new OperandRegister(BX, registers), new OperandImmediate(10))),
                        new MovInstruction("Mov1", new OperandRegister(BX, registers), new OperandImmediate(10))),

                Arguments.of((new MovInstruction("Mov1", new OperandMemoryWithBase(1, machine.getMemory(), BX, registers), new OperandRegister(AX, registers))),
                        new MovInstruction("Mov1",new OperandMemoryWithBase(1, machine.getMemory(), BX, registers), new OperandRegister(AX, registers)))
        );
    }
    @ParameterizedTest
    @MethodSource("provideTestDataForEquals")
    public void testEquals(Instruction obj1, Instruction obj2) {

        assertEquals(obj1, obj2);
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void testToString() {

        Instruction instruction = new MovInstruction(null, new OperandRegister(DX, registers), new OperandImmediate(100));
        String result = instruction.toString();

        assertEquals("mov DX, 100", result);
    }

    @Test
    public void testToStringtwo() {

        Instruction instruction = new MovInstruction(null, new OperandRegister(BX, registers), new OperandRegister(AX, registers));
        String result = instruction.toString();

        assertEquals("mov BX, AX", result);
    }
    @Test
    public void testToStringthree() {

        Instruction instruction = new MovInstruction(null, new OperandMemoryWithBase(2, machine.getMemory(), BX, registers), new OperandRegister(AX, registers));
        String result = instruction.toString();

        assertEquals("mov [BX + 2], AX", result);
    }
}

