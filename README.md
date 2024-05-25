
# Simple (Small) Machine Language (SML)

<img src="sml.jpg" alt="sml"/>

## The problem

This project is about developing an interpreter for SML.

The machine has six 32-bit registers, named
`AX`, `BX`, `CX`, `DX`, `SP` and `BP`. Registers are simple storage areas in computer memory, much like variables.

The machine also has two flags, `ZF` and `SF`. Each flag is a single-bit value (`true` or `false`): 

+ `ZF` is true if, in the most recently executed comparison instruction, the two operands were equal numbers, and false if they were not equal; 
+ `SF` is true if, in the most recently executed comparison instruction, the first operand was smaller than the second, and false if it was larger than or equal. 

Note that only comparison instructions change the flags, while all other operations preserve the flags.

The machine also has memory, which consists of 32-bit cells, each referred to by an address: the first cell has address `0`,
the second cell has address `1`, etc.

The general form of a machine language instruction is:

```
	[label:] opcode parameter-list
```
where

* `label` - is the optional label for the line. It is a sequence of non-whitespace characters.  
	Other instructions might “jump” to that label. Labels are denoted by `L` in the instruction table below.
* `opcode` - is the actual instruction name (operation code).
	In `SML`, there are instructions for adding, multiplying and so on, for storing integers, 
    and for conditionally branching to other labels  (like an `if` statement).
* `parameter-list` - is the comma-separated list of parameters for the instruction. Parameters can be
  - integer numbers (denoted by `imm` in the table below), 
  - registers (denoted by `reg`, `reg1` and `reg2` in the table below) that the instruction manipulates,
  - memory locations, which can be of the form `[offset]`, `[reg]` or `[reg+offset]`, where `offset` is an 
    integer and `reg` is a register name; `[offset]` refers to the cell with address `offset`,
    `[reg]` refers to the cell whose address is contained in register `reg`, and `[reg+offset]` refers
    to the cell whose address is obtained by adding `offset` to the contents of register `reg`;
    memory locations will be denoted by `mem` in the table below.

SML has the following types of instructions:

| Instruction      | Interpretation                                                                                                                                                                                                                           |
|------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `mov reg1, reg2` | Copy the contents of register `reg2` to register `reg1`                                                                                                                                                                                  |
| `mov reg1, mem`  | Copy the contents of memory cell `mem` to register `reg1`                                                                                                                                                                                |
| `mov mem, reg2`  | Copy the contents of register `reg2` to memory cell `mem`                                                                                                                                                                                |
| `mov reg1, imm`  | Copy the number `imm` to register `reg1`                                                                                                                                                                                              |
| `mov mem, imm`   | Copy the number `imm` to memory cell `reg1`                                                                                                                                                                                           |
| `add reg1, reg2` | Add the contents of registers `reg1` and `reg2` and store the result in register `reg1`                                                                                                                                                  |
| `add reg1, mem`  | (similar to `mov`)                                                                                                                                                                                                                       |
| `add mem, reg2`  | (similar to `mov`)                                                                                                                                                                                                                       |
| `add reg1, imm`  | (similar to `mov`)`                                                                                                                                                                                                                      |
| `add mem, imm`   | (similar to `mov`)                                                                                                                                                                                                                       |
| `sub reg1, reg2` | Subtract the contents of register `reg2` from the contents of `reg1` and store the result in register `reg1`                                                                                                                             |
| `sub reg1, mem`  | (similar to `mov`)                                                                                                                                                                                                                       |
| `sub mem, reg2`  | (similar to `mov`)                                                                                                                                                                                                                       |
| `sub reg1, imm`  | (similar to `mov`)                                                                                                                                                                                                                       |
| `sub mem, imm`   | (similar to `mov`)                                                                                                                                                                                                                       |
| `cmp reg1, reg2` | Compare the contents of registers `reg1` and `reg2` and set flags `ZF` and `SF`: `ZF` is set if the numbers are equal and `SF` is set if the first is smaller than the second                                                            |
| `cmp reg1, mem`  | (similar to `mov`)                                                                                                                                                                                                                       |
| `cmp mem, reg2`  | (similar to `mov`)                                                                                                                                                                                                                       |
| `cmp reg1, imm`  | (similar to `mov`)                                                                                                                                                                                                                       |
| `cmp mem, imm`   | (similar to `mov`)                                                                                                                                                                                                                       |
| `mul reg`        | Multiply the contents of registers `AX` and `reg` and store the result in registers `AX` (lower 32 bits) and `DX` (upper 32 bits)                                                                                                        |
| `mul mem`        | Multiply the contents of register `AX` and memory cell `mem` and store the result in registers `AX` (lower 32 bits) and `DX` (upper 32 bits)                                                                                             |
| `div reg`        | Divide (Java integer division) the 64-bit integer obtained from registers `DX` (upper 32 bits) and `AX`(lower 32-bits) by the contents of register `reg` and store the integer part in register `AX` and the remainder in register `DX`  |
| `div mem`        | Divide (Java integer division) the 64-bit integer obtained from registers `DX` (upper 32 bits) and `AX`(lower 32-bits) by the contents of memory cell `mem` and store the integer part in register `AX` and the remainder in register `DX` |
| `jne L`          | If the `ZF` flag is not set ("not equal"), then make the statement labeled `L` the next statement to execute; otherwise, continue normally.                                                                                              |
| `jge L`          | If the `SF` flag is not set or the `ZF` flag is set ("greater than or equal"), then make the statement labeled `L` the next statement to execute; otherwise, continue normally.                                                          |
| `jle L`          | If the `SF` or the `ZF` flag is set ("less than or equal"), then make the statement labeled `L` the next statement to execute; otherwise, continue normally.                                                                             |
