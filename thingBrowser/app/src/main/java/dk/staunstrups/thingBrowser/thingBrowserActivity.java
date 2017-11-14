package dk.staunstrups.thingBrowser;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.ObjectServerError;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

public class thingBrowserActivity extends Activity implements SyncUser.Callback {

  // Various constants needed by Realm

  // NEVER put username/password in a real app !!!!!
  private static final String USERNAME= "test@itu.dk";
  private static final String PASSWORD= "napoleon";

  private static final String HOST_ITU= "130.226.142.162";
  private static final String HOST_LOCAL= "JStXPS"; //localhost
  private static final String HOST= HOST_ITU;
  private static final String DBNAME= "realmthings";
  private static final String INITIALS= "JSt";

  //Url
  public static final String AUTH_URL= "http://" + HOST + ":9080/auth";
  public static final String REALM_URL= "realm://" + HOST + ":9080/~/" + DBNAME + INITIALS;

  // GUI variables
  private Button addThing;
  private TextView newWhat, newWhere;
  // area for displaying list of things
  private RecyclerView recyclerView;

  //Database of things - singleton
  private static ThingsDB thingsDB;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_thing_browser);
    setUpRealmSync();
  }

  private class ThingHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    private TextView mWhatTextView, mWhereTextView;

    public ThingHolder(View itemView) {
      super(itemView);
      mWhatTextView= (TextView) itemView.findViewById(R.id.thing_what);
      mWhereTextView= (TextView) itemView.findViewById(R.id.thing_where);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      int pos = recyclerView.getChildLayoutPosition(v);
      Thing t= thingsDB.getThingsDB().get(pos);
      thingsDB.removeThing(t);
    }
  }

  private class ThingAdapter extends RealmRecyclerViewAdapter<Thing, ThingHolder> {
    OrderedRealmCollection<Thing> mThings;

    public ThingAdapter(thingBrowserActivity a, OrderedRealmCollection<Thing> data) {
      super(a, data, true);
      mThings= data;
    }

    @Override
    public ThingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater= LayoutInflater.from(thingBrowserActivity.this);
      View v = layoutInflater.inflate(R.layout.list_item_thing, parent, false);
      return new ThingHolder(v);
    }

    @Override
    public void onBindViewHolder(ThingHolder holder, int position) {
      final Thing thing = mThings.get(position);
      holder.mWhatTextView.setText(thing.getmWhat());
      holder.mWhereTextView.setText(thing.getmWhere());
    }

    @Override
    public int getItemCount(){ return mThings.size(); }
  }

  //Important this must not be called before user is logged in
  private void setUpUI() {
    thingsDB = ThingsDB.get(this);
    newWhat = (TextView) findViewById(R.id.what_text);
    newWhere = (TextView) findViewById(R.id.where_text);

    recyclerView = (RecyclerView) findViewById(R.id.thing_recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(new ThingAdapter(thingBrowserActivity.this, thingsDB.getThingsDB()));

    // Button add thing
    addThing = (Button) findViewById(R.id.add_button);
    addThing.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
      if ((newWhat.getText().length() > 0) && (newWhere.getText().length() > 0)) {
        Thing newThing = new Thing(
          newWhat.getText().toString().trim(),
          newWhere.getText().toString().trim());
        thingsDB.addThing(newThing);
        newWhat.setText("");
        newWhere.setText("");
      }
      }
    });
  }

  private void setUpRealmSync() {
    if (SyncUser.currentUser() == null) {
      SyncCredentials myCredentials = SyncCredentials.usernamePassword(USERNAME, PASSWORD, false);
      SyncUser.loginAsync(myCredentials, AUTH_URL, this);
    } else {
      //User already logged in
      setupSync(SyncUser.currentUser());
    }
  }

  @Override
  public void onSuccess(SyncUser user) {  setupSync(user);  }

  @Override
  public void onError(ObjectServerError error) {
    setUpUI();
    Toast.makeText(this, "Failed to login - Using local database only", Toast.LENGTH_LONG).show();
  }

  private void setupSync(SyncUser user) {
    SyncConfiguration defaultConfig = new SyncConfiguration.Builder(user, REALM_URL).build();
    Realm.setDefaultConfiguration(defaultConfig);
    setUpUI();
    Toast.makeText(this, "Logged in", Toast.LENGTH_LONG).show();
  }
}


