package com.goldenplanet.mcppushdemo_aos.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.goldenplanet.mcppushdemo_aos.R;
import com.goldenplanet.mcppushdemo_aos.databinding.ActivityMainBinding;
import com.goldenplanet.mcppushdemo_aos.util.CommonUtil;
import com.goldenplanet.mcppushdemo_aos.util.SharedPrefHelper;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private EditText etUserId, etAccount, etDataSet;
    private Button btnSave;
    private TextView tvVersion, tvDensity, tvPPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        setSupportActionBar(binding.toolbar);

        etUserId = findViewById(R.id.et_userId);
        etAccount = findViewById(R.id.et_account);
        etDataSet = findViewById(R.id.et_dataset);

        btnSave = findViewById(R.id.bt_save);

        String userId = SharedPrefHelper.getUserId();
        String account = SharedPrefHelper.getAccount();
        String dataset = SharedPrefHelper.getDataSet();

        etUserId.setText(userId);
        etAccount.setText(account);
        etDataSet.setText(dataset);

        btnSave.setOnClickListener(view -> {
            SharedPrefHelper.setUserId(etUserId.getText().toString());
            SharedPrefHelper.setAccount(etAccount.getText().toString());
            SharedPrefHelper.setDataSet(etDataSet.getText().toString());

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("안내").setMessage("앱이 종료됩니다. 앱을 재실행하면 변경설정이 반영됩니다.");
            builder.setPositiveButton("확인", (dialog, id) -> {
                finish();
                System.exit(0);
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        tvVersion = findViewById(R.id.tv_version);
        tvDensity = findViewById(R.id.tv_density);
        tvPPI = findViewById(R.id.tv_ppi);

        String swVerName = CommonUtil.getAppVersionName(this);
        String swVerCode = String.valueOf(CommonUtil.getAppVersionCode(this));
        tvVersion.setText("Version : "+swVerCode+"/"+swVerName);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int densityDpi = (int)(metrics.density * 160f);
        tvDensity.setText("Density : "+densityDpi);

        float ppi = CommonUtil.getDevicePPI(this);
        tvPPI.setText("PPI : "+ ppi);
    }
}