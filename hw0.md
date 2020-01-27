HW 0
----------

## Watch the videos and write up your answers to the following questions

**Important!**

The virtual machine-in-your-browser and the videos you need for HW0 are here:

http://cs-education.github.io/sys/

The coursebook:

http://cs241.cs.illinois.edu/coursebook/index.html

Questions? Comments? Use Piazza:
https://piazza.com/illinois/spring2020/cs241

The in-browser virtual machine runs entirely in Javascript and is fastest in Chrome. Note the VM and any code you write is reset when you reload the page, *so copy your code to a separate document.* The post-video challenges (e.g. Haiku poem) are not part of homework 0 but you learn the most by doing (rather than just passively watching) - so we suggest you have some fun with each end-of-video challenge.

HW0 questions are below. Copy your answers into a text document because you'll need to submit them later in the course. More information will be in the first lab.

## Chapter 1

In which our intrepid hero battles standard out, standard error, file descriptors and writing to files.

### Hello, World! (system call style)
1. Write a program that uses `write()` to print out "Hi! My name is `<Your Name>`".

```C
#include <unistd.h>

int main() {
    write(1, "Hi! My name is Ramya.", 21);
    return 0;
}
```

### Hello, Standard Error Stream!
2. Write a function to print out a triangle of height `n` to standard error.
   - Your function should have the signature `void write_triangle(int n)` and should use `write()`.
   - The triangle should look like this, for n = 3:
   ```C
   *
   **
   ***
   ```
```C 
#include <unistd.h>
#define STDERR_FILENO 2

void write_triangle(int n) {
    if (n < 1) {
        exit(1);
    }

    int len;
    int currlen;
    
    for (len = 1; len <= n; len++) {
        for (currlen = 0; currlen < len; currlen++) {
	    write(2, "*", 1);
	}
	write(2, "\n", 1);
    }  	
}

int main() {
    write_triangle(3);
    return 0;
}
```

### Writing to files
3. Take your program from "Hello, World!" modify it write to a file called `hello_world.txt`.
   - Make sure to to use correct flags and a correct mode for `open()` (`man 2 open` is your friend).
```C   
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

int main() {
	mode_t mode = S_IRUSR | S_IWUSR;
	int fildes = open("hello_world.txt", O_CREAT | O_TRUNC | O_RDWR, mode);
	write(fildes, "Hi! My name is Ramya.", 21);
	close(fildes);
	return 0;
}
```
### Not everything is a system call
4. Take your program from "Writing to files" and replace `write()` with `printf()`.
   - Make sure to print to the file instead of standard out!
```C   
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

int main() {
	mode_t mode = S_IRUSR | S_IWUSR;
	close(1);
	int fildes = open("hello_world.txt", O_CREAT | O_TRUNC | O_RDWR, mode);
	printf("%s","Hi! My name is Ramya.");
	close(fildes);
	return 0;
}
```

5. What are some differences between `write()` and `printf()`?

`
write() is a system call which is basic and costly. On the other hand, printf() is a library provided by the C language that calls write(), and basically provides us to write data into a formatted sequence or output of bytes, and printf() also incorporates buffering such that data is written only when necessary. 
`

## Chapter 2

Sizing up C types and their limits, `int` and `char` arrays, and incrementing pointers

### Not all bytes are 8 bits?
1. How many bits are there in a byte?
```C  
At least 8 bits in a byte
```
2. How many bytes are there in a `char`?
```C  
1 byte in a char
```
3. How many bytes the following are on your machine?
   - `int`, `double`, `float`, `long`, and `long long`
```C  
int=4, double=8, float=4, long=4, long long=8
```
### Follow the int pointer
4. On a machine with 8 byte integers:
```C
int main(){
    int data[8];
} 
```
If the address of data is `0x7fbd9d40`, then what is the address of `data+2`?

```C  
0x7fbd9d50
```
5. What is `data[3]` equivalent to in C?
   - Hint: what does C convert `data[3]` to before dereferencing the address?
```C  
*(data+3)
```

### `sizeof` character arrays, incrementing pointers
  
Remember, the type of a string constant `"abc"` is an array.

6. Why does this segfault?
```C
char *ptr = "hello";
*ptr = 'J';
```

```C
"hello" is a string so it is immutable and constant
```

7. What does `sizeof("Hello\0World")` return?
```C 
12 
```
8. What does `strlen("Hello\0World")` return?
```C 
5
```
9. Give an example of X such that `sizeof(X)` is 3. 
```C 
sizeof("ab")
```
10. Give an example of Y such that `sizeof(Y)` might be 4 or 8 depending on the machine. 
```C 
sizeof(3)
```
## Chapter 3

Program arguments, environment variables, and working with character arrays (strings)

### Program arguments, `argc`, `argv`
1. What are two ways to find the length of `argv`? 
```C  
Argc is the first parameter of main(), and this indicated the length of argv. looping until argv[index] points to NULL
```
2. What does `argv[0]` represent?
```C  
It is the name of the program, so ./program
```
### Environment Variables
3. Where are the pointers to environment variables stored (on the stack, the heap, somewhere else)?
```C  
Somewhere else: top of process memory layout, so above the stack, in processors' own memory
```
### String searching (strings are just char arrays)
4. On a machine where pointers are 8 bytes, and with the following code:
```C
char *ptr = "Hello";
char array[] = "Hello";
```
What are the values of `sizeof(ptr)` and `sizeof(array)`? Why?
```C 
sizeof(ptr) = 8 -> pointer is 8 bytes in this machine
sizeof(array) = 6 -> 5 bytes for "Hello" + 1 byte for null char at the end
```

### Lifetime of automatic variables

5. What data structure manages the lifetime of automatic variables?
```C 
The stack
```

## Chapter 4

Heap and stack memory, and working with structs

### Memory allocation using `malloc`, the heap, and time
1. If I want to use data after the lifetime of the function it was created in ends, where should I put it? How do I put it there?
```C 
By using "static" to define a variable, or by using malloc/realloc/calloc you can put in the heap and use data after the lifetime of the function. 
```
2. What are the differences between heap and stack memory?
```C 
Heap: dynamic memory allocation, allocate and deallocate by programmer 
Stack: static memory allocation, stored directly to memory so it has fast access 
```
3. Are there other kinds of memory in a process?
```C 
Yes, Initialized data, uninitialized data, text segment
```
4. Fill in the blank: "In a good C program, for every malloc, there is a ___".
```C 
free()
```
### Heap allocation gotchas
5. What is one reason `malloc` can fail?
```C 
Malloc can fail when there is a buffer overflow, not enough memory space, or when asked for more bytes than you can ask for in a single allocation.
```
6. What are some differences between `time()` and `ctime()`?
```C 
time() returns seconds after 1970 as a time_t object
ctime() returns a string that represents the local time based on the argument timer.
```
7. What is wrong with this code snippet?
```C
free(ptr);
free(ptr);
```

```C 
You freed the pointer twice, and after it has been freed it has never been set to null.
```
8. What is wrong with this code snippet?
```C
free(ptr);
printf("%s\n", ptr);
```

```C 
You are using a pointer that has been freed
```
9. How can one avoid the previous two mistakes? 
```C 
Set pointer to null right after freeing it, and do not double free().
```
### `struct`, `typedef`s, and a linked list
10. Create a `struct` that represents a `Person`. Then make a `typedef`, so that `struct Person` can be replaced with a single word. A person should contain the following information: their name (a string), their age (an integer), and a list of their friends (stored as a pointer to an array of pointers to `Person`s).
11. Now, make two persons on the heap, "Agent Smith" and "Sonny Moore", who are 128 and 256 years old respectively and are friends with each other.
### Duplicating strings, memory allocation and deallocation of structures
Create functions to create and destroy a Person (Person's and their names should live on the heap).
12. `create()` should take a name and age. The name should be copied onto the heap. Use malloc to reserve sufficient memory for everyone having up to ten friends. Be sure initialize all fields (why?).
13. `destroy()` should free up not only the memory of the person struct, but also free all of its attributes that are stored on the heap. Destroying one person should not destroy any others.
```C 
#include <stdio.h>


struct Person {
	char* name;
	int age;
	struct Person* friends[];
};

typedef struct Person person_t;


int main() {
	
	person_t* person1 = (person_t*) malloc(sizeof(person_t));
	person_t* person2 = (person_t*) malloc(sizeof(person_t));
	
	person1->name = "Agent Smith";
	person2->name = "Sonny Moore";
	person1->age = 128;
	person2->age = 256;
	
	person1->friends[0] = person2;
	person2->friends[0] = person1;
	
	return 0;
	
}



person_t*  create(char* name_, int age_) {
	person_t* result = (person_t*) malloc(48);
	result->name = strdup(name_);
	result->age = age_;
	Return result;
}

Why should you initialize all fields??
Void destroy(person_t* p) {
	free(p->name);
	free(p->age);
	free(p->friends);
	memset(p, 0, 48);
	free(p);
}

```
## Chapter 5 

Text input and output and parsing using `getchar`, `gets`, and `getline`.

### Reading characters, trouble with gets
1. What functions can be used for getting characters from `stdin` and writing them to `stdout`?
```C 
gets(), puts(), getchar(), putchar()
```
2. Name one issue with `gets()`.
```C 
gets() does not do array bound testing so it does not recognize if the input is too long, and it can end up in buffer overflow
```
### Introducing `sscanf` and friends
3. Write code that parses the string "Hello 5 World" and initializes 3 variables to "Hello", 5, and "World".
```C 
#include <stdio.h>

int main() {
	
	char* data = "Hello 5 World";
	
	char buffer1[15];
	char buffer2[15];
	int n = 15;
	
	sscanf(data, "%s %d %s", buffer1, &n, buffer2); 
	return 0;
}
```
### `getline` is useful
4. What does one need to define before including `getline()`?
```C 
buffer variable and size of buffer variable

#define _GNU_SOURCE
```
5. Write a C program to print out the content of a file line-by-line using `getline()`.
```C 
#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>

int main(void)
{
	FILE *f = fopen("file", "r");
	if (f == NULL) {
		exit(EXIT_FAILURE);
	}
	
	char *line = NULL;
	size_t length = 0;
	ssize_t read_line = getline(&line, &length, f);
	
	while (read_line != -1) {
		printf("Retrieved line of length %zu:\n", read_line);
		printf("%s", line);
		read_line = getline(&line, &length, f);
	}
	
	return 0;
}
```

## C Development

These are general tips for compiling and developing using a compiler and git. Some web searches will be useful here

1. What compiler flag is used to generate a debug build?
```C 
-g
```
2. You modify the Makefile to generate debug builds and type `make` again. Explain why this is insufficient to generate a new build.
```C 
Insufficient because you want to clear previous .o files so its best to "make clean".
```
3. Are tabs or spaces used to indent the commands after the rule in a Makefile?
```C 
Yes, tabs
```
4. What does `git commit` do? What's a `sha` in the context of git?

`
A commit is a change to file(s), and git commit allows you to save your changes to the local repository, it only saves the new commit object in the local Git repository. When you save in Git, a unique ID is created which allows you to keep record of what changes were made when and by who, this unique ID is "SHA".
`

5. What does `git log` show you?

`
Git log is a running record of commits, it allows you to view information about previous commits that have occurred in a project. It lets you list the project history, filter it, and search for specific changes. 
`

6. What does `git status` tell you and how would the contents of `.gitignore` change its output?

`
git status is used to know the status of the working tree. It shows the current state of the working directory, it displays changes that have and have not been staged, it shows differences in the current tree and the HEAD pointer. The .gitignore file contains files that are intentionally supposed to be ignored. Since git status also displays paths in the working tree that are not tracked by Git, but are not ignored by gitignore, the contents of .gitignore would change the output of git status.
`

7. What does `git push` do? Why is it not just sufficient to commit with `git commit -m 'fixed all bugs' `?

`
Git push pushes commits made on your local branch to a remote repository. We can't just do git commit -m ’fixed all bugs’ because we end up commiting files to the local, whereas pushing it specifies the remote and branch names. 
`

8. What does a non-fast-forward error `git push` reject mean? What is the most common way of dealing with this?

` 
Git push reject is when git cannot push changes you made because someone else pushed to the same branch. The most common way of dealing with this is to fetch and merge changes made on the remote branch with changes made locally.
`

## Optional (Just for fun)
- Convert your a song lyrics into System Programming and C code and share on Piazza.
- Find, in your opinion, the best and worst C code on the web and post the link to Piazza.
- Write a short C program with a deliberate subtle C bug and post it on Piazza to see if others can spot your bug.
- Do you have any cool/disastrous system programming bugs you've heard about? Feel free to share with your peers and the course staff on piazza.
