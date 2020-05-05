package com.phantom.services;

import com.phantom.entities.Bone;
import com.phantom.entities.Half;

public class BoneService {
    private static BoneService instance;

    private BoneService() {}

    public static BoneService getInstance() {
        if (instance == null)
            instance = new BoneService();
        return instance;
    }

    public boolean attach(Bone to, Bone by) {
        for (Half half : to.getHalfs()) {
            if (half.getLink() == null) {
                for (Half halfb : by.getHalfs()) {
                    if (halfb.getLink() == null && halfb.getPoints() == half.getPoints()) {
                        halfb.setLink(to);
                        half.setLink(by);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isDuplicate(Bone b, int i) {
        for (Half h : b.getHalfs()) {
            if (h.getPoints() != i) {
                return false;
            }
        }

        return true;
    }
            
}