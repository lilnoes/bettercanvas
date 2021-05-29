
; You may customize this and other start-up templates; 
; The location of this template is c:\emu8086\inc\0_com_template.txt
;1306180082 - Leon Emmanuel ISHIMWE
.model small
.stack 64
.data 
    msg1  db 'Please enter your last id 6 numbers: $'  
    msg2  db 10, 13, "Your numbers reversed: $"
    leon db 6 dup (4); sectigim isim Leon
    
.code
   lea dx, @data    ; point ds to start of data
   mov ds, dx
   
   lea dx, msg1
   mov ah, 09h
   int 21h    
   
   mov cx, 6        ; 0'dan 6'ya
   mov si, offset leon    ; leon dizisi
   
   getnumbers: 
   mov ah, 01h; oku
   int 21h  
   mov [si], al; leon[si]'a yazdir
   inc si
   loop getnumbers 
   
   lea dx, msg2
   mov ah, 09h
   int 21h
   
   mov cx, 6
   writenumbers:   
   dec si
   mov dl, [si]
   mov ah, 02h; yaz
   int 21h  
   loop writenumbers  
   
   mov ax, 0082h; son4 hanesi
   mov bx, 2808h; 28/08 dogum gunu
   xor ax, bx
    
    
end 




