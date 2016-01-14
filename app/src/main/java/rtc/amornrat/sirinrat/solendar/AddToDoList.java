package rtc.amornrat.sirinrat.solendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddToDoList extends AppCompatActivity {
    //Explicit
    private TextView showDateTextView;
    private EditText toDoEditText;
    private String showDateString, todoString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do_list);

        //Bind Widget
        bindWidget();

        //Show Date
        showDate();
    }//Main Method

    private void showDate() {
        showDateString = getIntent().getStringExtra("Date");
        showDateTextView.setText(showDateString);

    }//ShowDate

    private void bindWidget() {
        showDateTextView = (TextView) findViewById(R.id.textView8);
        toDoEditText = (EditText) findViewById(R.id.editText);
    }

    public void clickSaveData(View view) {
        todoString = toDoEditText.getText().toString().trim();

        //Check Space
        if (todoString.equals("")) {
            Toast.makeText(AddToDoList.this, "กรุณากรอกในช่องว่าง",Toast.LENGTH_SHORT).show();
        } else {
        }
    }//clickSaveData

}//MainClass
