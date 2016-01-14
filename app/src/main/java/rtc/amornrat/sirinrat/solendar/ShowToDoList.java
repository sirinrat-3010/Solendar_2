package rtc.amornrat.sirinrat.solendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


    }//Main Method

    private void bindWidget() {
        showDateTextView = (TextView) findViewById(R.id.txtShowDate);
        todoListView = (ListView) findViewById(R.id.listView);
    }

    private void showDate() {
        showDateString = getIntent().getStringExtra("Date");
        showDateTextView.setText(showDateString);
    }

}//Main Class
