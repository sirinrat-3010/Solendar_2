package rtc.amornrat.sirinrat.solendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by SIRINRAT on 14/1/2559.
 */
public class MyAdapter extends BaseAdapter{
    //Explicit
    private Context objContext;
    private String[] titleStrings;


    public MyAdapter(Context objContext, String[] titleStrings) {
        this.objContext = objContext;
        this.titleStrings = titleStrings;
    }//Consructor

    @Override
    public int getCount() {
        return titleStrings.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater objLayoutInflater = (LayoutInflater) objContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View objView = objLayoutInflater.inflate(R.layout.todo_listview, parent, false);
        TextView titleTextView = (TextView) objView.findViewById(R.id.txtToDoShowList);
        titleTextView.setText(titleStrings[position]);

        return objView;
    }
}//Main Class
