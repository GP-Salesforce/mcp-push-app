package com.goldenplanet.mcppushdemo_aos.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.evergage.android.Campaign;
import com.evergage.android.CampaignHandler;
import com.evergage.android.ClientConfiguration;
import com.evergage.android.Evergage;
import com.evergage.android.Screen;
import com.evergage.android.promote.Category;
import com.evergage.android.promote.Product;
import com.evergage.android.promote.Tag;
import com.goldenplanet.mcppushdemo_aos.R;
import com.goldenplanet.mcppushdemo_aos.databinding.ActivityMainBinding;
import com.goldenplanet.mcppushdemo_aos.util.CommonUtil;
import com.goldenplanet.mcppushdemo_aos.util.LogMsg;
import com.goldenplanet.mcppushdemo_aos.util.SharedPrefHelper;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private EditText etUserId, etAccount, etDataSet;
    private Button btnAllSave, btnIdSave;
    private TextView tvVersion, tvDensity, tvPPI;

    private Campaign activeCampaign;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        setSupportActionBar(binding.toolbar);

        etUserId = findViewById(R.id.et_userId);
        etAccount = findViewById(R.id.et_account);
        etDataSet = findViewById(R.id.et_dataset);

        btnAllSave = findViewById(R.id.bt_all_save);
        btnIdSave = findViewById(R.id.bt_id_save);

        String userId = SharedPrefHelper.getUserId();
        String account = SharedPrefHelper.getAccount();
        String dataset = SharedPrefHelper.getDataSet();

        etUserId.setText(userId);
        etAccount.setText(account);
        etDataSet.setText(dataset);

        btnAllSave.setOnClickListener(view -> {
            SharedPrefHelper.setUserId(etUserId.getText().toString());
            SharedPrefHelper.setAccount(etAccount.getText().toString());
            SharedPrefHelper.setDataSet(etDataSet.getText().toString());

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("안내").setMessage("UserId, Account, DataSet이 재설정됩니다");
            builder.setPositiveButton("확인", (dialog, id) -> {
                Evergage evergage = Evergage.getInstance();
                evergage.reset();


                evergage.setUserId(etUserId.getText().toString());
                evergage.start(new ClientConfiguration.Builder()
                        .account(etAccount.getText().toString())
                        .dataset(etDataSet.getText().toString())
                        .usePushNotifications(true)
                        .build());
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });


        btnIdSave.setOnClickListener(view -> {
            SharedPrefHelper.setUserId(etUserId.getText().toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("안내").setMessage("UserId가 재설정됩니다.");
            builder.setPositiveButton("확인", (dialog, id) -> {
                Evergage evergage = Evergage.getInstance();

                evergage.setUserId(etUserId.getText().toString());
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });


        tvVersion = findViewById(R.id.tv_version);
        tvDensity = findViewById(R.id.tv_density);
        tvPPI = findViewById(R.id.tv_ppi);

        String swVerName = CommonUtil.getAppVersionName(this);
        String swVerCode = String.valueOf(CommonUtil.getAppVersionCode(this));
        tvVersion.setText("Version : " + swVerCode + "/" + swVerName);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int densityDpi = (int) (metrics.density * 160f);
        tvDensity.setText("Density : " + densityDpi);

        float ppi = CommonUtil.getDevicePPI(this);
        tvPPI.setText("PPI : " + ppi);
    }


    @Override
    protected void onStart() {
        super.onStart();
        refreshScreen();

        final Screen screen = Evergage.getInstance().getScreenForActivity(this);

        if (screen != null) {
            CampaignHandler handler = campaign -> {
                String featuredProductName = campaign.getData().optString("featuredProductName");
                if (featuredProductName == null || featuredProductName.isEmpty()) {
                    return;
                }

                screen.trackImpression(campaign);

                if (!campaign.isControlGroup()) {
                    activeCampaign = campaign;
                    String msg = "New Active campaign name : " + campaign.getCampaignName() +
                            ", target : " + campaign.getTarget() + ", data : " + campaign.getData();

                    LogMsg.d(msg);
                    showDialog(msg);

                }

            };

            screen.setCampaignHandler(handler, "featuredProduct");
        }
    }


    private void refreshScreen() {
        // Evergage track screen view
        Screen screen = Evergage.getInstance().getScreenForActivity(this);
        if (screen != null) {
            // If screen is viewing a product:
            screen.viewItem(new Product("p123"));

            // If screen is viewing a category, like women's merchandise:
            screen.viewCategory(new Category("Womens"));

            // Or if screen is viewing a tag, like some specific brand:
            screen.viewTag(new Tag("SomeBrand", Tag.Type.Brand));

            // Or maybe screen isn't related to your catalog:
            screen.trackAction("User Profile");
        }


        // ... your content fetching/displaying, etc.
    }


    private void showDialog(String msg) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("캠페인 팝업")
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.dismiss();
                    }
                });


        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialog.dismiss();
            }
        });


        // Remove previous dialog
        AlertDialog previousDialog = dialog;
        dialog = null;
        if (previousDialog != null) {
            previousDialog.hide();
        }

        // Show new dialog
        dialog = builder.create();
        dialog.show();
    }
}