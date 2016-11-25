package com.mdb.statwiz.utils;

/**
 * Created by Sayan Paul on 11/25/2016.
 */

public class Tuple<K, V> {
    public final K key;
    public final V value;

    public Tuple(K key, V value) {
        this.key = key;
        this.value = value;
    }
}