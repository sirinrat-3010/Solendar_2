package rtc.amornrat.sirinrat.solendar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by masterUNG on 1/14/16 AD.
 */
public class ManageTABLE {

    //Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static final String DATABASE_Date = "Date";
    public static final String DATABASE_ToDo = "ToDo";
    public static final String DATABASE_id = "_id";
    public static final String TABLE_TODO = "todoTABLE";

    public ManageTABLE(Context context) {

        //Connected Database
        objMyOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();

    }   // Constructor

    //Search Engine
    public String[] searchDate(String strDate) {

        try {

            String[] resultStrings = null;
            Cursor objCursor = readSqLiteDatabase.query(TABLE_TODO,
                    new String[]{DATABASE_id, DATABASE_Date, DATABASE_ToDo},
                    DATABASE_Date + "=?",
                    new String[]{String.valueOf(strDate)},
                    null, null, null, null);

            int intCount = objCursor.getColumnCount();
            resultStrings = new String[intCount];

            if (objCursor != null) {
                if (objCursor.moveToFirst()) {
                    for (int i = 0; i < intCount; i++) {
                        resultStrings[i] = objCursor.getString(i);
                    }   //for
                }   //if2
            }   // if1

        } catch (Exception e) {
            return null;
        }

        return new String[0];
    }

}   // Main Class
