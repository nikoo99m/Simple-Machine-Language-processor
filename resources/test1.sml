    mov AX, 1
    mov CX, 6
f3: mul CX
    sub CX, 1
    cmp CX, 1
    jge f3
