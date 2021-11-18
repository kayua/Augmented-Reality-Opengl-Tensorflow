package com.tensorlearning.facemasks.Engine.Core;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.tensorlearning.facemasks.R;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class Main extends AppCompatActivity implements View.OnClickListener {

    private static final int FILE_SELECT_CODE = 0;
    public String TAG="TAG";
    private Button chooser2;
    private ArrayList<String> array = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        chooser2 = (Button)findViewById(R.id.chooser2);
        chooser2.setOnClickListener(this);
        array.clear();
        array.add("101");
        array.add("102");
        array.add("103");
        array.add("104");
        array.add("105");
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  R.layout.simple_list_item_1, array);
        spinner.setAdapter(adapter);


    }


    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Файл оруулахаар сонгоно уу"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "File Manager суулгана уу!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
  //  public native String stringFromJNI();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d(TAG, "File Uri: " + uri.toString());
                    Toast.makeText(this, "uri:"+ uri.toString(), Toast.LENGTH_LONG).show();
                    // Get the path
                    String path = "null";
                    try {
                        path = FileUtils.getPath(this, uri);

                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "error"+e.toString(), Toast.LENGTH_LONG).show();
                    }
                    Log.d(TAG, "File Path: " + path);
//                    Toast.makeText(this,  "path"+path.toString(), Toast.LENGTH_LONG).show();
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }else{
                    Toast.makeText(this, "Файл сонгоно уу!", Toast.LENGTH_LONG).show();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.chooser2:{
                Intent inte = new Intent(this, MainActivity.class);
                startActivity(inte);
                break;
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                showAboutDialog();
                return true;
            case R.id.menu_guide: Dialog dd = new AlertDialog.Builder(this)
                        .setTitle("Заавар")
                        .setMessage(R.string.guide_text)
                        .setPositiveButton(R.string.ok, null)
                        .show();

                return true;
            case R.id.menu_exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void showAboutDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(R.string.about_text)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
