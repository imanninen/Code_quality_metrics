### Task 1
_**Why are you interested in this project?**_

I chose this internship, because code analyzes and researching for new code analyze metrics — it seems
exciting for me.  

Also, it will give me a great experience of real work, with a great team, and I believe it is also essential
for programmer to have. Although I am only on second year of studying at university, I have a huge thirst for
knowledge. I will also receive additional motivation from the fact that development will be carried out in
Java/Kotlin, the knowledge of which I want to bring to perfection.

And of course, this internship will allow me to get new connections, and hopefully future work colleagues!

_**How would you approach the research part?**_

I will explore some complete codebases, and run some metrics on them, for example, code complexity.  
Then, I'll collect all results and analyze what they have in common and what is different.
This approach will help me understand what parameters are really matter to a developer.

_**How do you see implementation of the code metrics mechanism?**_

I see it like this:
There is some main class like `CodeAnalyzer` which have ability to analyze file and also to choose what kind of metrics
user want to specify.
```kotlin
val analyzer = CodeAnalyzer()
analyzer.analize(file, listOfCodeMetrics)
```
Also, there is some common interface for all metrics with some common method, which applies metric to code.
```kotlin
interface Metric {
    fun apply(code: Code)
}
```
Additionally, there could be intermediate inheritance points like this:
```kotlin
interface ComplexityMetric : Metric {
    val someComplexitySpecificProperty: SomeTypre
    fun sumComplexitySpecificMethod()
}
```
They are necessary if we want to group some metrics together.