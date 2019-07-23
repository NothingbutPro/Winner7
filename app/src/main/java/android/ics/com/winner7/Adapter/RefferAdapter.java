package android.ics.com.winner7.Adapter;

import android.content.Context;
import android.ics.com.winner7.Model.PriceModel;
import android.ics.com.winner7.Model.RefferModel;
import android.ics.com.winner7.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RefferAdapter extends RecyclerView.Adapter<RefferAdapter.ViewHolder> {

    private static final String TAG = "RefferAdapter";
    private ArrayList<RefferModel> RefList;
    public Context context;
    String resId = "";
    String finalStatus = "";
    String Image;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView refName,refEmail;
        //  LinearLayout card;
        ImageView idProductImage;
        LinearLayout mainButton;
        int pos;

        public ViewHolder(View view) {
            super(view);

            refName = (TextView) view.findViewById(R.id.refName);
            refEmail = (TextView) view.findViewById(R.id.refEmail);
        }
    }

    public static Context mContext;

    public RefferAdapter(Context mContext, ArrayList<RefferModel> reff_list) {
        context = mContext;
        RefList = reff_list;

    }

    @Override
    public RefferAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reffer_row, parent, false);

        return new RefferAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RefferAdapter.ViewHolder viewHolder, final int position) {
        RefferModel refferModel = RefList.get(position);
        viewHolder.refName.setText(refferModel.getName());
        viewHolder.refEmail.setText(refferModel.getEmail());

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
        return RefList.size();
    }
}
