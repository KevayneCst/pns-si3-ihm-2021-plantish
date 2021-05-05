package edu.polytech.si3.ihm.plantish.identify.activities_identification;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import edu.polytech.si3.ihm.plantish.MainActivity;
import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.identify.DataLoader;
import edu.polytech.si3.ihm.plantish.identify.PlantFound;

import static edu.polytech.si3.ihm.plantish.identify.Application.PLANT_FOUND;
import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantCriteria.TYPE;

public class PlantFoundActivity extends Fragment {

    private PlantFound plantFound;
    private TextView label;
    private ImageView picture;
    private Button savePlantButton;
    private Button menuButton;
    private View view;
    private Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_plant_found, container, false);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ctx = getActivity();
        view = getView();

        label = view.findViewById(R.id.plantFoundLabel);
        picture = view.findViewById(R.id.plantFoundPicture);
        savePlantButton = view.findViewById(R.id.savePlantButton);
        menuButton = view.findViewById(R.id.menuButton);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            plantFound = bundle.getParcelable(PLANT_FOUND);
        }
        setActivityWidgets();

    }

    private void setActivityWidgets(){
        label.setText(plantFound.getLabel());
        DataLoader.setImageViewFromPath(plantFound.getPicture(),picture);
        savePlantButton.setOnClickListener(click -> {
            takeScreenshot();
            AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
            alertDialog.setTitle("Succès");
            alertDialog.setMessage("Votre plante a été sauvegardée dans la galerie de votre appareil");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        } );
        menuButton.setOnClickListener(click -> goToMainActivity());
    }

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + plantFound.getLabel() + ".jpg";

            // create bitmap screen capture
            View v1 = getActivity().getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "image", plantFound.getLabel());
            outputStream.flush();
            outputStream.close();

        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private void goToMainActivity(){
        MainActivity.setMainFragment(((AppCompatActivity)ctx).getSupportFragmentManager());

    }



}