package sml;
/**
 * Defines the interface for Machine, facilitating testing using JMock.
 * specially to test the 'execute' behavior of jump instructions.
 */
public interface IMachine {
    public void execute();
    public IFlags getFlags();
    public int getOffset(String goToLabel);
    public Registers getRegisters();
}
