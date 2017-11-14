package dk.staunstrups.thingBrowser;
import android.content.Context;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmResults;

public class ThingsDB {
  private static Realm realm;
  private static ThingsDB sThingsDB; //singleton

  public static ThingsDB get(Context context) {
    if (sThingsDB == null) {
      realm = Realm.getDefaultInstance();
      sThingsDB= new ThingsDB(context);
    }
    return sThingsDB;
  }

  public OrderedRealmCollection<Thing> getThingsDB() {
  return realm.where(Thing.class).findAll();
}

  public void addThing(Thing thing) {
    final Thing fthing= thing;
    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        realm.copyToRealm(fthing);
      }});
  }

  public void removeThing(Thing thing) {
    final Thing fthing= thing;
    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        RealmResults<Thing> rows=realm.where(Thing.class).equalTo("mId", fthing.getmId()).findAll();
        if (rows.size() > 0) rows.get(0).deleteFromRealm();
      }});
  }

  // Fill database for testing purposes
  private ThingsDB(Context context) {
    /*Hack to add contents to an empty database */
      addThing(new Thing("Mouse+", "Desk"));
      addThing(new Thing("iPhone+", "Desk"));
      addThing(new Thing("Sunglasses+", "Desk"));
      addThing(new Thing("Keyboard+", "Desk"));
      addThing(new Thing("Display+", "Desk"));
      addThing(new Thing("Computer+", "Desk"));
      addThing(new Thing("Cable+", "Desk"));
  }
}