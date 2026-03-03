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

package tools.aqua.komplex

import kotlin.math.sqrt
import tools.aqua.konstraints.smt.Expression
import tools.aqua.konstraints.smt.RealLiteral

data class Complex(val re: Double, val im: Double) {
  /** Addition: (a + bi) + (c + di) = (a+c) + (b+d)i */
  operator fun plus(other: Complex): Complex = Complex(re + other.re, im + other.im)

  /** Subtraction: (a + bi) - (c + di) = (a-c) + (b-d)i */
  operator fun minus(other: Complex): Complex = Complex(re - other.re, im - other.im)

  /** Multiplication: (a + bi)(c + di) = (ac - bd) + (ad + bc)i */
  operator fun times(other: Complex): Complex =
      Complex(re * other.re - im * other.im, re * other.im + im * other.re)

  /** Conjugate: ([re] - [im]i) */
  fun conjugate(): Complex = Complex(re, -im)

  /** Magnitude squared: |z|² = [re]² + [im]² */
  fun magnitudeSquared(): Double = re * re + im * im

  fun magnitude(): Double = sqrt(magnitudeSquared())

  /** Complex inverse: 1 / ([re] + [im]i) = ([re] - [im]i) / ([re]² + [im]²) */
  fun inverse(): Complex {
    val denom = magnitudeSquared()
    require(denom != 0.0) { "Can not invert zero complex number" }
    return Complex(re / denom, -im / denom)
  }

  /**
   * Division using multiplication and inverse: [this][tools.aqua.komplex.Complex] / [other] =
   * [this][tools.aqua.komplex.Complex] * [other].[inverse]
   */
  operator fun div(other: Complex): Complex = this * other.inverse()
}

val Number.re: Complex
  get() = Complex(this.toDouble(), 0.0)

val Number.i: Complex
  get() = Complex(0.0, this.toDouble())

infix operator fun Number.plus(other: Complex): Complex = Complex(this.toDouble(), 0.0) + other

infix operator fun Complex.plus(other: Number): Complex = this + Complex(other.toDouble(), 0.0)

fun Complex.toExpression(): Expression<ComplexSort> =
    ComplexSort.construct(RealLiteral(re), RealLiteral(im))
