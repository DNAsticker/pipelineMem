# pipelineMem
Simulations of improvements to Pipeline DNA Memory in US 11,515,012B1

This is supplemental information for a paper submitted to 2026 DNA Computing Conference.  There is one python program which simulates the purely recursive definition given in the paper (similar to recursive but more elegant to C code in the patent).  The patent solves the problems with random access with extra hardware that uses extra cycles.  The novel idea in the paper illustrated by the code in this repo (not disclosed in the patent) is how to solve the problem in fewer cycles without extra hardware.

There are several varieties of java source files.

The files with "StickxNLE" use strings to represent stickers that encode the address of each strands.  This code was taken from similar javascript.

The files with "StickxNLEpop*.java" use more efficient encoding, but still consider the contents of memory.  Also, "StickxNLEpopt*.java" is slightly more efficient.

The files with "StFake*.java" only simulate the presence of the tubes in the algorithm (and not the contents) for efficiency. The result of the simulation still verifies the correctness of the algorithm because the contents can be assumed from the pattern of the s and c logic.   

The number in the file name is the address width, aw.  Each of the simulations test every possible random address between 0 and 2 raised to aw, and then checks if sequential addressing continues correctly after the initial random access.  The main thing being tested are the special cases described in the paper for aw<=32.
