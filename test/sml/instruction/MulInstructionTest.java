package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.*;

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
    }

    @Test
    void multiplyWithAxAndOperandMemoryCell() {
        registers.set(AX, 9);
        machine.getMemory().set(0, 5);
        Instruction instruction = new MulInstruction(null, new OperandMemory(0, machine.getMemory()));
        instruction.execute(machine);
        Assertions.assertEquals(45, machine.getRegisters().get(AX));
    }
}