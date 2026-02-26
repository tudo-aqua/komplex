package tools.aqua.komplex

import tools.aqua.konstraints.dsl.UserDefinedSMTFunction1
import tools.aqua.konstraints.dsl.UserDefinedSMTFunction2
import tools.aqua.konstraints.smt.BinaryExpression
import tools.aqua.konstraints.smt.Expression
import tools.aqua.konstraints.smt.Index
import tools.aqua.konstraints.smt.RealAdd
import tools.aqua.konstraints.smt.RealDiv
import tools.aqua.konstraints.smt.RealMul
import tools.aqua.konstraints.smt.RealNeg
import tools.aqua.konstraints.smt.RealSub
import tools.aqua.konstraints.smt.SortedVar
import tools.aqua.konstraints.smt.Theories
import tools.aqua.konstraints.smt.UnaryExpression
import tools.aqua.konstraints.smt.toSymbol

object ComplexAddDecl :
    UserDefinedSMTFunction2<ComplexSort, ComplexSort, ComplexSort>(
        "cpx.add".toSymbol(),
        ComplexSort,
        SortedVar("x".toSymbol(), ComplexSort),
        SortedVar("y".toSymbol(), ComplexSort),
        { x: Expression<ComplexSort>, y: Expression<ComplexSort> ->
            ComplexSort.construct(RealAdd(x.re(), y.re()), RealAdd(x.im(), y.im()))
        },
    ) {
    override fun constructDynamic(
        args: List<Expression<*>>,
        indices: List<Index>,
    ): Expression<ComplexSort> {
        @Suppress("UNCHECKED_CAST")
        return ComplexMul(args[0] as Expression<ComplexSort>, args[1] as Expression<ComplexSort>)
    }
}

class ComplexAdd(
    override val lhs: Expression<ComplexSort>,
    override val rhs: Expression<ComplexSort>,
) : BinaryExpression<ComplexSort, ComplexSort, ComplexSort>("cpx.add".toSymbol(),
    ComplexSort
) {
    override val theories: Set<Theories> = emptySet()
    override val func = ComplexAddDecl // this is important so the context finds the related function

    override fun copy(children: List<Expression<*>>): Expression<ComplexSort> {
        TODO("Not yet implemented")
    }
}

object ComplexSubDecl :
    UserDefinedSMTFunction2<ComplexSort, ComplexSort, ComplexSort>(
        "cpx.sub".toSymbol(),
        ComplexSort,
        SortedVar("x".toSymbol(), ComplexSort),
        SortedVar("y".toSymbol(), ComplexSort),
        { x: Expression<ComplexSort>, y: Expression<ComplexSort> ->
            ComplexSort.construct(RealSub(x.re(), y.re()), RealSub(x.im(), y.im()))
        },
    ) {
    override fun constructDynamic(
        args: List<Expression<*>>,
        indices: List<Index>,
    ): Expression<ComplexSort> {
        @Suppress("UNCHECKED_CAST")
        return ComplexSub(args[0] as Expression<ComplexSort>, args[1] as Expression<ComplexSort>)
    }
}

class ComplexSub(
    override val lhs: Expression<ComplexSort>,
    override val rhs: Expression<ComplexSort>,
) : BinaryExpression<ComplexSort, ComplexSort, ComplexSort>("cpx.sub".toSymbol(),
    ComplexSort
) {
    override val theories: Set<Theories> = emptySet()
    override val func = ComplexSubDecl // this is important so the context finds the related function

    override fun copy(children: List<Expression<*>>): Expression<ComplexSort> {
        TODO("Not yet implemented")
    }
}

object ComplexMulDecl :
    UserDefinedSMTFunction2<ComplexSort, ComplexSort, ComplexSort>(
        "cpx.mul".toSymbol(),
        ComplexSort,
        SortedVar("x".toSymbol(), ComplexSort),
        SortedVar("y".toSymbol(), ComplexSort),
        { x: Expression<ComplexSort>, y: Expression<ComplexSort> ->
            ComplexSort.construct(
                RealSub(RealMul(x.re(), y.re()), RealMul(x.im(), y.im())),
                RealAdd(RealMul(x.re(), y.im()), RealMul(x.im(), y.re())),
            )
        },
    ) {
    override fun constructDynamic(
        args: List<Expression<*>>,
        indices: List<Index>,
    ): Expression<ComplexSort> {
        @Suppress("UNCHECKED_CAST")
        return ComplexMul(args[0] as Expression<ComplexSort>, args[1] as Expression<ComplexSort>)
    }
}

class ComplexMul(
    override val lhs: Expression<ComplexSort>,
    override val rhs: Expression<ComplexSort>,
) : BinaryExpression<ComplexSort, ComplexSort, ComplexSort>("cpx.mul".toSymbol(),
    ComplexSort
) {
    override val theories: Set<Theories> = emptySet()
    override val func = ComplexMulDecl // this is important so the context finds the related function

    override fun copy(children: List<Expression<*>>): Expression<ComplexSort> {
        TODO("Not yet implemented")
    }
}

object ComplexDivDecl :
    UserDefinedSMTFunction2<ComplexSort, ComplexSort, ComplexSort>(
        "cpx.div".toSymbol(),
        ComplexSort,
        SortedVar("x".toSymbol(), ComplexSort),
        SortedVar("y".toSymbol(), ComplexSort),
        { x: Expression<ComplexSort>, y: Expression<ComplexSort> -> ComplexMul(x, ComplexInv(y)) },
    ) {
    override fun constructDynamic(
        args: List<Expression<*>>,
        indices: List<Index>,
    ): Expression<ComplexSort> {
        @Suppress("UNCHECKED_CAST")
        return ComplexDiv(args[0] as Expression<ComplexSort>, args[1] as Expression<ComplexSort>)
    }
}

class ComplexDiv(
    override val lhs: Expression<ComplexSort>,
    override val rhs: Expression<ComplexSort>,
) : BinaryExpression<ComplexSort, ComplexSort, ComplexSort>("cpx.div".toSymbol(),
    ComplexSort
) {
    override val theories: Set<Theories> = emptySet()
    override val func = ComplexDivDecl // this is important so the context finds the related function

    override fun copy(children: List<Expression<*>>): Expression<ComplexSort> {
        TODO("Not yet implemented")
    }
}

object ComplexInvDecl :
    UserDefinedSMTFunction1<ComplexSort, ComplexSort>(
        "cpx.inv".toSymbol(),
        ComplexSort,
        SortedVar("x".toSymbol(), ComplexSort),
        { x: Expression<ComplexSort> ->
            ComplexSort.construct(
                RealDiv(x.re(), RealAdd(RealMul(x.re(), x.re()), RealMul(x.im(), x.im()))),
                RealNeg(RealDiv(x.im(), RealAdd(RealMul(x.re(), x.re()), RealMul(x.im(), x.im())))),
            )
        },
    ) {
    override fun constructDynamic(
        args: List<Expression<*>>,
        indices: List<Index>,
    ): Expression<ComplexSort> {
        @Suppress("UNCHECKED_CAST")
        return ComplexInv(args[0] as Expression<ComplexSort>)
    }
}

class ComplexInv(
    override val inner: Expression<ComplexSort>,
) : UnaryExpression<ComplexSort, ComplexSort>("cpx.inv".toSymbol(),
    ComplexSort
) {
    override val theories: Set<Theories> = emptySet()
    override val func = ComplexInvDecl // this is important so the context finds the related function

    override fun copy(children: List<Expression<*>>): Expression<ComplexSort> {
        TODO("Not yet implemented")
    }
}