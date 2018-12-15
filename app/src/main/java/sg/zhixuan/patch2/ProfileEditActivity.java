package sg.zhixuan.patch2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileEditActivity extends AppCompatActivity {

    private static final String TAG = "ProfileEditActivity";
    GridView gvHobby;
    EditText etName;
    Spinner spnAge;
    String hobby, userHobby;
    TextView tvName, tvAge, tvHobbies, txtEditProfile;
    List<String> hobbies;
    String[] userHobbies;
    Button btnDone;
    ImageView ivProfile;
    final int GALLERY = 1;
    final int CAMERA = 2;
    Uri uri;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        gvHobby = (GridView) findViewById(R.id.gvHobby);
        btnDone = (Button) findViewById(R.id.btnDone);
        etName = (EditText) findViewById(R.id.etName);
        spnAge = (Spinner) findViewById(R.id.spnAge);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        tvName = (TextView)findViewById(R.id.tvName);
        tvAge = (TextView)findViewById(R.id.tvAge);
        tvHobbies = (TextView)findViewById(R.id.tvHobbies);
        txtEditProfile = (TextView)findViewById(R.id.txtEditProfile);

        setChineseLanguage();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        StorageReference imgRef = storage.getReferenceFromUrl(MainAccountActivity.user.getProfilePic());

        Glide.with(ProfileEditActivity.this /* context */)
                .load(MainAccountActivity.user.profilePic)
                .apply(new RequestOptions()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(ivProfile);

        etName.setText(MainAccountActivity.user.getName());

        List age = new ArrayList<Integer>();
        for (int i = 55; i <= 100; i++) {
            age.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> spnArrayAdapter = new ArrayAdapter<Integer>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, age);
        spnAge.setAdapter(spnArrayAdapter);
        Integer index = age.indexOf(Integer.toString(MainAccountActivity.user.age));
        spnAge.setSelection(index);

        hobbies = new ArrayList<>();
        hobby = "";
        userHobby = MainAccountActivity.user.getHobby();
        userHobbies = MainAccountActivity.user.getHobby().split(",");
        hobbies.addAll(Arrays.asList(userHobbies));

        String[] hobbyList = new String[] {
                "Exercise",
                "Watch Videos / Shows",
                "Reading",
                "Cooking",
                "Talking / Chatting"
        };

        final List<String> hobbiesList = new ArrayList<String>(Arrays.asList(hobbyList));

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProfileEditActivity.this, android.R.layout.simple_list_item_1, hobbiesList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                String mHobby = gvHobby.getItemAtPosition(position).toString();
                if (hobbies.contains(mHobby)) {
                    view.setBackgroundColor(Color.parseColor("#dfb992"));
                } else {
                    view.setBackgroundColor(Color.parseColor("#f0dfbb"));
                }
                return view;
            }
        };
        gvHobby.setAdapter(adapter);


        gvHobby.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                if (hobbies.isEmpty()) {
                    hobbies.add(selectedItem);
                    view.setBackgroundColor(Color.parseColor("#dfb992"));
                } else {
                    if (hobbies.contains(selectedItem)) {
                        hobbies.remove(selectedItem);
                        view.setBackgroundColor(Color.parseColor("#f0dfbb"));
                    } else {
                        hobbies.add(selectedItem);
                        view.setBackgroundColor(Color.parseColor("#dfb992"));
                    }
                }
            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) { //permission is allowed
                    showPictureDialog();
                } else if (!checkPermission()){ //permission is revoked by user
                    String message = "Please allow access to camera and storage to add profile picture.";
                    String neutralbtn = "Back";
                    if (MainActivity.language.equals("Chinese")) {
                        message = "请准许使用相机和存储的权限";
                        neutralbtn = "关闭";
                    }

                    new AlertDialog.Builder(v.getContext())
                            .setMessage(message)
                            .setNeutralButton(neutralbtn,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                }
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                if (!name.isEmpty() &&
                        name.matches("^[a-zA-Z\\s]*$")) {
                    MainAccountActivity.user.setName(name);
                } else {
                    String error = "Enter a valid name.";
                    if (MainActivity.language.equals("Chinese"))
                        error = "请您输入一个有效的姓名 （只接受英文字母）。";
                    etName.setError(error);
                    etName.requestFocus();
                    return;
                }

                Integer age = Integer.parseInt(spnAge.getSelectedItem().toString());
                MainAccountActivity.user.setAge(age);

                for (int i = 0; i < hobbies.size(); i++) {
                    if (hobby.isEmpty()) {
                        hobby += hobbies.get(i);
                    } else {
                        hobby += "," + hobbies.get(i);
                    }
                }
                MainAccountActivity.user.setHobby(hobby);

                uploadImage();

                FirebaseDatabase.getInstance().getReference()
                        .child("users")
                        .child(MainAccountActivity.user.getUid())
                        .setValue(MainAccountActivity.user);

                finish();
            }
        });
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

    private void uploadImage() {
        if (uri != null) {

            final StorageReference ref = storageReference.child("images/" + MainAccountActivity.user.getUid());
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            MainAccountActivity.user.setProfilePic(uri.toString());
                                            Log.d(TAG, "onSuccess: success");
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: failure " + e.getMessage());
                        }
                    });
        }
    }

    private void setChineseLanguage() {
        if (MainActivity.language.equals("Chinese")) {
            tvName.setText("姓名：");
            txtEditProfile.setText("编辑个人资料");
            tvAge.setText("年龄：");
            tvHobbies.setText("兴趣/爱好：");
            btnDone.setText("完成");
        }
    }
}
