package fr.an.exprlib.utils;

import com.google.common.collect.ImmutableList;
import lombok.val;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class LsUtils {
    public static <TRes,TSrc> List<TRes> map(Collection<TSrc> src, Function<TSrc,TRes> mapFunc) {
        return src.stream().map(mapFunc).collect(toList());
    }

    public static <TRes,TSrc> ImmutableList<TRes> mapImmutableList(Collection<TSrc> src, Function<TSrc,TRes> mapFunc) {
        return ImmutableList.copyOf(map(src, mapFunc));
    }

    public static <T,TKey> Map<TKey,T> toMap(Collection<T> src, Function<T,TKey> keyExtractFunc) {
        val res = new LinkedHashMap<TKey,T>();
        for(val e : src) {
            val key = keyExtractFunc.apply(e);
            res.put(key, e);
        }
        return res;
    }

    public static <K> Map<K,Integer> toMapWithIndex(Collection<K> keys) {
        Map<K,Integer> res = new LinkedHashMap<>();
        int idx = 0;
        for(Iterator<K> keyIter = keys.iterator(); keyIter.hasNext(); idx++) {
            K key = keyIter.next();
            res.put(key, idx);
        }
        return res;
    }

}
