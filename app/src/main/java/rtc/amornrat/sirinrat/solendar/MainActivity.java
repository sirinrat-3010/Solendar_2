package rtc.amornrat.sirinrat.solendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements OnClickListener {
    private static final String tag = "Main";
    private Button selectedDayMonthYearButton;
    private Button currentMonth;
    private ImageView prevMonth;
    private ImageView nextMonth;
    private GridView calendarView;
    private GridCellAdapter adapter;
    private Calendar _calendar;
    private int month, year;

    private ManageTABLE objManageTABLE;

    private void getRequestParameters() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                if (extras != null) {
                    Log.d(tag, "+++++----------------->" + extras.getString("params"));
                }
            }
        }
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connected Database
        connectedDatabase();

        //findDate Notification
        findDateNotification();



        _calendar = Calendar.getInstance(Locale.getDefault());
        month = _calendar.get(Calendar.MONTH);
        year = _calendar.get(Calendar.YEAR);



        prevMonth = (ImageView) this.findViewById(R.id.prevMonth);
        prevMonth.setOnClickListener(this);

        currentMonth = (Button) this.findViewById(R.id.currentMonth);

        //Find Current Date
        DateFormat myDateFormat = new SimpleDateFormat("dd / MMMM / yyyy");
        Date currentDate = new Date();
        String strCurrentDate = myDateFormat.format(currentDate);
        currentMonth.setText(strCurrentDate);

        nextMonth = (ImageView) this.findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(this);

        calendarView = (GridView) this.findViewById(R.id.calendar);

        // Initialised
        adapter = new GridCellAdapter(getApplicationContext(), R.id.gridcell, month, year);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);

    }//Main Method

    private void findDateNotification() {

      //read all column Date
        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                MODE_PRIVATE, null);
        Cursor objCursor = objSqLiteDatabase.rawQuery("SELECT * FROM todoTABLE", null);
        String[] databaseDateStrings = new String[objCursor.getCount()];
        objCursor.moveToFirst();
        for (int i=0;i<objCursor.getCount();i++) {
            databaseDateStrings[i] = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.DATABASE_Date));
            Log.d("Solendar", "Date[" + Integer.toString(i) + "] =" + databaseDateStrings[i]);
            objCursor.moveToNext();

        }//for
        objCursor.close();
    }//

    @Override
    protected void onRestart() {
        super.onRestart();

        connectedDatabase();

    }

    private void connectedDatabase() {
        objManageTABLE = new ManageTABLE(this);
    }

    @Override
    public void onClick(View v) {
        if (v == prevMonth) {
            if (month <= 1) {
                month = 11;
                year--;
            } else {
                month--;
            }

            Log.d(tag, "Before 1 MONTH " + "Month: " + month + " " + "Year: " + year);
            adapter = new GridCellAdapter(getApplicationContext(), R.id.gridcell, month, year);
            _calendar.set(year, month, _calendar.get(Calendar.DAY_OF_MONTH));
            currentMonth.setText(_calendar.getTime().toString());

            adapter.notifyDataSetChanged();
            calendarView.setAdapter(adapter);
        }
        if (v == nextMonth) {
            if (month >= 11) {
                month = 0;
                year++;
            } else {
                month++;
            }

            Log.d(tag, "After 1 MONTH " + "Month: " + month + " " + "Year: " + year);
            adapter = new GridCellAdapter(getApplicationContext(), R.id.gridcell, month, year);
            _calendar.set(year, month, _calendar.get(Calendar.DAY_OF_MONTH));
            currentMonth.setText(_calendar.getTime().toString());
            adapter.notifyDataSetChanged();
            calendarView.setAdapter(adapter);
        }
    }

    //
    public class GridCellAdapter extends BaseAdapter implements OnClickListener {
        private static final String tag = "GridCellAdapter";
        private final Context _context;
        private final List<String> list;
        private final String[] weekdays = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        private final int month, year;
        int daysInMonth, prevMonthDays;
        private final int currentDayOfMonth;
        private Button gridcell;

        // Days in Current Month
        public GridCellAdapter(Context context, int textViewResourceId, int month, int year) {
            super();
            this._context = context;
            this.list = new ArrayList<String>();
            this.month = month;
            this.year = year;

            Log.d(tag, "Month: " + month + " " + "Year: " + year);
            Calendar calendar = Calendar.getInstance();
            currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            printMonth(month, year);
        }

        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        private void printMonth(int mm, int yy) {
            // The number of days to leave blank at
            // the start of this month.
            int trailingSpaces = 0;
            int leadSpaces = 0;
            int daysInPrevMonth = 0;
            int prevMonth = 0;
            int prevYear = 0;
            int nextMonth = 0;
            int nextYear = 0;

            GregorianCalendar cal = new GregorianCalendar(yy, mm, currentDayOfMonth);

            // Days in Current Month
            daysInMonth = daysOfMonth[mm];
            int currentMonth = mm;
            if (currentMonth == 11) {
                prevMonth = 10;
                daysInPrevMonth = daysOfMonth[prevMonth];
                nextMonth = 0;
                prevYear = yy;
                nextYear = yy + 1;
            } else if (currentMonth == 0) {
                prevMonth = 11;
                prevYear = yy - 1;
                nextYear = yy;
                daysInPrevMonth = daysOfMonth[prevMonth];
                nextMonth = 1;
            } else {
                prevMonth = currentMonth - 1;
                nextMonth = currentMonth + 1;
                nextYear = yy;
                prevYear = yy;
                daysInPrevMonth = daysOfMonth[prevMonth];
            }

            // Compute how much to leave before before the first day of the
            // month.
            // getDay() returns 0 for Sunday.
            trailingSpaces = cal.get(Calendar.DAY_OF_WEEK) - 1;

            if (cal.isLeapYear(cal.get(Calendar.YEAR)) && mm == 1) {
                ++daysInMonth;
            }

            // Trailing Month days
            for (int i = 0; i < trailingSpaces; i++) {
                list.add(String.valueOf((daysInPrevMonth - trailingSpaces + 1) + i) + "-GREY" + "-" + months[prevMonth] + "-" + prevYear);
            }

            // Current Month Days
            for (int i = 1; i <= daysInMonth; i++) {
                list.add(String.valueOf(i) + "-WHITE" + "-" + months[mm] + "-" + yy);
            }

            // Leading Month days
            for (int i = 0; i < list.size() % 7; i++) {
                Log.d(tag, "NEXT MONTH:= " + months[nextMonth]);
                list.add(String.valueOf(i + 1) + "-GREY" + "-" + months[nextMonth] + "-" + nextYear);
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(tag, "getView ...");
            View row = convertView;
            if (row == null) {
                // ROW INFLATION
                Log.d(tag, "Starting XML Row Inflation ... ");
                LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.gridcell, parent, false);

                Log.d(tag, "Successfully completed XML Row Inflation!");
            }

            gridcell = (Button) row.findViewById(R.id.gridcell);
            gridcell.setOnClickListener(this);

            // ACCOUNT FOR SPACING

            Log.d(tag, "Current Day: " + currentDayOfMonth);
            String[] day_color = list.get(position).split("-");
            gridcell.setText(day_color[0]);
            gridcell.setTag(day_color[0] + "-" + day_color[2] + "-" + day_color[3]);

            if (day_color[1].equals("GREY")) {
                gridcell.setTextColor(Color.LTGRAY);
            }
            if (day_color[1].equals("WHITE")) {
                gridcell.setTextColor(Color.BLACK);
            }
            if (position == currentDayOfMonth) {
                gridcell.setTextColor(Color.BLACK);
            }

            return row;
        }

        @Override
        public void onClick(View view) {
            String date_month_year = (String) view.getTag();
            Toast.makeText(getApplicationContext(), date_month_year, Toast.LENGTH_SHORT).show();

            checkDatabase(date_month_year);



        }   // onClick
    }

    private void checkDatabase(String date_month_year) {

        //Check Date
        try {

            String[] resultStrings = objManageTABLE.searchDate(date_month_year);
            Log.d("Solendar", "Have " + resultStrings[1]);

            if (resultStrings[1] != null) {

                Intent objIntent = new Intent(MainActivity.this, ShowToDoList.class);
                objIntent.putExtra("Date", date_month_year);
                startActivity(objIntent);
            } else {
                myAlertDialog(date_month_year);
            }



        } catch (Exception e) {
            myAlertDialog(date_month_year);
        }

    }   //checkDatabase

    private void myAlertDialog(final String date_month_year) {

        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.icon_myaccount);
        objBuilder.setTitle("วันนี่ว่าง");
        objBuilder.setMessage("วันนี่ยังไม่มีข้อมูล ต้องการเพิ่มข้อมูล เลือก OK ? ");
        objBuilder.setCancelable(false);
        objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent objIntent = new Intent(MainActivity.this, AddToDoList.class);
                objIntent.putExtra("Date", date_month_year);
                startActivity(objIntent);
                dialogInterface.dismiss();
            }
        });
        objBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        objBuilder.show();

    }   // myAlertDialog
}
