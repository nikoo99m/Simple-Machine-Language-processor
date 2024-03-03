package sml;

public interface IMachine {
    public void execute();
    public IFlags getFlags();
    public int getOffset(String goToLabel);
    public Registers getRegisters();
}
