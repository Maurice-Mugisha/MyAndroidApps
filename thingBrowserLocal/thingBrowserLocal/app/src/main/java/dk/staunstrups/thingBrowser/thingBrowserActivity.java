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

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class thingBrowserActivity extends Activity {

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

  protected void onResume() {
    super.onResume();
    thingsDB = ThingsDB.get(this);
    setUpUI();
  }

  private void setUpUI() {
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
}


