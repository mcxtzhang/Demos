package com.mcxtzhang.rxjava2demo.rxjava2;

public class Indexed<T> {
    public final int index;
    public final T item;

    public Indexed(int index, T item) {
        this.index = index;
        this.item = item;
    }

    @Override
    public String toString() {
        return "Indexed{" +
                "item=" + item +
                ", index=" + index +
                '}';
    }
}
