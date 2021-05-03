package edu.polytech.si3.ihm.plantish.community;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.CalendarView;
import android.widget.Toast;


import java.util.Calendar;
import java.util.GregorianCalendar;

import edu.polytech.si3.ihm.plantish.R;

public class HitoriqueIncidents extends AppCompatActivity {

    CalendarView cv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitorique_incidents);
        cv=findViewById(R.id.calendarView);
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

                Toast.makeText(getApplicationContext(), month +"-"+ dayOfMonth +"-"+ year, Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        });
    }

}