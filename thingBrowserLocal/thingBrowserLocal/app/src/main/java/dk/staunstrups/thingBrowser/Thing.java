package dk.staunstrups.thingBrowser;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Thing extends RealmObject {
  private String mWhat= null;
  private String mWhere= null;
  @PrimaryKey
  private String mId;

  public Thing() {
    mWhat="no what";
    mWhere="no where";
    mId= UUID.randomUUID().toString();
  }

  public Thing(String what, String where) {
    mWhat= what;
    mWhere= where;
    mId= UUID.randomUUID().toString();
  }

  public String getmWhat() {
    return mWhat;
  }
  public void setmWhat(String mWhat) {
    this.mWhat=mWhat;
  }
  public String getmWhere() {
    return mWhere;
  }
  public void setmWhere(String mWhere) {
    this.mWhere=mWhere;
  }
  public String getmId() { return mId;  }
}
