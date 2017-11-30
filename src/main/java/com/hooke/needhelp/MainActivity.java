package com.hooke.needhelp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String[] myDataset;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.item_rw);
        context = this;

        getPermision();
        showInfo();

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        myDataset = getResources().getStringArray(R.array.regions);

        mAdapter = new RegionAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void showInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.getContext());
        builder.setTitle(R.string.info)
                .setMessage(R.string.info_text)
                .setPositiveButton(R.string.okay_main, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                     }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void getPermision() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, R.string.no_perm, Toast.LENGTH_SHORT).show();

            }
        };


        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setRationaleTitle(R.string.need_perm)
                .setRationaleMessage(R.string.just_give)
                .setDeniedTitle(R.string.perm_deny)
                .setDeniedMessage(
                        R.string.okay_no_perm)
                .setGotoSettingButtonText(R.string.manual_perm)
                .setPermissions(Manifest.permission.CALL_PHONE)
                .check();

    }

    public static Context getContext(){
        return context;
    }

}
