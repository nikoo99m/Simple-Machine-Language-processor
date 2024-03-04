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

import static org.junit.jupiter.api.Assertions.*;
import static sml.Registers.RegisterNameImpl.*;
import static sml.Registers.RegisterNameImpl.DX;

class MulInstructionTest {

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
    void multiplyWithAxAndOperandRegister() {
        registers.set(AX, 5);
        registers.set(BX, 6);
        Instruction instruction = new MulInstruction(null, new OperandRegister(BX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(30, machine.getRegisters().get(AX));
        Assertions.assertEquals(0, machine.getRegisters().get(DX));
    }
    @Test
    void multiplyWithAxAndOperandRegisterWhenTheResultIsMoreThan32Bits() {
        registers.set(AX, 2147483647);
        registers.set(BX, 10);
        Instruction instruction = new MulInstruction(null, new OperandRegister(BX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(2147483638, machine.getRegisters().get(AX));
        Assertions.assertEquals(9, machine.getRegisters().get(DX));
    }

    @Test
    void multiplyWithAxAndOperandMemoryCell() {
        registers.set(AX, 9);
        machine.getMemory().set(0, 5);
        Instruction instruction = new MulInstruction(null, new OperandMemory(0, machine.getMemory()));
        instruction.execute(machine);
        Assertions.assertEquals(45, machine.getRegisters().get(AX));
    }

    static Stream<Arguments> provideTestDataForNotEquals() {
        Machine machine = new Machine(10);
        Registers registers = machine.getRegisters();
        return Stream.of(
                Arguments.of(new MulInstruction("mul1", new OperandRegister(AX, registers)),
                        new MulInstruction("mul2", new OperandRegister(AX, registers))),

                Arguments.of((new MulInstruction("mul1", new OperandRegister(AX, registers))),
                        new MulInstruction("mul1", new OperandRegister(BX, registers))),

                Arguments.of((new MulInstruction("mul1", new OperandMemory(0 ,machine.getMemory()))),
                        new MulInstruction("mul1", new OperandMemory(3, machine.getMemory()))));


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
                Arguments.of(new MulInstruction("mul1", new OperandRegister(AX, registers)),
                        new MulInstruction("mul1", new OperandRegister(AX, registers))),

                Arguments.of((new MulInstruction("mul1", new OperandMemory(0 ,machine.getMemory()))),
                        new MulInstruction("mul1", new OperandMemory(0, machine.getMemory()))));
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForEquals")
    public void testEquals(Instruction obj1, Instruction obj2) {

        assertEquals(obj1, obj2);
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void testToString() {

        Instruction instruction = new MulInstruction("f1", new OperandMemory(7, machine.getMemory()));
        String result = instruction.toString();

        assertEquals("f1: mul [7]", result);
    }

    @Test
    public void testToStringtwo() {

        Instruction instruction = new MulInstruction("f2", new OperandRegister(BX, registers));
        String result = instruction.toString();

        assertEquals("f2: mul BX", result);
    }
}