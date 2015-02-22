Tarka
=====
This is a simple logical language currently interpreted by Java 8.  Tarka works in a domain of discourse defined by a _model_.  There is currently only one model, the integer model, whose universal set is the set of integers ℤ.

Examples
========
The syntax has not been finalised, but here are some current examples in ℤ:

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

The variable can be restricted to specific sets:

- `(∀x ∊ {1 2} (x ∊ ℙ))` returns `false` in ℤ
- `(∃x ∊ {1 2} (x ∊ ℙ))` returns `true` in ℤ
- `(∃!x ∊ {1 2} (x ∊ ℙ))` returns `true` in ℤ

Equality
--------
A single equals sign is used for equals.

- `(1 = 2)` returns `false` in ℤ

Membership
----------
- `(2 ∊ ℙ)` returns `true` in ℤ

Binary Logical Operations
-----------------
Tarka supports the binary operators `∨`, `⊽`, `∧`, `⊼`, `→`, `↛`, `↔`, `↮`, `←` and `↚`, which correspond to or, nor, and, nand, implies, does not imply, iff, xor, implied by and not implied by.

- `(⊥ → ⊤)` is a tautology

Negation (a Unary Logical Operation)
-----------------------------------
- `(¬⊤)` is a contradiction

Union
-----
Tarka supports binary and multary unions.

- `({1 2} ∪ {2 3})` returns `{1 2 3}` in ℤ
- `(⋃ {1} {2} {3})` returns `{1 2 3}` in ℤ

Intersection
------------
Tarka supports binary and multary intersections.

- `({1 2} ∩ {2 3})` returns `{2}` in ℤ
- `(⋂ {1 2 3} {2 3} {3})` returns `{3}` in ℤ

Addition
--------
In the default integer model, Tarka supports binary and multary sums.

- `(2 + 2)` returns `4` in ℤ
- `(Σ 1 2 3)` returns `6` in ℤ

Assignment
----------
Assign local variables:

- `(x where x is 2)` returns `2` in ℤ
- `((x = 1) where x is (1 + 1))` returns `false` in ℤ

If/else
-------
- `(2 if ⊤ otherwise 3)` returns `2` in ℤ
- `((1 + 2) if (⊤ → ⊥) otherwise (3 - 1))` returns `2` in ℤ

`todo: document some more`
