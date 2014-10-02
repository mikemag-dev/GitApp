package edu.illinois.maguire7.criminalintent;
import java.util.Date;
import java.util.UUID;


public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mIsSolved;

    public Crime(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    @Override
    public String toString(){
        return mTitle;
    }

    public boolean isSolved() {
        return mIsSolved;
    }

    public void setIsSolved(boolean isSolved) {
        mIsSolved = isSolved;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }



    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }
}
