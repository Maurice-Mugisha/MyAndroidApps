package dk.staunstrups.thingBrowser;
import android.app.Application;

import io.realm.Realm;

public class thingBrowserApplication extends Application {
  @Override
  public void onCreate() {
   super.onCreate();
   Realm.init(this);
  }
 }