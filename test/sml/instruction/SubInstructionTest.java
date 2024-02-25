package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.*;

import static org.junit.jupiter.api.Assertions.*;
import static sml.Registers.RegisterNameImpl.*;
import static sml.Registers.RegisterNameImpl.DX;

class SubInstructionTest {

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
        Instruction instruction = new SubInstruction(null, new OperandRegister(BX, registers), new OperandRegister(AX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(1, machine.getRegisters().get(BX));
    }

    @Test
    void executeValidTwo() {
        registers.set(AX, 5);
        registers.set(BX, 6);
        Instruction instruction = new SubInstruction(null, new OperandMemoryWithBase(2, machine.getMemory(), BX, registers), new OperandRegister(AX, registers));
        instruction.execute(machine);
        Assertions.assertEquals(-5, machine.getMemory().get(8));
    }

    @Test
    void executeValidThree() {
        registers.set(DX, 6);
        Instruction instruction = new SubInstruction(null, new OperandRegister(DX, registers), new OperandImmediate(4));
        instruction.execute(machine);
        Assertions.assertEquals(2, machine.getRegisters().get(DX));
    }
}