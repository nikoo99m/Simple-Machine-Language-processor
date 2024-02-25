package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sml.*;

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
    void divideWithOperandRegisterAndCheckRemainder(){
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

}