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

Equality
--------
A single equals sign is used for equals.
- `(1 = 2)` returns `false` in the default integer model

Membership
----------
- `(2 ∊ ℙ)` returns `true` in the default integer model

Binary Operations
-----------------
Tarka supports the binary operators `∨`, `⊽`, `∧`, `⊼`, `→`, `↛`, `↔`, `↮`, `←` and `↚`, which correspond to or, nor, and, nand, implies, does not imply, iff, xor, implied by and not implied by.
- `(⊥ → ⊤)` is a tautology

Negation
--------
- `(¬⊤)` is a contradiction

Union
-----
Tarka supports binary and multary unions.
- `({1 2} ∪ {2 3})` returns `{1 2 3}` in the default integer model
- `(⋃ {1} {2} {3})` returns `{1 2 3}` in the default integer model

Intersection
------------
Tarka supports binary and multary intersections.
- `({1 2} ∩ {2 3})` returns `{1 2 3}` in the default integer model
- `(⋂ {1 2 3} {2 3} {3})` returns `{3}` in the default integer model

`todo: document some more`
