package web.bahuma.shoppinglist;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import web.bahuma.shoppinglist.R;

public class ItemsActivity extends ActionBarActivity {
    private DatabaseManager mHelper;
    private SQLiteDatabase mDatabase;
    private AlertDialog.Builder mBuiler;

    private ListView itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        this.mHelper = new DatabaseManager(this);
        this.itemsList = (ListView) findViewById(R.id.list_items);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDatabase = mHelper.getReadableDatabase();

        loadItems();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDatabase.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.items, menu);
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
        if (id == R.id.action_add_item) {
            Toast.makeText(this, "Add Item clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadItems() {
        Cursor itemCursor = mDatabase.query(
                "items",
                new String[] {"_id", "name"},
                null, null, null, null,
                "name");

        startManagingCursor(itemCursor);

        SimpleCursorAdapter itemsAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                itemCursor,
                new String[] {"name"},
                new int[] {android.R.id.text1}
        );

        this.itemsList.setAdapter(itemsAdapter);

        this.itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), "Item clicked: " + id, Toast.LENGTH_LONG).show();
            }
        });
    }
}
