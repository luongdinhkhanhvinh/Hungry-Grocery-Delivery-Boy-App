package com.cscodetech.freshfastfooddeliveryboy.fregment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crashlytics.android.Crashlytics;
import com.cscodetech.freshfastfooddeliveryboy.R;
import com.cscodetech.freshfastfooddeliveryboy.activity.OrderPendingDetailsActivity;
import com.cscodetech.freshfastfooddeliveryboy.model.PendingOrder;
import com.cscodetech.freshfastfooddeliveryboy.model.PendingOrderItem;
import com.cscodetech.freshfastfooddeliveryboy.model.User;
import com.cscodetech.freshfastfooddeliveryboy.retrofit.APIClient;
import com.cscodetech.freshfastfooddeliveryboy.retrofit.GetResult;
import com.cscodetech.freshfastfooddeliveryboy.utils.CustPrograssbar;
import com.cscodetech.freshfastfooddeliveryboy.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kofigyan.stateprogressbar.StateProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

import static com.cscodetech.freshfastfooddeliveryboy.utils.SessionManager.CURRUNCY;


public class PendingFragment extends Fragment implements GetResult.MyListener, OrderPendingDetailsActivity.PenddingFragment {
    @BindView(R.id.txt_itmecount)
    TextView txtItmecount;
    @BindView(R.id.recycle_pending)
    RecyclerView recyclePending;

    // TODO: Rename and change types of parameters
    CustPrograssbar custPrograssbar;
    SessionManager sessionManager;
    User user;
    @BindView(R.id.txtNodata)
    TextView txtNodata;
    List<PendingOrderItem> pendinglistMain = new ArrayList<>();
    PendingAdepter myOrderAdepter;

    public PendingFragment() {
        // Required empty public constructor
    }

    public static PendingFragment newInstance(String param1, String param2) {
        PendingFragment fragment = new PendingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pendding, container, false);
        ButterKnife.bind(this, view);
        OrderPendingDetailsActivity.listener = this;
        custPrograssbar = new CustPrograssbar();
        sessionManager = new SessionManager(getActivity());
        user = sessionManager.getUserDetails("");
        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(getActivity());
        recyclePending.setLayoutManager(recyclerLayoutManager);
        Crashlytics.getInstance().setDebugMode(true);
        getPendingOrder();
        return view;
    }
    private void getPendingOrder() {
        custPrograssbar.PrograssCreate(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rid", user.getId());
            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getOlist((JsonObject) jsonParser.parse(jsonObject.toString()));
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
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                PendingOrder pendingOrder = gson.fromJson(result.toString(), PendingOrder.class);
                if (pendingOrder.getResult().equalsIgnoreCase("true")) {
                    txtItmecount.setText(pendingOrder.getOrderData().size() + " Orders");
                    if (pendingOrder.getOrderData().size() == 0) {
                        txtNodata.setVisibility(View.VISIBLE);
                        recyclePending.setVisibility(View.GONE);
                    } else {
                        pendinglistMain = pendingOrder.getOrderData();
                        myOrderAdepter = new PendingAdepter(pendinglistMain);
                        recyclePending.setAdapter(myOrderAdepter);
                    }
                } else {
                    txtNodata.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    @Override
    public void onClickItem(String s, PendingOrderItem orderItem) {

        for (int i = 0; i < pendinglistMain.size(); i++) {
            if (pendinglistMain.get(i).getOrderid().equalsIgnoreCase(orderItem.getOrderid())) {
                if (s.equalsIgnoreCase("reject")) {
                    pendinglistMain.remove(i);
                    myOrderAdepter.notifyDataSetChanged();
                } else {
                    orderItem.setStatus("processing");
                    pendinglistMain.set(i, orderItem);
                    myOrderAdepter.notifyDataSetChanged();
                }
                break;  // uncomment to get the first instance
            }
        }
    }
    public class PendingAdepter extends RecyclerView.Adapter<PendingAdepter.ViewHolder> {
        private List<PendingOrderItem> pendinglist;

        public PendingAdepter(List<PendingOrderItem> pendinglist) {
            this.pendinglist = pendinglist;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pending_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder,
                                     int position) {
            Log.e("position", "" + position);
            String[] descriptionData = {"Pending", "Processing", "Confirm"};

            PendingOrderItem order = pendinglist.get(position);
            holder.txtOderid.setText("OrderID #" + order.getOrderid());
            holder.txtDateandstatus.setText(order.getStatus() + " on " + order.getOdate());
            holder.txtType.setText("" + order.getPMethod());
            holder.txtPricetotla.setText(sessionManager.getStringData(CURRUNCY) + " " + order.getTotal());

            if(order.getStatus().equalsIgnoreCase("Pending")){
                holder.timeView.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
            }else if(order.getStatus().equalsIgnoreCase("")){
                holder.timeView.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
            }

            holder.timeView.checkStateCompleted(true);

            holder.timeView.setStateDescriptionData(descriptionData);
            holder.lvlClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), OrderPendingDetailsActivity.class).putExtra("MyClass", order).putParcelableArrayListExtra("MyList", order.getProductinfo()));
                }
            });
        }

        @Override
        public int getItemCount() {
            return pendinglist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.txt_oderid)
            TextView txtOderid;
            @BindView(R.id.txt_pricetotla)
            TextView txtPricetotla;
            @BindView(R.id.txt_dateandstatus)
            TextView txtDateandstatus;
            @BindView(R.id.txt_type)
            TextView txtType;

            @BindView(R.id.lvl_click)
            LinearLayout lvlClick;
            @BindView(R.id.img_right)
            ImageView imgRight;
            @BindView(R.id.time_view)
            StateProgressBar timeView;

            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
