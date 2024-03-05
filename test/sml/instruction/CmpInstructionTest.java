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

class CmpInstructionTest {
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
    void compareWithOperandRegistersAndCheckEquality() {
        registers.set(AX, 5);
        registers.set(BX, 5);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(BX, registers), new OperandRegister(AX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(true, machine.getFlags().getZF());
        Assertions.assertEquals(false, machine.getFlags().getSF());
    }

    @Test
    void compareWithOperandRegisterAndCheckGreaterThan() {
        registers.set(AX, 5);
        registers.set(BX, 9);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(BX, registers), new OperandRegister(AX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(false, machine.getFlags().getSF());
    }

    @Test
    void compareWithOperandRegisterAndCheckLessThan() {
        registers.set(AX, 9);
        registers.set(BX, 5);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(BX, registers), new OperandRegister(AX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(true, machine.getFlags().getSF());
    }

    @Test
    void compareOperandMemoryWithOperandRegisterAndCheckEquality() {
        registers.set(AX, 5);
        machine.getMemory().set(0, 5);
        Instruction instruction = new CmpInstruction(null, new OperandMemory(0, machine.getMemory()), new OperandRegister(AX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(true, machine.getFlags().getZF());
        Assertions.assertEquals(false, machine.getFlags().getSF());
    }

    @Test
    void compareOperandMemoryWithOperandRegisterAndCheckGreaterThan() {
        registers.set(AX, 8);
        machine.getMemory().set(0, 5);
        Instruction instruction = new CmpInstruction(null, new OperandMemory(0, machine.getMemory()), new OperandRegister(AX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(true, machine.getFlags().getSF());
    }

    @Test
    void compareOperandMemoryWithOperandRegisterAndCheckLessThan() {
        registers.set(AX, 8);
        machine.getMemory().set(0, 5);
        Instruction instruction = new CmpInstruction(null, new OperandMemory(0, machine.getMemory()), new OperandRegister(AX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(true, machine.getFlags().getSF());
    }

    @Test
    void compareOperandRegisterWithOperandMemoryAndCheckEquality() {
        registers.set(AX, 5);
        machine.getMemory().set(0, 5);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(AX, registers), new OperandMemory(0, machine.getMemory()));
        instruction.execute(machine);
        Assertions.assertEquals(true, machine.getFlags().getZF());
        Assertions.assertEquals(false, machine.getFlags().getSF());
    }

    @Test
    void compareOperandRegisterWithOperandMemoryAndCheckGreaterThan() {
        registers.set(AX, 5);
        machine.getMemory().set(0, 8);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(AX, registers), new OperandMemory(0, machine.getMemory()));
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(true, machine.getFlags().getSF());
    }

    @Test
    void compareOperandRegisterWithOperandMemoryAndCheckLessThan() {
        registers.set(AX, 5);
        machine.getMemory().set(0, 8);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(AX, registers), new OperandMemory(0, machine.getMemory()));
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(true, machine.getFlags().getSF());
    }

    @Test
    void compareOperandRegisterWithIntegerNumbersAndCheckEquality() {
        registers.set(AX, 6);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(AX, registers), new OperandImmediate(6));
        instruction.execute(machine);
        Assertions.assertEquals(true, machine.getFlags().getZF());
        Assertions.assertEquals(false, machine.getFlags().getSF());
    }

    @Test
    void compareOperandRegisterWithIntegerNumbersAndCheckGreaterThan() {
        registers.set(AX, 7);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(AX, registers), new OperandImmediate(2));
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(false, machine.getFlags().getSF());
    }
    @Test
    void compareOperandRegisterWithIntegerNumbersAndCheckLessThan() {
        registers.set(AX, 5);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(AX, registers), new OperandImmediate(100));
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(true, machine.getFlags().getSF());
    }


    @Test
    void compareMemoryCellWithIntegerNumbersAndCheckEquality() {
        machine.getMemory().set(0, 5);
        Instruction instruction = new CmpInstruction(null, new OperandMemory(0, machine.getMemory()), new OperandImmediate(5));
        instruction.execute(machine);
        Assertions.assertEquals(true, machine.getFlags().getZF());
        Assertions.assertEquals(false, machine.getFlags().getSF());
    }

    @Test
    void compareMemoryCellWithIntegerNumbersAndCheckGreaterThan() {
        machine.getMemory().set(0, 7);
        Instruction instruction = new CmpInstruction(null, new OperandMemory(0, machine.getMemory()), new OperandImmediate(5));
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(false, machine.getFlags().getSF());
    }
    @Test
    void compareMemoryCellWithIntegerNumbersAndCheckLessThan() {
        machine.getMemory().set(0, 2);
        Instruction instruction = new CmpInstruction(null, new OperandMemory(0, machine.getMemory()), new OperandImmediate(5));
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(true, machine.getFlags().getSF());
    }
    static Stream<Arguments> provideTestDataForNotEquals() {
        Machine machine = new Machine(10);
        Registers registers = machine.getRegisters();
        return Stream.of(
                Arguments.of(new CmpInstruction("cmp1", new OperandRegister(BX, registers), new OperandRegister(AX, registers)),
                        new CmpInstruction("cmp2", new OperandRegister(BX, registers), new OperandRegister(AX, registers))),

                Arguments.of((new CmpInstruction("cmp1", new OperandRegister(AX, registers), new OperandRegister(AX, registers))),
                        new CmpInstruction("cmp1", new OperandRegister(BX, registers), new OperandRegister(AX, registers))),

                Arguments.of((new CmpInstruction("cmp1", new OperandRegister(BX, registers), new OperandRegister(AX, registers))),
                        new CmpInstruction("cmp1", new OperandRegister(BX, registers), new OperandRegister(BX, registers))),

                Arguments.of((new CmpInstruction("cmp1", new OperandRegister(BX, registers), new OperandImmediate(10))),
                        new CmpInstruction("cmp1", new OperandRegister(BX, registers), new OperandImmediate(2))),

                Arguments.of((new CmpInstruction("cmp1", new OperandMemoryWithBase(2, machine.getMemory(), BX, registers), new OperandRegister(AX, registers))),
                        new CmpInstruction("cmp1",new OperandMemoryWithBase(1, machine.getMemory(), BX, registers), new OperandRegister(AX, registers))),

                Arguments.of((new CmpInstruction("cmp1", new OperandMemoryWithBase(1, machine.getMemory(), BX, registers), new OperandRegister(AX, registers))),
                        new CmpInstruction("cmp1",new OperandMemoryWithBase(1, machine.getMemory(), AX, registers), new OperandRegister(AX, registers)))
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
                Arguments.of(new CmpInstruction("cmp1", new OperandRegister(BX, registers), new OperandRegister(AX, registers)),
                        new CmpInstruction("cmp1", new OperandRegister(BX, registers), new OperandRegister(AX, registers))),

                Arguments.of((new CmpInstruction("cmp1", new OperandRegister(BX, registers), new OperandImmediate(10))),
                        new CmpInstruction("cmp1", new OperandRegister(BX, registers), new OperandImmediate(10))),

                Arguments.of((new CmpInstruction("cmp1", new OperandMemoryWithBase(1, machine.getMemory(), BX, registers), new OperandRegister(AX, registers))),
                        new CmpInstruction("cmp1",new OperandMemoryWithBase(1, machine.getMemory(), BX, registers), new OperandRegister(AX, registers)))
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

        Instruction instruction = new CmpInstruction("f4", new OperandRegister(DX, registers), new OperandImmediate(10));
        String result = instruction.toString();

        assertEquals("f4: cmp DX, 10", result);
    }

    @Test
    public void testToStringTwo() {

        Instruction instruction = new CmpInstruction(null, new OperandRegister(BX, registers), new OperandRegister(AX, registers));
        String result = instruction.toString();

        assertEquals("cmp BX, AX", result);
    }
    @Test
    public void testToStringThree() {

        Instruction instruction = new CmpInstruction(null, new OperandMemoryWithBase(2, machine.getMemory(), BX, registers), new OperandRegister(AX, registers));
        String result = instruction.toString();

        assertEquals("cmp [BX + 2], AX", result);
    }
    @Test
    public void testGetSize() {

        Instruction instruction = new CmpInstruction(null, new OperandRegister(DX, registers), new OperandImmediate(100));
        int result = instruction.getSize();

        assertEquals(2, result);
    }
    @Test
    public void testGetSizeTwo() {

        Instruction instruction = new CmpInstruction(null, new OperandRegister(BX, registers), new OperandRegister(AX, registers));
        int result = instruction.getSize();

        assertEquals(1, result);
    }
    @Test
    public void testGetSizeThree() {

        Instruction instruction = new CmpInstruction(null, new OperandMemoryWithBase(2, machine.getMemory(), BX, registers), new OperandRegister(AX, registers));
        int result = instruction.getSize();

        assertEquals(2 , result);
    }


}