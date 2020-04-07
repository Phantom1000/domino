package com.phantom;

import java.util.Comparator;

public class BoneComparator implements Comparator<Bone> {
    public int compare(Bone b1, Bone b2) {
        int sum1 = 0, sum2 = 0;
        for (Half h : b1.getHalfs()) {
            sum1 += h.getNumber();
        }
        for (Half h : b2.getHalfs()) {
            sum2 += h.getNumber();
        }
        if (sum1 > sum2) {
            return 1;
        }
        return -1;
    }
}