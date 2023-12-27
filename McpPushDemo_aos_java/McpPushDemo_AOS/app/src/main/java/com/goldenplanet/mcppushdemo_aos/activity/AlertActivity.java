package com.goldenplanet.mcppushdemo_aos.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.evergage.android.Evergage;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Kim Namhoon on 12/22/23.
 */

public class AlertActivity extends AppCompatActivity {
    private AlertDialog currentDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVisible(false);
        handleAlertIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleAlertIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void handleAlertIntent(Intent intent) {
        overridePendingTransition(0, 0);
        Parcelable parcelable = intent != null ? intent.getParcelableExtra("remoteMessage") : null;
        final RemoteMessage remoteMessage = (parcelable instanceof RemoteMessage) ? (RemoteMessage) parcelable : null;
        if (remoteMessage != null && remoteMessage.getNotification() != null) {
            // New dialog build
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle(remoteMessage.getNotification().getTitle())
                    .setMessage(remoteMessage.getNotification().getBody())
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            // *** TRACKING THE CLICK: ***
                            com.evergage.android.Context globalContext = Evergage.getInstance().getGlobalContext();
                            if (globalContext != null) {
                                globalContext.trackClickthrough(remoteMessage.getData());
                            }

                            exitIfNoContent(dialogInterface);
                        }
                    });


            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    exitIfNoContent(dialogInterface);
                }
            });


            // Remove previous dialog
            AlertDialog previousDialog = currentDialog;
            currentDialog = null;
            if (previousDialog != null) {
                previousDialog.hide();
            }

            // Show new dialog
            currentDialog = builder.create();
            currentDialog.show();
        }

        exitIfNoContent(null);
    }

    private void exitIfNoContent(@Nullable DialogInterface dialogBeingDismissed) {
        if (currentDialog != null && currentDialog != dialogBeingDismissed) {
            return;
        }

        currentDialog = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask();
        } else {
            finish();
        }
        overridePendingTransition(0, 0);
    }
}
