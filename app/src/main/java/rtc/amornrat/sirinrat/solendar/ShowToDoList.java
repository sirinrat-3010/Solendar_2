package rtc.amornrat.sirinrat.solendar;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ShowToDoList extends AppCompatActivity {
    //Explicit
    private TextView showDateTextView;
    private ListView todoListView;
    private String showDateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_to_do_list);

        //bind widget
        bindWidget();
       //show date
        showDate();


        //Create ListView
        createListView();

    }//Main Method

    public void clickAddToDo(View view) {

        Intent objIntent = new Intent(ShowToDoList.this, AddToDoList.class);
        objIntent.putExtra("Date", showDateString);
        startActivity(objIntent);
        finish();

    }//clickAddToDo

    private void createListView() {

        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                MODE_PRIVATE, null);

        Cursor objCursor = objSqLiteDatabase.rawQuery("SELECT * FROM todoTABLE WHERE Date = " + "'" + showDateString + "'", null);
        objCursor.moveToFirst();
        String[] strTitle = new String[objCursor.getCount()];

        for (int i=0;i<objCursor.getCount();i++) {

            strTitle[i] = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.DATABASE_ToDo));
            objCursor.moveToNext();

        }//for
        objCursor.close();

        MyAdapter objMyAdapter = new MyAdapter(ShowToDoList.this, strTitle);
        todoListView.setAdapter(objMyAdapter);

    }//CreateListview

    private void bindWidget() {
        showDateTextView = (TextView) findViewById(R.id.txtShowDate);
        todoListView = (ListView) findViewById(R.id.listView);
    }

    private void showDate() {
        showDateString = getIntent().getStringExtra("Date");
        showDateTextView.setText(showDateString);
    }

}//Main Class
