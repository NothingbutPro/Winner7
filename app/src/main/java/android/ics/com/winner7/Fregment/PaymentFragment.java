package android.ics.com.winner7.Fregment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.ics.com.winner7.R;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class PaymentFragment extends Fragment {
    // Array of strings...
    ListView listVw;
    String countryList[] = {"12-07-2019", "14-07-2019", "16-07-2019", "18-07-2019", "20-07-2019", "22-07-2019"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        listVw = (ListView)view.findViewById(R.id.listVw);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.activity_listview, R.id.textView, countryList);
        listVw.setAdapter(arrayAdapter);
        super.onViewCreated(view, savedInstanceState);

    }
}
