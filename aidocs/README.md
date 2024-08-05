## AI-DOCS
- load spring docs into vector database and query
- we use shell command
- 
- load once through postconstruct


# Normal Spring flow
## use
- @ShellComponent, @ShellMethod

## alternative
- @CommandScan on @SpringBootApplication
- @Command on class and Method


# questions
- q "What is the RestClient in Spring"
- q "What is @RestController and can you provide code examples"


# native image
- add Hints to compiler to locate resources via RuntimeRegistrar
  - add as Spring Configuration
  - add @ImportRuntimeHints(HintsRegistrar.class) on SpringBootApplication
- ./mvnw -Pnative native:compile -DskipTests