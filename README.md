# java-uni-project-parser
Uni project to write a parser that takes a grammar in Chomsky Normal Form and a string. It determines if the string is in the language of the grammar and returns a parse tree if so or Null if not.

Helper code was provided as this was from a Foundations of Computation module focusing on grammars rather than a coding module.
We were each provided a grammar that we had to convert into Chomsky Normal Form, MyGrammar.java contains my solution to can be used by Parser.java. The grammar generates the language of syntactically correct addition and multiplication equations (including bracketing) containing the variables 1, 0, and x.
Parser.java contains my Parser class that carries out a leftmost derivation on the input string.

Both MyGrammar.java and Parser.java are my own work, whereas the other files were provided as part of the project.
Main.java explains the project and carries out some tests on the parser using a basic grammar that generates the language {0ⁿ1ⁿ where n ≥ 0}.
