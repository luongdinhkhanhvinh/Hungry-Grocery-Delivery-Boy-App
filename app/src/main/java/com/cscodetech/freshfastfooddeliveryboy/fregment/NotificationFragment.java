package com.cscodetech.freshfastfooddeliveryboy.fregment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cscodetech.freshfastfooddeliveryboy.R;
import com.cscodetech.freshfastfooddeliveryboy.model.Noti;
import com.cscodetech.freshfastfooddeliveryboy.model.NotiItem;
import com.cscodetech.freshfastfooddeliveryboy.model.User;
import com.cscodetech.freshfastfooddeliveryboy.retrofit.APIClient;
import com.cscodetech.freshfastfooddeliveryboy.retrofit.GetResult;
import com.cscodetech.freshfastfooddeliveryboy.utils.CustPrograssbar;
import com.cscodetech.freshfastfooddeliveryboy.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;


public class NotificationFragment extends Fragment implements GetResult.MyListener {

    @BindView(R.id.recycle_delivery)
    RecyclerView recycleDelivery;
    @BindView(R.id.txt_nodata)
    TextView txtNodata;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    CustPrograssbar custPrograssbar;
    SessionManager sessionManager;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(getActivity());
        recycleDelivery.setLayoutManager(recyclerLayoutManager);

        custPrograssbar = new CustPrograssbar();
        sessionManager = new SessionManager(getActivity());
        user = sessionManager.getUserDetails("");
        getNotification();
        return view;
    }

    private void getNotification() {
        custPrograssbar.PrograssCreate(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", user.getId());
            JsonParser jsonParser = new JsonParser();

            Call<JsonObject> call = APIClient.getInterface().getNoti((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {

        try {
            custPrograssbar.ClosePrograssBar();
            Gson gson = new Gson();
            Noti noti = gson.fromJson(result.toString(), Noti.class);
            if (noti.getResult().equalsIgnoreCase("true")) {
                if (noti.getData().size() == 0) {
                    txtNodata.setVisibility(View.VISIBLE);
                } else {
                    notificationAdepter myOrderAdepter = new notificationAdepter(noti.getData());
                    recycleDelivery.setAdapter(myOrderAdepter);
                }
            } else {
                txtNodata.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class notificationAdepter extends RecyclerView.Adapter<notificationAdepter.ViewHolder> {

        private List<NotiItem> pendinglist;

        public notificationAdepter(List<NotiItem> pendinglist) {
            this.pendinglist = pendinglist;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.notification_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder,
                                     int position) {
            Log.e("position", "" + position);
            NotiItem order = pendinglist.get(position);
            holder.txtTitel.setText("" + order.getMsg());
            holder.txtDate.setText(order.getDate());

        }

        @Override
        public int getItemCount() {
            return pendinglist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.txt_titel)
            TextView txtTitel;
            @BindView(R.id.txt_date)
            TextView txtDate;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
