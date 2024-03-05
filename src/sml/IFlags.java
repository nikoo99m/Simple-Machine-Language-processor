package sml;
/**
 * Defines the interface for Flags, facilitating testing using JMock.
 * specially to test the 'execute' behavior of jump instructions.
 */
public interface IFlags {
    boolean getZF();
    boolean getSF();
    public void setZF(boolean zero);
    public void setSF(boolean sign);
}
