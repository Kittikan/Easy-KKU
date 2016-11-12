package kku.charoenrat.kittikan.easykku;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by CSITGIS on 12/11/2559.
 */

public class MyAlert {

    //Explicit
    private Context context;
    private int anInt;
    private String titlesString, messageString;

    public MyAlert(Context context, int anInt, String titlesString, String messageString) {
        this.context = context;
        this.anInt = anInt;
        this.titlesString = titlesString;
        this.messageString = messageString;
    }

    public void MyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setIcon(anInt);
        builder.setTitle(titlesString);
        builder.setMessage(messageString);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();


    } // myDialog
} // Main Class
