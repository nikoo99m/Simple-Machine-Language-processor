package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.*;

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
        Instruction instruction = new CmpInstruction(null, new OperandRegister(BX, registers), new OperandRegister(AX, registers), machine.getFlags());
        instruction.execute(machine);
        Assertions.assertEquals(true, machine.getFlags().getZF());
        Assertions.assertEquals(false, machine.getFlags().getSF());
    }

    @Test
    void compareWithOperandRegisterAndCheckGreaterThan() {
        registers.set(AX, 5);
        registers.set(BX, 9);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(BX, registers), new OperandRegister(AX, registers), machine.getFlags());
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(false, machine.getFlags().getSF());
    }

    @Test
    void compareWithOperandRegisterAndCheckLessThan() {
        registers.set(AX, 9);
        registers.set(BX, 5);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(BX, registers), new OperandRegister(AX, registers), machine.getFlags());
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(true, machine.getFlags().getSF());
    }

    @Test
    void compareOperandMemoryWithOperandRegisterAndCheckEquality() {
        registers.set(AX, 5);
        machine.getMemory().set(0, 5);
        Instruction instruction = new CmpInstruction(null, new OperandMemory(0, machine.getMemory()), new OperandRegister(AX, registers), machine.getFlags());
        instruction.execute(machine);
        Assertions.assertEquals(true, machine.getFlags().getZF());
        Assertions.assertEquals(false, machine.getFlags().getSF());
    }

    @Test
    void compareOperandMemoryWithOperandRegisterAndCheckGreaterThan() {
        registers.set(AX, 8);
        machine.getMemory().set(0, 5);
        Instruction instruction = new CmpInstruction(null, new OperandMemory(0, machine.getMemory()), new OperandRegister(AX, registers), machine.getFlags());
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(true, machine.getFlags().getSF());
    }

    @Test
    void compareOperandMemoryWithOperandRegisterAndCheckLessThan() {
        registers.set(AX, 8);
        machine.getMemory().set(0, 5);
        Instruction instruction = new CmpInstruction(null, new OperandMemory(0, machine.getMemory()), new OperandRegister(AX, registers), machine.getFlags());
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(true, machine.getFlags().getSF());
    }

    @Test
    void compareOperandRegisterWithOperandMemoryAndCheckEquality() {
        registers.set(AX, 5);
        machine.getMemory().set(0, 5);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(AX, registers), new OperandMemory(0, machine.getMemory()), machine.getFlags());
        instruction.execute(machine);
        Assertions.assertEquals(true, machine.getFlags().getZF());
        Assertions.assertEquals(false, machine.getFlags().getSF());
    }

    @Test
    void compareOperandRegisterWithOperandMemoryAndCheckGreaterThan() {
        registers.set(AX, 5);
        machine.getMemory().set(0, 8);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(AX, registers), new OperandMemory(0, machine.getMemory()), machine.getFlags());
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(true, machine.getFlags().getSF());
    }

    @Test
    void compareOperandRegisterWithOperandMemoryAndCheckLessThan() {
        registers.set(AX, 5);
        machine.getMemory().set(0, 8);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(AX, registers), new OperandMemory(0, machine.getMemory()), machine.getFlags());
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(true, machine.getFlags().getSF());
    }

    @Test
    void compareOperandRegisterWithIntegerNumbersAndCheckEquality() {
        registers.set(AX, 6);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(AX, registers), new OperandImmediate(6), machine.getFlags());
        instruction.execute(machine);
        Assertions.assertEquals(true, machine.getFlags().getZF());
        Assertions.assertEquals(false, machine.getFlags().getSF());
    }

    @Test
    void compareOperandRegisterWithIntegerNumbersAndCheckGreaterThan() {
        registers.set(AX, 7);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(AX, registers), new OperandImmediate(2), machine.getFlags());
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(false, machine.getFlags().getSF());
    }
    @Test
    void compareOperandRegisterWithIntegerNumbersAndCheckLessThan() {
        registers.set(AX, 5);
        Instruction instruction = new CmpInstruction(null, new OperandRegister(AX, registers), new OperandImmediate(100), machine.getFlags());
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(true, machine.getFlags().getSF());
    }


    @Test
    void compareMemoryCellWithIntegerNumbersAndCheckEquality() {
        machine.getMemory().set(0, 5);
        Instruction instruction = new CmpInstruction(null, new OperandMemory(0, machine.getMemory()), new OperandImmediate(5), machine.getFlags());
        instruction.execute(machine);
        Assertions.assertEquals(true, machine.getFlags().getZF());
        Assertions.assertEquals(false, machine.getFlags().getSF());
    }

    @Test
    void compareMemoryCellWithIntegerNumbersAndCheckGreaterThan() {
        machine.getMemory().set(0, 7);
        Instruction instruction = new CmpInstruction(null, new OperandMemory(0, machine.getMemory()), new OperandImmediate(5), machine.getFlags());
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(false, machine.getFlags().getSF());
    }
    @Test
    void compareMemoryCellWithIntegerNumbersAndCheckLessThan() {
        machine.getMemory().set(0, 2);
        Instruction instruction = new CmpInstruction(null, new OperandMemory(0, machine.getMemory()), new OperandImmediate(5), machine.getFlags());
        instruction.execute(machine);
        Assertions.assertEquals(false, machine.getFlags().getZF());
        Assertions.assertEquals(true, machine.getFlags().getSF());
    }
}