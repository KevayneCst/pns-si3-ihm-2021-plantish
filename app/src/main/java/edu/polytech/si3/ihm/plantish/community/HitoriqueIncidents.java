package edu.polytech.si3.ihm.plantish.community;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;


import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

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