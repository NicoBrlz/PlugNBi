package fr.an.exprlib.sql.eval;

import lombok.val;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ExprEvalUtils {

    public static <T> Object[] applyObjectFunctions(
            T obj,
            List<? extends Function<T,?>> evalFuncs) {
        int len = evalFuncs.size();
        Object[] res = new Object[len];
        for(int i = 0; i < len; i++) {
            Function<T,?> func = evalFuncs.get(i);
            res[i] = func.apply(obj);
        }
        return res;
    }

    public static <T> Object[] applyObjectEvalFunctions(
            T obj,
            List<BiFunction<T,EvalCtx,Object>> evalFuncs,
            EvalCtx evalCtx) {
        int len = evalFuncs.size();
        Object[] res = new Object[len];
        for(int i = 0; i < len; i++) {
            val evalFunc = evalFuncs.get(i);
            res[i] = evalFunc.apply(obj, evalCtx);
        }
        return res;
    }

}
