wc.exe -w test.c -o resultFile/readWord.txt
wc.exe -c test.c -o resultFile/readChar.txt
wc.exe -l test.c -o resultFile/readLine.txt
wc.exe -w -s *.c -o resultFile/FindFile.txt
wc.exe -a test.c -o resultFile/diffLine.txt
wc.exe -w -c test.c -o resultFile/readCharAndWord.txt
wc.exe -w -c -s *.c -o resultFile/FindFile_readCharAndWord.txt
wc.exe -w -c  -a -s *.c -o resultFile/FindFile_readCharAndWord.txt
wc.exe -w -c -a -l -s *.c -o resultFile/FindFile_readCharAndWord.txt
wc.exe -a -s *.c -o resultFile/FindFile_readCharAndWord.txt
pause