package com.hooke.needhelp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by hooke on 30.11.17.
 */

public class RegionAdapter extends RecyclerView.Adapter<RegionAdapter.ViewHolder> {
    private String[] mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView regionTextView;
        public CardView cardView;

        public ViewHolder(View v) {
            super(v);
            regionTextView = (TextView) itemView.findViewById(R.id.region);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }


    public RegionAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public RegionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_rw, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.regionTextView.setText(mDataset[position].substring(0, mDataset[position].indexOf("+") - 1));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] phones = mDataset[position].substring(mDataset[position].indexOf("+")).split(",");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.getContext());
                builder.setTitle(R.string.call_title)
                        .setItems(phones, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phones[which]));
                                if (ActivityCompat.checkSelfPermission(MainActivity.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    Toast.makeText(MainActivity.getContext(), R.string.need_perm, Toast.LENGTH_SHORT).show();

                                } else {
                                    MainActivity.getContext().startActivity(intent);
                                }
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
