package kku.charoenrat.kittikan.easykku;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;

public class SingUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, phoneEditText, userEditText , passwordEditText;
    private ImageView imageView;
    private Button button;
    private String nameString, phoneString, userString, passwordString, imagePathString, imageNameString ;
    private Uri uri;
    private boolean aBoolean =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        //Bind Widget
        nameEditText = (EditText) findViewById(R.id.editText4);
        phoneEditText = (EditText) findViewById(R.id.editText3);
        userEditText = (EditText) findViewById(R.id.editText2);
        passwordEditText = (EditText) findViewById(R.id.editText);
        imageView = (ImageView) findViewById(R.id.imageView2);
        button = (Button) findViewById(R.id.button3);


        //SignUp Controller
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameString = nameEditText.getText().toString().trim();
                phoneString = phoneEditText.getText().toString().trim();
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //Check Space
                if (nameString.equals("") || phoneString.equals("") || userString.equals("") || passwordString.equals("")) {
                    Log.d("12novV1","Have Space");
                    MyAlert myAlert = new MyAlert(SingUpActivity.this, R.drawable.doremon48,"มีช่องว่าง","กรุณากรอกให้จบ");
                    myAlert.MyDialog();
                } else if (aBoolean) {
                    MyAlert myAlert = new MyAlert(SingUpActivity.this, R.drawable.doremon48,"ยังไม่เลือกรูป","กรุณาเลือกรูป");
                    myAlert.MyDialog();
                } else {
                    uploadImageToServer();
                }
            }
        });

        //image view
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"โปรดเลือกแอฟดูภาพ"), 0);

            } //onClick
        });
    } //Main Method

    private void uploadImageToServer() {
        //change policy
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        try{

            SimpleFTP simpleFTP = new SimpleFTP();
            simpleFTP.connect("ftp.swiftcodingthai.com",21,"kku@swiftcodingthai.com","Abc12345");
            simpleFTP.bin();
            simpleFTP.cwd("Image");
            simpleFTP.stor(new File(imagePathString));
            simpleFTP.disconnect();

        }catch (Exception e){
            Log.d("12noVV1","e simpleFTP ==> " + e.toString());
        }

    }//uplode

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 0)&&(resultCode == RESULT_OK)) {
            Log.d("12novV1","Result OK");
            aBoolean = false;

            uri = data.getData();
            try {

                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);

            }catch (Exception e){
                e.printStackTrace();
            }

            //Find Path
            imagePathString = myFindPath(uri);
            Log.d("12novV1","imagePath ==> " + imagePathString);

            //find name image
            imageNameString = imagePathString.substring(imagePathString.lastIndexOf("/"));
            Log.d("12novV1","imageName ==> "+ imageNameString);
        }

    } //onActivityResult

    private String myFindPath(Uri uri) {

        String result = null;
        String[] string = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri,string,null,null,null);

        if (cursor != null){
            cursor.moveToFirst();
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            result=cursor.getString(index);
        }else{
            result=uri.getPath();
        }

        return result;
    }
}//Main Class
