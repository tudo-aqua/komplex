<!--
   SPDX-License-Identifier: CC-BY-4.0

   Copyright 2023-2026 The Konstraints Authors

   This work is licensed under the Creative Commons Attribution 4.0
   International License.

   You should have received a copy of the license along with this
   work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
-->

# komplex

`komplex` is a Kotlin library for modeling complex numbers and encoding complex-number reasoning as
SMT expressions. It is built on top of [Konstraints](https://github.com/tudo-aqua/konstraints).

## Supported Operations

The project currently provides these complex-number operations:

- addition (`+`, `cpxadd`)
- subtraction (`-`, `cpxsub`)
- multiplication (`*`, `cpxmul`)
- division (`/`, `cpxdiv`)
- multiplicative inverse (`inverse()`, `cpxinv`)
- conjugation (`conjugate()`)
- magnitude and squared magnitude (`magnitude()`, `magnitudeSquared()`)

All listed operations are available with both concrete semantics (runtime complex arithmetic) and
SMT semantics (symbolic encoding as solver expressions).

## Build

```bash
./gradlew build
```

## Authors

See the [AUTHORS](AUTHORS.md) file.
