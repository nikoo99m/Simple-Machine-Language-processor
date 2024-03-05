package sml.instruction;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import sml.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class JneInstructionTest {

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
    void jumpNotEqualsReturnsJumpCounterWhenZeroFlagIsFalse() {

        Mockery context = new Mockery();

        IMachine machineMock = context.mock(IMachine.class);
        IFlags flagsMock = context.mock(IFlags.class);
        context.checking(new Expectations() {{
            oneOf(machineMock).getFlags();
            will(returnValue(flagsMock));

            oneOf(machineMock).getOffset(with(any(String.class)));
            will(returnValue(3));

            oneOf(flagsMock).getZF();
            will(returnValue(false));
        }});

        Instruction instruction = new JneInstruction(null, "label1");
        int programCounter = instruction.execute(machineMock);
        assertEquals(programCounter, 3);
    }

    @Test
    void jumpNotEqualsReturnsNextProgramCounterIncrementWhenZeroFlagIsTrue() {

        Mockery context = new Mockery();

        IMachine machineMock = context.mock(IMachine.class);
        IFlags flagsMock = context.mock(IFlags.class);
        context.checking(new Expectations() {{
            oneOf(machineMock).getFlags();
            will(returnValue(flagsMock));

            oneOf(machineMock).getOffset(with(any(String.class)));
            will(returnValue(3));

            oneOf(flagsMock).getZF();
            will(returnValue(true));
        }});

        Instruction instruction = new JneInstruction(null, "label1");
        int programCounter = instruction.execute(machineMock);
        assertEquals(programCounter, 1);
    }

    static Stream<Arguments> provideTestDataForNotEquals() {
        Machine machine = new Machine(10);
        Registers registers = machine.getRegisters();
        return Stream.of(
                Arguments.of(new JneInstruction("label1", "label2"),
                        new JneInstruction("labelx", "label2")),

                Arguments.of(new JneInstruction("label1", "label2"),
                        new JneInstruction("label1", "labelx"))
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
                Arguments.of(new JneInstruction("label1", "label2"),
                        new JneInstruction("label1", "label2"))
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

        Instruction instruction = new JleInstruction("f3", "exit");
        String result = instruction.toString();

        assertEquals("f3: jle exit", result);
    }
    @Test
    public void testgetSize() {

        Instruction instruction = new JneInstruction(null, "f2");
        int result = instruction.getSize();

        assertEquals(1, result);
    }
}
