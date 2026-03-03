/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2023-2026 The Konstraints Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.stream.Stream
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import tools.aqua.komplex.Complex

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ComplexTest {

  private fun assertComplexEquals(expected: Complex, actual: Complex, delta: Double = 1e-9) {
    assertEquals(expected.re, actual.re, delta, "Real parts differ")
    assertEquals(expected.im, actual.im, delta, "Imag parts differ")
  }

  private fun additionProvider(): Stream<Arguments> =
      Stream.of(
          Arguments.of(Complex(1.0, 2.0), Complex(3.0, 4.0), Complex(4.0, 6.0)),
          Arguments.of(Complex(0.0, 0.0), Complex(1.0, -1.0), Complex(1.0, -1.0)),
          Arguments.of(Complex(-2.0, 5.0), Complex(3.0, -7.0), Complex(1.0, -2.0)),
      )

  @ParameterizedTest
  @MethodSource("additionProvider")
  fun testAddition(a: Complex, b: Complex, expected: Complex) {
    assertComplexEquals(expected, a + b)
  }

  private fun subtractionProvider(): Stream<Arguments> =
      Stream.of(
          Arguments.of(Complex(1.0, 2.0), Complex(3.0, 4.0), Complex(-2.0, -2.0)),
          Arguments.of(Complex(0.0, 0.0), Complex(1.0, -1.0), Complex(-1.0, 1.0)),
          Arguments.of(Complex(-2.0, 5.0), Complex(3.0, -7.0), Complex(-5.0, 12.0)),
      )

  @ParameterizedTest
  @MethodSource("subtractionProvider")
  fun testSubtraction(a: Complex, b: Complex, expected: Complex) {
    assertComplexEquals(expected, a - b)
  }

  private fun multiplicationProvider(): Stream<Arguments> =
      Stream.of(
          Arguments.of(Complex(1.0, 2.0), Complex(3.0, 4.0), Complex(-5.0, 10.0)),
          Arguments.of(Complex(2.0, -1.0), Complex(1.0, 1.0), Complex(3.0, 1.0)),
          Arguments.of(Complex(0.0, 0.0), Complex(5.0, -3.0), Complex(0.0, 0.0)),
      )

  @ParameterizedTest
  @MethodSource("multiplicationProvider")
  fun testMultiplication(a: Complex, b: Complex, expected: Complex) {
    assertComplexEquals(expected, a * b)
  }

  private fun conjugateProvider(): Stream<Arguments> =
      Stream.of(
          Arguments.of(Complex(1.0, 2.0), Complex(1.0, -2.0)),
          Arguments.of(Complex(-3.5, 4.1), Complex(-3.5, -4.1)),
          Arguments.of(Complex(0.0, 0.0), Complex(0.0, 0.0)),
      )

  @ParameterizedTest
  @MethodSource("conjugateProvider")
  fun testConjugate(a: Complex, expected: Complex) {
    assertComplexEquals(expected, a.conjugate())
  }

  private fun inverseProvider(): Stream<Arguments> =
      Stream.of(
          Arguments.of(Complex(1.0, 0.0), Complex(1.0, 0.0)),
          Arguments.of(Complex(1.0, 1.0), Complex(0.5, -0.5)),
          Arguments.of(Complex(2.0, -2.0), Complex(0.25, 0.25)),
      )

  @ParameterizedTest
  @MethodSource("inverseProvider")
  fun testInverse(a: Complex, expected: Complex) {
    assertComplexEquals(expected, a.inverse())
  }

  @Test
  fun testInverseZeroThrows() {
    assertThrows<IllegalArgumentException> { Complex(0.0, 0.0).inverse() }
  }

  private fun divisionProvider(): Stream<Arguments> =
      Stream.of(
          Arguments.of(Complex(1.0, 2.0), Complex(3.0, 4.0), Complex(0.44, 0.08)),
          Arguments.of(Complex(2.0, -1.0), Complex(1.0, 1.0), Complex(0.5, -1.5)),
          Arguments.of(Complex(0.0, 0.0), Complex(5.0, -3.0), Complex(0.0, 0.0)),
      )

  @ParameterizedTest
  @MethodSource("divisionProvider")
  fun testDivision(a: Complex, b: Complex, expected: Complex) {
    assertComplexEquals(expected, a / b, delta = 1e-2) // allow FP rounding
  }

  private fun equalsProvider(): Stream<Arguments> =
      Stream.of(
          Arguments.of(Complex(1.0, 2.0), Complex(1.0, 2.0), true),
          Arguments.of(Complex(1.0, 2.0), Complex(1.0, 3.0), false),
          Arguments.of(Complex(1.0, 2.0), Complex(2.0, 2.0), false),
          Arguments.of(Complex(0.0, 0.0), Complex(0.0, 0.0), true),
          Arguments.of(Complex(1.0, -1.0), Complex(1.0, -1.0), true),
      )

  @ParameterizedTest
  @MethodSource("equalsProvider")
  fun testEquals(a: Complex, b: Complex, expected: Boolean) {
    assertEquals(expected, a == b)
  }
}
