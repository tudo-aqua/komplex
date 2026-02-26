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