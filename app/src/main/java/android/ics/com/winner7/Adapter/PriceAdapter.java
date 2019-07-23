package android.ics.com.winner7.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.ics.com.winner7.Model.PriceModel;
import android.ics.com.winner7.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.ViewHolder> {

    private static final String TAG = "TopAdapter";
    private ArrayList<PriceModel> PriceList;
    public Context context;
    String resId = "";
    String finalStatus = "";
    String Image;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView  winPrice,amt;
        //  LinearLayout card;
        ImageView idProductImage;
        LinearLayout mainButton;
        int pos;

        public ViewHolder(View view) {
            super(view);

            winPrice = (TextView) view.findViewById(R.id.winPrice);
            amt = (TextView) view.findViewById(R.id.amt);
        }
    }

    public static Context mContext;

    public PriceAdapter(Context mContext, ArrayList<PriceModel> price_list) {
        context = mContext;
        PriceList = price_list;

    }

    @Override
    public PriceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.price_row, parent, false);

        return new PriceAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PriceAdapter.ViewHolder viewHolder, final int position) {
        PriceModel priceModel = PriceList.get(position);
        viewHolder.winPrice.setText(priceModel.getWinners());
        viewHolder.amt.setText(priceModel.getAmount());

        // viewHolder.card.setTag(viewHolder);

       /* viewHolder.mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           *//*     TopModel topModel = TopList.get(position);
                Intent intent = new Intent(context, NewActivity.class);
                intent.putExtra("TopModel", topModel);
                context.startActivity(intent);
                ((Activity)context).finish();*//*
            }
        });*/
        viewHolder.pos = position;

    }

    @Override
    public int getItemCount() {
        return PriceList.size();
    }
}
