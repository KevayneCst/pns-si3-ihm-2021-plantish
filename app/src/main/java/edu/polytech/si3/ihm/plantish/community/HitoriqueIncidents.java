package edu.polytech.si3.ihm.plantish.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;



import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import edu.polytech.si3.ihm.plantish.R;

public class HitoriqueIncidents extends Fragment {

    CalendarView cv;
    private Context ctx ;
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_hitorique_incidents, container, false);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ctx = getActivity();
        view = getView();
        super.onCreate(savedInstanceState);

        cv= view.findViewById(R.id.calendarView);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                //Toast.makeText(getApplicationContext(), "" + dayOfMonth, 0).show();

                // Call built in calendar
                Calendar cal = new GregorianCalendar();
                cal.set(year, month, dayOfMonth);  // sets the date picker to the clicked date
                cal.add(Calendar.MONTH, 0); // 0 for current month
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE, "Incident");
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, " ");
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTime().getTime());
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTime().getTime() + 3600000); //3600000 for an hour

                Toast.makeText(ctx.getApplicationContext(), month +"-"+ dayOfMonth +"-"+ year, Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        });
    }
}