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

import org.junit.jupiter.api.Test
import tools.aqua.konstraints.smt.Assert
import tools.aqua.konstraints.smt.Equals
import tools.aqua.konstraints.smt.MutableSMTProgram
import tools.aqua.konstraints.smt.toSymbol
import tools.aqua.konstraints.solvers.InteractiveZ3Solver

class OperationsTest {
  @Test
  fun testAddition() {
    val program = MutableSMTProgram()
    program.setLogic(QF_UFRDLDT)

    program.declareDatatype(ComplexSort)
    program.defineFun(ComplexAddDecl)

    val foo = program.declareConst("foo".toSymbol(), ComplexSort)
    val bar = program.declareConst("bar".toSymbol(), ComplexSort)

    program.assert(
        Assert(
            Equals(ComplexAdd(foo.instance, bar.instance), ComplexAdd(bar.instance, foo.instance)),
        )
    )

    program.checkSat()
    program.getModel()

    val solver = InteractiveZ3Solver()
    solver.use { solver -> solver.solve(program) }

    println(program.status)
    println(program.model)
  }
}
