package android.ics.com.winner7.Fregment;

import android.content.Context;
import android.content.Intent;
import android.ics.com.winner7.HomeActivity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.ics.com.winner7.R;
import android.widget.Button;
import android.widget.LinearLayout;


public class RequestFragment extends Fragment {
    Button reqPay;
    LinearLayout btok, fillPay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        reqPay = (Button) view.findViewById(R.id.reqPay);

        btok = (LinearLayout) view.findViewById(R.id.btok);
        fillPay = (LinearLayout) view.findViewById(R.id.fillPay);
        reqPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  reqPay.setVisibility(View.GONE);

                fillPay.setVisibility(View.VISIBLE);
                btok.setVisibility(View.VISIBLE);

            }
        });

     /*   btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });*/
        super.onViewCreated(view, savedInstanceState);

    }
}
