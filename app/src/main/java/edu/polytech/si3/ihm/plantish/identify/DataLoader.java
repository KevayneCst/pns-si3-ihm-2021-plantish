package edu.polytech.si3.ihm.plantish.identify;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import static edu.polytech.si3.ihm.plantish.identify.Paths.PATH_TO_CRITERIA_JSON;
import static edu.polytech.si3.ihm.plantish.identify.Paths.PATH_TO_PLANTS_JSON;

/**
 * This class is for retreiving data from the firebase
 */
public abstract class DataLoader {
    private static StorageReference storageReference;



    public static void setImageViewForCriterion(Context context, Path pathToElement, ImageView imageView) throws Exception {
        String firebasePathToImage = getCriterionDataFromJson(context, pathToElement);
        setImageViewFromPath(firebasePathToImage,imageView);
    }

    /**
     * this method is for setting up an image view using image stored in the backend
     *
     * @param :         path of the image in the storage
     * @param imageView : image view to be set up
     * @throws IOException
     */

    public static void setImageViewFromPath(String pathToElement, ImageView imageView){
        storageReference = FirebaseStorage.getInstance().getReference().child(pathToElement);
        final File localFile;
        try {
            localFile = File.createTempFile("prefix", "suffix");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(localFile.getAbsoluteFile()));
                    imageView.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * The hierarchy in the firebase is as follows criterion name (ex: plant type) | criterion option ( ex: haie) | data (ex: description)
     * The path has 3 children
     * this function updates the text view with the data in the firebase
     *
     * @param pathToElement
     * @param textView
     */
    public static void setCriterionInfoTextView(Context context,Path pathToElement, TextView textView) throws Exception {
        textView.setText(getCriterionDataFromJson(context, pathToElement));
    }


    public static String readFileAsString(Context context, int pathToFile) throws Exception {
        InputStream is = context.getResources().openRawResource(pathToFile);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return writer.toString();
    }

    public static String getCriterionDataFromJson(Context context, Path pathToElement) throws Exception {
        String jsonString = readFileAsString(context, PATH_TO_CRITERIA_JSON);
        JSONObject obj = new JSONObject(jsonString);
        return obj.getJSONObject(pathToElement.getChild(0)).getJSONObject(pathToElement.getChild(1)).getString(pathToElement.getChild(2));
    }

    public static String getPlantsDataFromJson(Context context) throws Exception {
        return readFileAsString(context, PATH_TO_PLANTS_JSON);
    }

    public static int getNumberOfPlantsFromJsonString(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        JSONArray jArray = obj.getJSONArray("Plants");
        return jArray.length();
    }



    public static String getFiltredPlantsByCriterion(String raw, String plantCriterionLabel, String criterionLabelValue) throws JSONException {
        JSONObject result = new JSONObject();
        JSONArray updatedArray = new JSONArray();
        JSONObject obj = new JSONObject(raw);
        JSONArray jsonArray = obj.getJSONArray("Plants");
        for (int i = 0;i<jsonArray.length();i++){
            if (jsonArray.getJSONObject(i).getString(plantCriterionLabel).equals(criterionLabelValue)){
                updatedArray.put(jsonArray.getJSONObject(i));
            }
        }
        result.put("Plants",updatedArray);
        return result.toString();
    }



}
