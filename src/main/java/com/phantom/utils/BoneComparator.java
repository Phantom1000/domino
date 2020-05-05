package com.phantom.utils;

import com.phantom.entities.Bone;
import com.phantom.entities.Half;

import java.util.Comparator;

public class BoneComparator implements Comparator<Bone> {
    public int compare(Bone b1, Bone b2) {
        int sum1 = 0, sum2 = 0;
        for (Half h : b1.getHalfs()) {
            sum1 += h.getPoints();
        }
        for (Half h : b2.getHalfs()) {
            sum2 += h.getPoints();
        }
        if (sum1 > sum2) {
            return 1;
        }
        return -1;
    }
}