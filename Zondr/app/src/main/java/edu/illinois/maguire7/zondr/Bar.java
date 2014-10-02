package edu.illinois.maguire7.zondr;

/**
 * Created by MaguireM on 9/25/14.
 */
public class Bar {

    private String mBarName;
    private int mPercentGents;
    private int mPercentLadies;
    private int mCapacity;
    private int mLineLength;
    private int mCoverCost;

    public Bar(String barName, int percentGents, int percentLadies, int capacity, int lineLength, int coverCost){
        mBarName = barName;
        mPercentGents = percentGents;
        mPercentLadies = percentLadies;
        mCapacity = capacity;
        mLineLength = lineLength;
        mCoverCost = coverCost;
    }

    public String getBarName() {
        return mBarName;
    }

    public int getPercentGents() {
        return mPercentGents;
    }

    public int getPercentLadies() {
        return mPercentLadies;
    }

    public int getCapacity() {
        return mCapacity;
    }

    public int getLineLength() {
        return mLineLength;
    }

    public int getCoverCost() {
        return mCoverCost;
    }

}
