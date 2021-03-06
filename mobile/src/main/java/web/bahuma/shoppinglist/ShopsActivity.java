package web.bahuma.shoppinglist;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class ShopsActivity extends ActionBarActivity {
    private DatabaseManager mHelper;
    private SQLiteDatabase mDatabase;
    private AlertDialog.Builder mBuilder;

    private ListView shopsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        this.mHelper = new DatabaseManager(this);
        this.shopsList = (ListView) findViewById(R.id.list_shops);
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

            final EditText input = new EditText(this);

            mBuilder = new AlertDialog.Builder(this);
            mBuilder.setTitle(R.string.add_shops_title)
                    .setIcon(android.R.drawable.ic_input_add)
                    .setMessage(R.string.add_shops_message)
                    .setView(input)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            addShop(input.getText().toString());
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();

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

        this.shopsList.setAdapter(shopsAdapter);

        this.shopsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(), "Item clicked: " + id, Toast.LENGTH_LONG).show();

                Intent intent = new Intent (parent.getContext(), ItemsActivity.class);
                intent.putExtra("id", id);

                startActivity(intent);

            }
        });
    }

    private void addShop(String name) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        mDatabase.insert("shops", null, values);
        loadShops();
    }
}
