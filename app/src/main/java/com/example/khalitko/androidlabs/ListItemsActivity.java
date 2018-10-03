package com.example.khalitko.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import static com.example.khalitko.androidlabs.StartActivity.ACTIVITY_NAME;

public class ListItemsActivity extends Activity {

    protected static final String ACTIVITY_NAME = "LoginActivity";

    private ImageButton imageButton;
    protected Switch aSwitch;
    protected CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        imageButton = (ImageButton) findViewById(R.id.imageItems);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }
            }

        });

        aSwitch = findViewById(R.id.switchSlider);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Toast.makeText(ListItemsActivity.this, isChecked ? "Switch is On" : "Switch is Off", isChecked ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();

            }

        });

        checkBox = (CheckBox)findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this)
                        .setMessage(R.string.dialog_message)
                        .setTitle(R.string.dialog_title)
//                        .setCancelable(true)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {



                @Override
                public void onClick(DialogInterface dialog, int id){
                    dialog.cancel();
                        Intent resultIntent = new Intent(  );
                        resultIntent.putExtra("Response", "My information to share");
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();

                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                if (checkBox.isChecked()) {
                    alertDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(true);
                    alertDialog.getButton(Dialog.BUTTON_NEGATIVE).setEnabled(true);


                }else {

//                    alertDialog.cancel();
//                    checkBox.setSelected(false);
//                    checkBox.toggle();
                }
//                checkBox.toggle();
            }

        });




    }
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                CharSequence text;
//                int duration;
//                if (isChecked == true){
//                    text = "Switch is On";
//                    duration = Toast.LENGTH_SHORT;
//                } else {
//                    text = "Switch is Off";
//                    duration = Toast.LENGTH_LONG;
//                }
//
//                Toast toast = Toast.makeText(ListItemsActivity.this, text, duration);
//                toast.show();
//            }
//        });
//
//        checkBox = findViewById(R.id.checkBox);
//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this)
//                        .setMessage(R.string.dialog_message)
//                        .setTitle(R.string.dialog_title)
//                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int id) {
//                                Intent resultIntent = new Intent();
//                                resultIntent.putExtra("Response","Here is my response");
//                                setResult(Activity.RESULT_OK,resultIntent);
//                                finish();
//                            }
//                        })
//                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int id) {
//
//                            }
//                        });
//                builder.show();
//            }
//        });
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
