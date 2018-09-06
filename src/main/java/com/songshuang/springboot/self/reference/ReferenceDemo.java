package com.songshuang.springboot.self.reference;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferenceDemo {

    public static void main(String[] args) {

        SoftReferenceObject softReferenceObject = new SoftReferenceObject();

        SoftReference<SoftReferenceObject> softReference = new SoftReference<SoftReferenceObject>(softReferenceObject);

        WeakReferenceObject weakReferenceObject = new WeakReferenceObject();

        WeakReference<WeakReferenceObject> weakReference = new WeakReference<WeakReferenceObject>(weakReferenceObject);

        System.gc();

    }


    public static class SoftReferenceObject {

        public SoftReferenceObject() {}

        @Override
        public void finalize() {
            System.out.printf("Soft Reference Object GC...");
        }

    }

    public static class WeakReferenceObject {
        @Override
        public void finalize() {
            System.out.printf("Weak Reference Object GC...");
        }
    }
}
