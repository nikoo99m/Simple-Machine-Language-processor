[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/SngwL4Jy)
# Simple (Small) Machine Language (SML)

### A coursework examining reflection API and dependency injection

+ This assignment is to be completed **individually**, and without any AI tools or assistants. 
+ The sample code mentioned in the text can be found in this repository.

<img src="sml.jpg" alt="sml"/>

The aim of this assignment is to give you practice with 

+ subclasses, 
+ modifying existing code, 
+ testing,
+ dependency injection,
+ and the use of reflection,

amongst other skills.

## Details

**Name**: Your name should appear here

**Your userid**: e.g., myname01

Please do not forget to complete this part as too often we have "orphaned" repositories,
which can require months to sort out.

## The problem

In this assignment you will write an interpreter for a simple machine language — `SML`. 

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


Here is an example of an SML program to compute the factorial of 6 (see `test1.sml` in the `resources` folder):

```
    mov AX, 1
    mov CX, 6
f3: mul CX
    sub CX, 1
    cmp CX, 1
    jge f3
```

Note that labels end with a colon (but the colon is not part of the label's name),
and that label, opcode and operands are separated by whitespace, 
with commas between operands.

The instructions of a program are executed in order (starting with the first one), 
unless the order is changed by execution of a "jump" instruction (`jne`, `jge` or `jle`). 
Execution of a program terminates when its last instruction has been executed 
(and provided that the instruction does not change the order of execution).

Your interpreter will:

1. Read the name of a file that contains the program from the command line 
(via `String[] args` and the `RunSml` class).
2. Read the program from the file and translate it into the internal form.
3. Print the program out.
4. Execute the program. 
5. Print the final values of the registers and used memory cells.

This looks like a tall order, but have no fear; 
we provide you with some of the code, so you can concentrate on the interesting 
use of subclasses, dependency injection and reflection. 

Completing the worksheets really helps as preparation for this assignment.

## Design of the program

We provide some of the classes, specifications for a few, and leave a few others 
for you to write/complete. The code we provide does some of the dirty work of reading 
in a program and translating it to an internal form;  you can concentrate on the 
code that executes the program. 

We suggest that you examine the `Machine` class first, as it is the heart 
of the program (you can use the `main` method in the `RunSml` class to guide you as well).

## Studying the program

You are provided with some skeleton code which is on this repository.

Look at the fields of the `Machine` class, which contain exactly what is 
needed to execute an SML program:

+ the *labels* defined in the program,
+ the program itself in an *internal form* (as a map: each instruction has its address),
+ the *registers*, *flags* and the *memory* of the machine, and
+ the *program counter* — the address of the next instruction to execute.

Next examine the method `Machine.execute`, which executes the program. 
It is a typical *fetch-decode-execute* cycle that all machines have in some form. 

At each iteration, the instruction to execute is fetched, the instruction is executed and 
the program counter is updated. In most cases, the program counter is simply incremented by
the instruction size to move to the next instruction in the program; 
some instructions (e.g., `jne`) can change the order of execution by jumping to a specific label.

The `Translator` class contains the methods that read in the program and translate 
it into an internal form; be warned, very little error checking goes on here. 
For example, there is no checking for duplicate label definitions, 
for the use of a label name that doesn't exist.

Finally, study the `main` method of the `RunSml` class (if you think it will help you).

## The `Instruction` class and its subclasses

All the programming that you do has to do with the `Instruction` class and it's subclasses. 
The specification of the class `Instruction` has been given to you — open the file

```
	Instruction.java
```
and examine it. This class is *abstract*, because it should not be instantiated.  
The method `execute` is also abstract, forcing every subclass to implement it. 
Every instruction has an optional *label* and an *operation code* — that is exactly 
what is common to every instruction.  Therefore, these properties are maintained 
in the base class of all instructions.

## Tasks

There are two components to this coursework assignment.

##### Part I

1. Complete the methods in the `Instruction` class — this may require you to add some fields, 
which should be *protected*, so that they are accessible in any subclasses.

2. Now create a subclass of `Instruction` for each kind of SML instruction and fix 
the method `Translator.instruction` so that it properly translates that kind of instruction.

*Recommended*: write one instruction at a time and test it out thoroughly, before proceeding to the next!

3. Start with the `mov` instruction, because the implementation of the `Instruction` subclass and 
the code for translating it is already there —  in method `Translator.getInstruction`.  

4. For each instruction, the subclass needs appropriate fields, a constructor, 
and methods `execute`, `getSize` and `toString`; these should override 
the same methods in the `Instruction` class, with appropriate annotations.

5. As you do this, you will see that each successive class can be written by 
duplicating a previous one and modifying it. 
Introduce auxiliary abstract classes where that can help avoid code duplication. 

6. Write a test class for each of the `Instruction` subclasses.

7. After you finish writing a subclass for an SML instruction, 
   you will have to add code to the method `Translator.getInstruction` to translate 
   that instruction. The existing code for translating `mov` should help you with this.

8. There are also a few places in the code with `TODO:` labels - follow the instructions to
   improve the provided code (or implement missing methods as required). 
   Use the Java Stream API whenever possible instead of loops.

##### Part II

1. Next, take the `switch` statement in `Translator.java` that decides which type of instruction is created 
   and modify the code so that it uses *reflection* to create the instances, i.e., 
   remove the explicit calls to the subclasses and the `switch` statement. 
   This will allow the SML language to be extended without having to modify the original code.

2. Modify the source code to use *dependency injection*, the *singleton* design pattern, 
   and *factory* classes, where you deem appropriate. (You are allowed to use other 
   design patterns if you consider them necessary.)

3. Apart from the specific code mentioned above you should not modify other classes.

All of these parts of the coursework should be fully tested (you do not need to provide 
tests for the original codebase).

## Submission

Your repository will be *cloned* at the appropriate due date and time.

------

###### Individual Coursework 2023-24
