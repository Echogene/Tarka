Tarka
=====
This is a simple logical language currently interpreted by Java 8.  Tarka works in a domain of discourse defined by a _model_.  There is currently only one model, the integer model, whose universal set is the set of integers ℤ.

Additional Libraries
====================
The source code currently needs no additional libraries, but the test code does.  It needs
- [JUnit](https://github.com/junit-team/junit/wiki/Download-and-Install)
- [Reflections](https://code.google.com/p/reflections/), which in turn needs
  - [Javassist](https://github.com/jboss-javassist/javassist)
  - [Guava](https://code.google.com/p/guava-libraries/)

Examples
========
The syntax has not been finalised, but here are some current examples in the default integer model:
- `(∀x ∊ {2 3 5} (x ∊ ℙ))` _returns_ `true`
- `(⋂ ℙ [0 10])` _returns_ `{2 3 5 7}`
- `((x + 2) where x is 2)` _returns_ `4`

Supported Features
==================
Quantification
--------------
Tarka supports universal, existential and unique quantification:
- `(∀x ⊥)` would be `true` only in the empty model
- `(∃x ⊤)` would be `true` only in everything but the empty model
- `(∃!x ⊤)` would be `true` only in all models with a universal set of cardinality 1

`todo: document some more`
