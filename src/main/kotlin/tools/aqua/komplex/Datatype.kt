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

import tools.aqua.konstraints.smt.ConstructorDecl
import tools.aqua.konstraints.smt.Datatype
import tools.aqua.konstraints.smt.Expression
import tools.aqua.konstraints.smt.RealSort
import tools.aqua.konstraints.smt.SMTReal
import tools.aqua.konstraints.smt.SelectorDecl
import tools.aqua.konstraints.smt.toSymbol

object ComplexSort :
    Datatype(
        0,
        "Complex".toSymbol(),
        listOf(
            ConstructorDecl(
                "C".toSymbol(),
                listOf(
                    SelectorDecl("re".toSymbol(), SMTReal),
                    SelectorDecl("im".toSymbol(), SMTReal),
                ),
            )
        ),
    ) {
  fun construct(re: Expression<RealSort>, im: Expression<RealSort>): Expression<ComplexSort> {
    @Suppress("UNCHECKED_CAST")
    return constructors[0].constructDynamic(listOf(re, im), emptyList()) as Expression<ComplexSort>
  }

  fun re(expr: Expression<ComplexSort>): Expression<RealSort> {
    @Suppress("UNCHECKED_CAST")
    return selectors[0].constructDynamic(listOf(expr), emptyList()) as Expression<RealSort>
  }

  fun im(expr: Expression<ComplexSort>): Expression<RealSort> {
    @Suppress("UNCHECKED_CAST")
    return selectors[1].constructDynamic(listOf(expr), emptyList()) as Expression<RealSort>
  }
}

fun Expression<ComplexSort>.re(): Expression<RealSort> {
  return ComplexSort.re(this)
}

fun Expression<ComplexSort>.im(): Expression<RealSort> {
  return ComplexSort.im(this)
}
