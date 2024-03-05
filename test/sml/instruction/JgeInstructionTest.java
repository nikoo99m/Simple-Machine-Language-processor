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

import org.jmock.Mockery;
import org.jmock.Expectations;

class JgeInstructionTest {

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
    void jumpGreaterEqualsReturnsNextProgramCounterIncrementWhenSignFlagIsTrueAndZeroFlagIsFalse() {

        Mockery context = new Mockery();

        IMachine machineMock = context.mock(IMachine.class);
        IFlags flagsMock = context.mock(IFlags.class);
        context.checking(new Expectations() {{
            oneOf(machineMock).getFlags();
            will(returnValue(flagsMock));

            oneOf(flagsMock).getSF();
            will(returnValue(true));

            oneOf(flagsMock).getZF();
            will(returnValue(false));
        }});

        Instruction instruction = new JgeInstruction(null, "label1");
        int programCounter = instruction.execute(machineMock);
        assertEquals(programCounter, 1);
    }

    @Test
    void jumpGreaterEqualsReturnsJumpCounterWhenZeroFlagIsTrue() {

        Mockery context = new Mockery();

        IMachine machineMock = context.mock(IMachine.class);
        IFlags flagsMock = context.mock(IFlags.class);

        context.checking(new Expectations() {{
            oneOf(machineMock).getFlags();
            will(returnValue(flagsMock));

            oneOf(machineMock).getOffset(with(any(String.class)));
            will(returnValue(3));

            oneOf(flagsMock).getSF();
            will(returnValue(true));

            oneOf(flagsMock).getZF();
            will(returnValue(true));
        }});

        Instruction instruction = new JgeInstruction(null, "label1");
        int programCounter = instruction.execute(machineMock);
        assertEquals(programCounter, 3);
    }

    @Test
    void jumpGreaterEqualsReturnsJumpCounterWhenSignFlagIsFalse() {

        Mockery context = new Mockery();

        IMachine machineMock = context.mock(IMachine.class);
        IFlags flagsMock = context.mock(IFlags.class);
        context.checking(new Expectations() {{
            oneOf(machineMock).getFlags();
            will(returnValue(flagsMock));

            oneOf(machineMock).getOffset(with(any(String.class)));
            will(returnValue(3));

            oneOf(flagsMock).getSF();
            will(returnValue(false));

            oneOf(flagsMock).getZF();
            will(returnValue(false));
        }});

        Instruction instruction = new JgeInstruction(null, "label1");
        int programCounter = instruction.execute(machineMock);
        assertEquals(programCounter, 3);
    }

    static Stream<Arguments> provideTestDataForNotEquals() {
        Machine machine = new Machine(10);
        Registers registers = machine.getRegisters();
        return Stream.of(
                Arguments.of(new JgeInstruction("label1", "label2"),
                        new JgeInstruction("labelx", "label2")),

                Arguments.of(new JgeInstruction("label1", "label2"),
                        new JgeInstruction("label1", "labelx"))
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
                Arguments.of(new JgeInstruction("label1", "label2"),
                        new JgeInstruction("label1", "label2"))
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

        Instruction instruction = new JgeInstruction(null, "success");
        String result = instruction.toString();

        assertEquals("jge success", result);
    }
    @Test
    public void testgetSize() {

        Instruction instruction = new JgeInstruction(null, "f2");
        int result = instruction.getSize();

        assertEquals(1, result);
    }


}