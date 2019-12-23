package Kamil215691.ZPO.LAB6.Zad2;

import java.util.Map;
import java.util.function.BiFunction;

public class CustomMergeUtilities {

    public static <K,V> V mergeUsingContains(Map<K, V> map, K key, V value,  BiFunction<? super V,? super V,? extends V> remappingFunction){
        if(map.containsKey(key)){
            V value1 = map.get(key);
            value1 = value1 != null ? remappingFunction.apply(value1,value) : value;
            map.put(key, value1);
            return value1;
        }
        else{
            map.put(key,value);
            return value;
        }
    }

    public static <K,V> V mergeUsingGetAndNullCheck(Map<K, V> map, K key, V value,  BiFunction<? super V,? super V,? extends V> remappingFunction){
        V value1 = map.get(key);
        if(value1 == null){
            value1 = value;
        }
        else {
            value1 = remappingFunction.apply(value, value1);
        }
        map.put(key,value1);
        return value1;
    }

    public static <K,V> V mergeUsingGetOrDefault(Map<K, V> map, K key, V value,  BiFunction<? super V,? super V,? extends V> remappingFunction){
        V value1 = map.getOrDefault(key, (value instanceof Number) ? (V) ((Number) 0) : ((value instanceof String) ? (V) "" : null));
        value1 = remappingFunction.apply(value1, value);
        map.put(key, value1);
        return value1;
    }

    public static <K,V> V mergeUsingPutIfAbsent(Map<K, V> map, K key, V value,  BiFunction<? super V,? super V,? extends V> remappingFunction){
        V value1 = map.putIfAbsent(key, value);
        if(value1 != null) {
            value = remappingFunction.apply(value1,value);
            map.put(key, value);
        }
        return value;
    }
}
