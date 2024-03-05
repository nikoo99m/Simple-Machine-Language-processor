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

class DivInstructionTest {

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
    void divideWithOperandRegisterAndCheckQuotient() {
        registers.set(AX, 10);
        registers.set(BX, 2);
        Instruction instruction = new DivInstruction(null, new OperandRegister(BX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(5, machine.getRegisters().get(AX));
    }
    @Test
    void divideWithOperandRegisterAndCheckWhenTheCombinationOfAxAndDxIsMoreThan32Bits() {
        registers.set(AX, 1);
        registers.set(DX, 1);

        registers.set(BX, 10);
        Instruction instruction = new DivInstruction(null, new OperandRegister(BX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(429496729, machine.getRegisters().get(AX));
        Assertions.assertEquals(7, machine.getRegisters().get(DX));
    }
    @Test
    void divideWithOperandRegisterAndCheckRemainder() {
        registers.set(AX, 10);
        registers.set(BX, 3);
        Instruction instruction = new DivInstruction(null, new OperandRegister(BX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(1, machine.getRegisters().get(DX));
    }

    @Test
    void divideWithOperandMemoryCellAndCheckQuotient() {
        registers.set(AX, 18);
        machine.getMemory().set(0, 2);
        Instruction instruction = new DivInstruction(null, new OperandMemory(0, machine.getMemory()));
        instruction.execute(machine);
        Assertions.assertEquals(9, machine.getRegisters().get(AX));
    }

    @Test
    void divideWithMemoryCellAndCheckRemainder() {
        registers.set(AX, 18);
        machine.getMemory().set(0, 4);
        Instruction instruction = new DivInstruction(null, new OperandMemory(0, machine.getMemory()));
        instruction.execute(machine);
        Assertions.assertEquals(2, machine.getRegisters().get(DX));
    }

    static Stream<Arguments> provideTestDataForNotEquals() {
        Machine machine = new Machine(10);
        Registers registers = machine.getRegisters();
        return Stream.of(
                Arguments.of(new DivInstruction("div1", new OperandRegister(AX, registers)),
                        new DivInstruction("div2", new OperandRegister(AX, registers))),

                Arguments.of((new DivInstruction("div1", new OperandRegister(AX, registers))),
                        new DivInstruction("div1", new OperandRegister(BX, registers))),

                Arguments.of((new DivInstruction("div1", new OperandMemory(0, machine.getMemory()))),
                        new DivInstruction("div1", new OperandMemory(3, machine.getMemory()))));


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
                Arguments.of(new DivInstruction("div1", new OperandRegister(AX, registers)),
                        new DivInstruction("div1", new OperandRegister(AX, registers))),

                Arguments.of((new DivInstruction("div1", new OperandMemory(0, machine.getMemory()))),
                        new DivInstruction("div1", new OperandMemory(0, machine.getMemory()))));
    }

    @ParameterizedTest
    @MethodSource("provideTestDataForEquals")
    public void testEquals(Instruction obj1, Instruction obj2) {

        assertEquals(obj1, obj2);
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void testToString() {

        Instruction instruction = new DivInstruction(null, new OperandMemory(7, machine.getMemory()));
        String result = instruction.toString();

        assertEquals("div [7]", result);
    }

    @Test
    public void testToStringTwo() {

        Instruction instruction = new DivInstruction(null, new OperandRegister(BX, registers));
        String result = instruction.toString();

        assertEquals("div BX", result);
    }

    @Test
    public void testGetSize() {

        Instruction instruction = new DivInstruction(null, new OperandRegister(BX, registers));
        int result = instruction.getSize();

        assertEquals(1, result);
    }

    @Test
    public void testGetSizeTwo() {

        Instruction instruction = new DivInstruction(null, new OperandMemoryWithBase(2, machine.getMemory(), BX, registers));
        int result = instruction.getSize();

        assertEquals(2, result);
    }

    @Test
    public void testGetSizeThree() {

        Instruction instruction = new DivInstruction(null, new OperandMemory(2, machine.getMemory()));
        int result = instruction.getSize();

        assertEquals(2, result);
    }

    @Test
    public void testExecuteHandlesDivisionByZero() throws ArithmeticException {
        registers.set(AX, 10);
        registers.set(BX, 0);

        Instruction instruction = new DivInstruction(null, new OperandRegister(Registers.RegisterNameImpl.BX, registers));
        try {
            instruction.execute(machine);
            fail("expected AromaticException to be thrown");
        } catch (ArithmeticException e) {
            System.out.println("Division by zero error occurred.");
        }
    }
}


