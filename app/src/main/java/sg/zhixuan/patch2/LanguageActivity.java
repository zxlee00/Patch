package sg.zhixuan.patch2;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LanguageActivity extends AppCompatActivity {

    Button btnEnglish, btnChinese;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        btnEnglish = (Button)findViewById(R.id.btnEnglish);
        btnChinese = (Button)findViewById(R.id.btnChinese);

        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(LanguageActivity.this);
                alertDialog.setTitle("English")
                        .setMessage("Confirm to select English?")
                        .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //MainActivity.language = "English";
                                Log.d("ZZZ", "LANGUAGE SELECTED: " + MainActivity.language);
                                updateLanguage("English");
                                closeAppAlertDialog("English");
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = alertDialog.create();
                alert.setCancelable(false);
                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }
        });

        btnChinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(LanguageActivity.this);
                alertDialog.setTitle("中文")
                        .setMessage("确定选择中文吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //MainActivity.language = "Chinese";
                                Log.d("ZZZ", "LANGUAGE SELECTED: " + MainActivity.language);
                                updateLanguage("Chinese");
                                closeAppAlertDialog("Chinese");
                            }
                        })
                        .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = alertDialog.create();
                alert.setCancelable(false);
                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }
        });
    }

    private void updateLanguage(String newlanguage) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language", newlanguage);
        editor.commit();
    }

    private void closeAppAlertDialog(String alertdialoglanguage) {
        AlertDialog.Builder closeAppDialog = new AlertDialog.Builder(LanguageActivity.this);
        if (alertdialoglanguage.equals("Chinese")) {
            closeAppDialog.setTitle("关闭")
                    .setMessage("想看到语言更改的话，请关闭 Patch 再重开。")
                    .setNeutralButton("知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            finish();
                        }
                    });
        } else if (alertdialoglanguage.equals("English")) {
            closeAppDialog.setTitle("Close Application")
                    .setMessage("If you want to see the changes to the language, please restart Patch.")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                finish();
                            }
                        });
        }

        AlertDialog closedialog = closeAppDialog.create();
        closedialog.setCancelable(false);
        closedialog.setCanceledOnTouchOutside(false);
        closedialog.show();
    }
}
