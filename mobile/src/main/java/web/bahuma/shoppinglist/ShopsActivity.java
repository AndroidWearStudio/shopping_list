package web.bahuma.shoppinglist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class ShopsActivity extends ActionBarActivity {
    private DatabaseManager mHelper;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        mHelper = new DatabaseManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDatabase = mHelper.getReadableDatabase();

        loadShops();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDatabase.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.shops, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_add_shop) {
            Toast.makeText(this, "Add Shop clicked", Toast.LENGTH_SHORT).show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadShops() {
        Cursor shopCursor = mDatabase.query(
                "shops",
                new String[] {"_id", "name"},
                null, null, null, null,
                "name");
        startManagingCursor(shopCursor);

        SimpleCursorAdapter shopsAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                shopCursor,
                new String[] {"name"},
                new int[] {android.R.id.text1}
        );

        final ListView shopsList = (ListView) findViewById(R.id.list_shops);
        shopsList.setAdapter(shopsAdapter);
    }
}
