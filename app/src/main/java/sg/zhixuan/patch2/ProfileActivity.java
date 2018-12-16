package sg.zhixuan.patch2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    ImageView ivProfile;
    Button btnNext;
    final int GALLERY = 1;
    final int CAMERA = 2;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri uri;
    TextView signuptext4, txtprofilepicprompt, profilepic;

    private static final String TAG = "ProfileActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepic);

        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        btnNext = (Button) findViewById(R.id.btnNext);
        signuptext4 = (TextView)findViewById(R.id.signuptext4);
        txtprofilepicprompt = (TextView)findViewById(R.id.txtprofilepicprompt);
        profilepic = (TextView)findViewById(R.id.profilepic);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        setChineseLanguage();

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) { //permission is allowed
                    showPictureDialog();
                } else if (!checkPermission()){ //permission is revoked by user
                    String message = "";
                    if(MainActivity.language.equals("Chinese")) {
                        message = "请准许使用相机和存储的权限";
                    } else if (MainActivity.language.equals("English")) {
                        message = "Please allow access to camera and storage to add profile picture.";
                    }

                    String back = "";
                    if(MainActivity.language.equals("Chinese")) {
                        back = "关闭";
                    } else if (MainActivity.language.equals("English")) {
                        back = "Back";
                    }
                    new AlertDialog.Builder(view.getContext())
                            .setMessage(message)
                            .setNeutralButton(back,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
    }

    private void uploadImage() {
        if (uri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            String uploadmsg = "Uploading...";
            if (MainActivity.language.equals("Chinese")) {
                uploadmsg = "上传中。。。";
            }
            progressDialog.setTitle(uploadmsg);
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/" + MainAccountActivity.user.getUid());
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            ref.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            MainAccountActivity.user.setProfilePic(uri.toString());
                                            Log.d(TAG, "onSuccess: " + MainAccountActivity.user.getProfilePic());
                                            VerificationActivity.mDatabase.child("users").child(MainAccountActivity.user.getUid()).child("profilePic").setValue(MainAccountActivity.user.getProfilePic())
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d(TAG, "onSuccess: success add pic");
                                                            startActivity(new Intent(ProfileActivity.this, HobbyActivity.class));
                                                        }
                                                    });
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Log.d(TAG, "onFailure: failure" + e.getMessage());
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = null;

            if (requestCode == GALLERY) {
                try {
                    uri = data.getData();
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == CAMERA) {
                bitmap = (Bitmap) data.getExtras().get("data");
            }

            ivProfile.setImageBitmap(bitmap);
        }

    }

    //dialog to prompt user to choose method of getting image
    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        String title = "Select Action";
        String item1 = "Select from gallery";
        String item2 = "Capture from camera";
        if (MainActivity.language.equals("Chinese")) {
            title = "选项";
            item1 = "从照片库选择头像";
            item2 = "从相机捕捉头像";
        }
        pictureDialog.setTitle(title);

        String[] pictureDialogItems = {
                item1,
                item2
        };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                chooseFromGallery();
                                break;
                            case 1:
                                takeFromCamera();
                        }
                    }
                });
        pictureDialog.show();
    }

    //open gallery
    private void chooseFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY);
    }

    //open camera
    private void takeFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    //check if app has permission to access camera and phone storage
    private boolean checkPermission() {
        int result;
        String[] permissions = new String[]{
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    private void setChineseLanguage() {
        if (MainActivity.language.equals("Chinese")) {
            txtprofilepicprompt.setText("按图标即可选择想上传的头像");
            signuptext4.setText("注册");
            profilepic.setText("头像");
            btnNext.setText("下一步");
        }
    }
}
