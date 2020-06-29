package com.cscodetech.freshfastfooddeliveryboy.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.cscodetech.freshfastfooddeliveryboy.R;
import com.cscodetech.freshfastfooddeliveryboy.model.PendingOrderItem;
import com.cscodetech.freshfastfooddeliveryboy.model.Productinfo;
import com.cscodetech.freshfastfooddeliveryboy.model.RestResponse;
import com.cscodetech.freshfastfooddeliveryboy.model.User;
import com.cscodetech.freshfastfooddeliveryboy.retrofit.APIClient;
import com.cscodetech.freshfastfooddeliveryboy.retrofit.GetResult;
import com.cscodetech.freshfastfooddeliveryboy.utils.CustPrograssbar;
import com.cscodetech.freshfastfooddeliveryboy.utils.GeoPoint;
import com.cscodetech.freshfastfooddeliveryboy.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.util.RFABTextUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import static com.cscodetech.freshfastfooddeliveryboy.retrofit.APIClient.Base_URL;
import static com.cscodetech.freshfastfooddeliveryboy.utils.SessionManager.CURRUNCY;

public class OrderPendingDetailsActivity extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener, GetResult.MyListener {

    @BindView(R.id.txt_orderid)
    TextView txtOrderid;
    @BindView(R.id.txt_total)
    TextView txtTotal;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.txt_qty)
    TextView txtQty;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.txt_email)
    TextView txtEmail;
    @BindView(R.id.txt_paymode)
    TextView txtPaymode;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.lvl_items)
    LinearLayout lvlItems;


    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.activity_main_rfab)
    RapidFloatingActionButton rfaButton;
    @BindView(R.id.activity_main_rfal)
    RapidFloatingActionLayout rfaLayout;
    @BindView(R.id.txt_deliverd)
    TextView txtDeliverd;
    @BindView(R.id.lvl_dilevry)
    LinearLayout lvlDilevry;
    @BindView(R.id.txt_accept)
    TextView txtAccept;
    @BindView(R.id.txt_reject)
    TextView txtReject;
    @BindView(R.id.lvl_accept_reject)
    LinearLayout lvlAcceptReject;


    private RapidFloatingActionHelper rfabHelper;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();
                return true;
            case R.id.navigation_call:
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + order.getMobile()));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);

                    return true;
                }
                startActivity(intent);
                return true;
            case R.id.navigation_map:
                Log.e("Map", "-->");
                getLocationFromAddress(order.getDelivery());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    ArrayList<Productinfo> productinfoArrayList;
    PendingOrderItem order;
    SessionManager sessionManager;

    CustPrograssbar custPrograssbar;
    User user;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order Details");
        getSupportActionBar().setElevation(0f);
        requestPermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        sessionManager = new SessionManager(this);
        user = sessionManager.getUserDetails("");
        custPrograssbar = new CustPrograssbar();
        order = (PendingOrderItem) getIntent().getParcelableExtra("MyClass");
        productinfoArrayList = getIntent().getParcelableArrayListExtra("MyList");
        setJoinPlayrList(lvlItems, productinfoArrayList);
        txtOrderid.setText("" + order.getOrderid());
        txtTotal.setText(sessionManager.getStringData(CURRUNCY) + " " + order.getTotal());
        txtTime.setText("" + order.getTimesloat());
        txtOrderid.setText("" + order.getOrderid());
        txtQty.setText("" + productinfoArrayList.size());
        txtAddress.setText("" + order.getDelivery());
        txtEmail.setText("" + order.getEmail());
        txtPaymode.setText("" + order.getPMethod());
        txtName.setText("" + order.getName());
        txtStatus.setText("" + order.getStatus());
        if (order.getStatus().equalsIgnoreCase("processing")) {
            lvlAcceptReject.setVisibility(View.GONE);
            rfaLayout.setVisibility(View.VISIBLE);
            lvlDilevry.setVisibility(View.VISIBLE);
            txtStatus.setBackground(getResources().getDrawable(R.drawable.pending_round_yell));
        }
        setFloting();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setFloting() {

        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(this);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Issue with an ongoing order")
                .setResId(R.drawable.ic_clear_white)
                .setIconNormalColor(Color.parseColor("#C81507"))
                .setIconPressedColor(R.color.colorred)
                .setLabelColor(Color.WHITE)
                .setLabelBackgroundDrawable(getDrawable(R.drawable.button_round))
                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Payment issue with my order")
                .setResId(R.drawable.ic_clear_white)
                .setIconNormalColor(Color.parseColor("#C81507"))
                .setIconPressedColor(R.color.colorred)
                .setLabelColor(Color.WHITE)
                .setLabelBackgroundDrawable(getDrawable(R.drawable.button_round))
                .setWrapper(1)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Address wrong ")
                .setResId(R.drawable.ic_clear_white)
                .setIconNormalColor(Color.parseColor("#C81507"))
                .setIconPressedColor(R.color.colorred)
                .setLabelColor(Color.WHITE)
                .setLabelBackgroundDrawable(getDrawable(R.drawable.button_round))
                .setWrapper(2)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("Other")
                .setResId(R.drawable.ic_clear_white)
                .setIconNormalColor(Color.parseColor("#C81507"))
                .setIconPressedColor(R.color.colorred)
                .setLabelColor(Color.WHITE)
                .setLabelBackgroundDrawable(getDrawable(R.drawable.button_round))
                .setWrapper(3)
        );
        rfaContent
                .setItems(items)
                .setIconShadowRadius(RFABTextUtil.dip2px(this, 2))
                .setIconShadowColor(R.color.colorred)
                .setIconShadowDy(RFABTextUtil.dip2px(this, 1))
        ;

        rfabHelper = new RapidFloatingActionHelper(
                this,
                rfaLayout,
                rfaButton,
                rfaContent
        ).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order, menu);
        return true;
    }

    private void setJoinPlayrList(LinearLayout lnrView, ArrayList<Productinfo> productinfoArrayList) {

        lnrView.removeAllViews();

        for (int i = 0; i < productinfoArrayList.size(); i++) {
            Productinfo listdatum = productinfoArrayList.get(i);
            LayoutInflater inflater = LayoutInflater.from(OrderPendingDetailsActivity.this);

            View view = inflater.inflate(R.layout.pending_order_item, null);
            ImageView imgView = view.findViewById(R.id.imageView);
            TextView txtTitle = view.findViewById(R.id.txt_title);
            TextView txtItems = view.findViewById(R.id.txt_items);
            TextView txt_price = view.findViewById(R.id.txt_price);
            TextView txt_pricedis = view.findViewById(R.id.txt_pricedis);
            Glide.with(OrderPendingDetailsActivity.this).load(Base_URL + listdatum.getProductImage()).placeholder(R.drawable.slider).into(imgView);
            txtTitle.setText("" + listdatum.getProductName());
            txtItems.setText(" X " + listdatum.getProductQty() + " Items");
            txt_price.setText(sessionManager.getStringData(CURRUNCY) + listdatum.getProductPrice() + "");
            txt_pricedis.setPaintFlags(txt_pricedis.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            txt_pricedis.setText(sessionManager.getStringData(CURRUNCY) + "" + listdatum.getDiscount());
            lnrView.addView(view);
        }
    }


    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {


        OrderCencel(item.getLabel());


        rfabHelper.toggleContent();
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        OrderCencel(item.getLabel());
        rfabHelper.toggleContent();
    }

    public boolean getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return false;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();
            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", location.getLatitude(), location.getLongitude());
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @OnClick({R.id.txt_deliverd, R.id.txt_accept, R.id.txt_reject})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_deliverd:
                startActivity(new Intent(OrderPendingDetailsActivity.this, SignatureActivity.class).putExtra("oid", order.getOrderid()).putExtra("rid", user.getId()));
                break;
            case R.id.txt_accept:
                OrderStatus("accept");
                break;
            case R.id.txt_reject:
                OrderStatus("reject");
                break;
            default:
                break;
        }
    }

    private void OrderStatus(String status) {
        custPrograssbar.PrograssCreate(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rid", user.getId());
            jsonObject.put("oid", order.getOrderid());
            jsonObject.put("status", status);
            JsonParser jsonParser = new JsonParser();

            Call<JsonObject> call = APIClient.getInterface().getOstatus((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void OrderCencel(String comment) {
        custPrograssbar.PrograssCreate(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rid", user.getId());
            jsonObject.put("oid", order.getOrderid());
            jsonObject.put("status", "cancle");
            jsonObject.put("comment", comment);
            JsonParser jsonParser = new JsonParser();

            Call<JsonObject> call = APIClient.getInterface().getOstatus((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "2");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.ClosePrograssBar();
            order.setProductinfo(productinfoArrayList);
            if (callNo.equalsIgnoreCase("1")) {
                Gson gson = new Gson();
                RestResponse response = gson.fromJson(result, RestResponse.class);
                Toast.makeText(this, response.getResponseMsg(), Toast.LENGTH_SHORT).show();
                if (response.getResult().equalsIgnoreCase("true")) {

                    listener.onClickItem("processing", order);
                    txtStatus.setText("processing");
                    lvlAcceptReject.setVisibility(View.GONE);
                    rfaLayout.setVisibility(View.VISIBLE);
                    lvlDilevry.setVisibility(View.VISIBLE);
                    txtStatus.setBackground(getResources().getDrawable(R.drawable.pending_round_yell));
                } else {

                    listener.onClickItem("reject", order);
                    finish();

                }
            } else if (callNo.equalsIgnoreCase("2")) {
                Gson gson = new Gson();
                RestResponse response = gson.fromJson(result, RestResponse.class);
                Toast.makeText(this, response.getResponseMsg(), Toast.LENGTH_SHORT).show();
                if (response.getResult().equalsIgnoreCase("true")) {
                    listener.onClickItem("reject", order);
                    finish();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PenddingFragment listener;

    public interface PenddingFragment {
        public void onClickItem(String s, PendingOrderItem orderItem);

    }
}
