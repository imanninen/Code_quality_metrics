# Code quality metrics
Here is [link](task1.md) to task 1.
## How to run application
To run my code analyzer, you could pass working directory and kotlin file in it via program arguments.

Example: `./gradlew run -P args="testData Simple.kt"` or set program arguments using IDEA capabilities.

As a result, my application prints in console response from code analyzers:
```
secondFunction - 3
thirdFunction - 3
firstFunction - 2
All functions passed CamelCase names test.
secondFunction has to many nested blocks (3).
```

## Solution
### General approach
I decided to parse all code in file into some "tokens". 
The idea was that working with tokens it is better and easier than with row string. 
But the more I immersed myself into kotlin code parsing, the more I realize how challenging it is. 
Therefore, for simplicity, I made some assumptions:
- To declare the implementation of a function, its body must be in curly braces. 
I don't support functional declarations.
- The body of each condition also must be in curly braces.

Not allowed: 
```kotlin
if (a > 0)
    b = a
else 
    b = 0
```
Allowed:
```kotlin
if (a > 0) {
    b = a
} else {
    b = 0
}
```
- Condition declaration in one line is forbidden. 
Not allowed: `if (a > 0) {b = a} else {b = 0}`
- For now, I don't support `when` condition. (Hopefully, I will have straight to fix)

Also, my parser doesn't parse anything except `if`, `fun`, `while`, `do-while`, `for`.
It doesn't collect conditions of `while` or `if` or function parameters, but it is extendable.

**_Why I decided to do parsing with all these limits?_**

Parsing gives us a full code structure clearer for computer, and it allows us to simply extend our application in 
the future, when we will decide to add more code analyze metrics. 
And also, it is necessary for my own metric, which I added to the solution.

### Supported metrics
- Code complexity: counting all conditions in function
- Code nesting complexity: counting nesting for each condition. 
If function has more nesting score than `nestingLimit` my program will indicate.
- Code style: check function's names for camelCase pattern.  

### Architecture of application
- In `app` directory there is main UI class [`CodeAnalyzer`](src/main/kotlin/app/CodeAnalyzer.kt).
- `file.code` directory contains classes:
[`FileCodeRepresentation`](src/main/kotlin/file/code/FileCodeRepresentation.kt) and
[`FileCodeRepresentationFactory`](src/main/kotlin/file/code/FileCodeRepresentationFactory.kt), which
describe how code parsed code looks and create it respectfully.
- `file.load` directory contains [`Loader`](src/main/kotlin/file/load/Loader.kt), which loads row kotlin file as a `List<String>`.
- `file.processing` directory contains [`Lexer`](src/main/kotlin/file/processing/Lexer.kt) file, which parse row code to tokens.
- `metrics` directory contains [`CodeAnalyzeMetric`](src/main/kotlin/metrics/CodeAnalyzeMetric.kt) interface
which describes an abstract code analyze metric.
Also, there I create [`AllCodeAnalyzeMetrics`](src/main/kotlin/metrics/AllCodeAnalyzeMetrics.kt) enum class,
which helps me to easily take objects of metrics.
- `utils` directory contains [`Expressions.kt`](src/main/kotlin/utils/Expressions.kt) file, which include all
classes needed for construct structure of code. 
