package com.tingle.tinglev4;

/**
 * Created by MAURICE on 03/03/2017.
 */

public class Thing {
    private String mWhat = null;
    private String mWhere = null;

    public Thing(String mWhat, String mWhere){
        this.mWhat = mWhat;
        this.mWhere = mWhere;
    }
    @Override
    public String toString(){
        return oneLine("is here: ");
    }
    public String getWhat() {
        return mWhat;
    }
    public void setWhat(String what) {
        mWhat= what;
    }
    public String getWhere() {
        return mWhere;
    }
    public void setWhere(String where) {
        mWhere= where;
    }
    public String oneLine(String post) {
        return mWhat + " "+post + mWhere;
    }

}
