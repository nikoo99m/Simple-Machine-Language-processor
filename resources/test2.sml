       mov BP, 100
       mov CX, 1
loop:  mov AX, CX
       mul CX
       mov [BP], AX
       add BP, 1
       add CX, 1
       cmp CX, 10
       jle loop
       mov AX, 0
       mov BP, 200
       mov DX, 1
       mov CX, 1
loop2: add AX, DX
       mov [BP], AX
       add BP, 1
       add DX, 2
       add CX, 1
       cmp CX, 10
       jle loop2
       mov BP, 100
       mov CX, 1
loop3: mov DX, [BP]
       cmp [BP+100], DX
       jne exit
       add BP, 1
       add CX, 1
       cmp CX, 10
       jle loop3
       jge success
exit:  jne exit
success: mov AX, 42