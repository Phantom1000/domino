package com.phantom;

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
                    if (halfb.getLink() == null && halfb.getNumber() == half.getNumber()) {
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
            if (h.getNumber() != i) {
                return false;
            }
        }

        return true;
    }
            
}