package com.cscodetech.freshfastfooddeliveryboy.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cscodetech.freshfastfooddeliveryboy.R;
import com.cscodetech.freshfastfooddeliveryboy.model.PendingOrderItem;
import com.cscodetech.freshfastfooddeliveryboy.model.Productinfo;
import com.cscodetech.freshfastfooddeliveryboy.model.User;
import com.cscodetech.freshfastfooddeliveryboy.utils.CustPrograssbar;
import com.cscodetech.freshfastfooddeliveryboy.utils.SessionManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cscodetech.freshfastfooddeliveryboy.retrofit.APIClient.Base_URL;
import static com.cscodetech.freshfastfooddeliveryboy.utils.SessionManager.CURRUNCY;

public class OrderDeliverDetailsActivity extends AppCompatActivity {

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

    @BindView(R.id.img_sing)
    ImageView img_sing;


    ArrayList<Productinfo> productinfoArrayList;
    PendingOrderItem order;
    SessionManager sessionManager;

    CustPrograssbar custPrograssbar;
    User user;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_deliver_details);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order Delivered Details");
        getSupportActionBar().setElevation(0f);
        sessionManager = new SessionManager(this);
        user = sessionManager.getUserDetails("");
        custPrograssbar = new CustPrograssbar();
        order = (PendingOrderItem) getIntent().getParcelableExtra("MyClass");
        productinfoArrayList = getIntent().getParcelableArrayListExtra("MyList");

        txtOrderid.setText("" + order.getOrderid());
        txtTotal.setText(sessionManager.getStringData(CURRUNCY) + " " + order.getTotal());
        txtTime.setText("" + order.getTimesloat());
        txtOrderid.setText("" + order.getOrderid());
        txtQty.setText("" + productinfoArrayList.size());
        txtAddress.setText("" + order.getDelivery());
        txtEmail.setText("" + order.getEmail());
        txtPaymode.setText("" + order.getPMethod());
        txtName.setText("" + order.getName());
        byte[] decodedString;

        if (order.getSign() != null){
            decodedString = Base64.decode(order.getSign(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            img_sing.setImageBitmap(decodedByte);
        }

        setJoinPlayrList(lvlItems, productinfoArrayList);
    }

    private void setJoinPlayrList(LinearLayout lnrView, ArrayList<Productinfo> productinfoArrayList) {

        lnrView.removeAllViews();

        for (int i = 0; i < productinfoArrayList.size(); i++) {
            Productinfo listdatum = productinfoArrayList.get(i);
            LayoutInflater inflater = LayoutInflater.from(OrderDeliverDetailsActivity.this);

            View view = inflater.inflate(R.layout.pending_order_item, null);
            ImageView imgView = view.findViewById(R.id.imageView);
            TextView txtTitle = view.findViewById(R.id.txt_title);
            TextView txtItems = view.findViewById(R.id.txt_items);
            TextView txt_price = view.findViewById(R.id.txt_price);
            TextView txt_pricedis = view.findViewById(R.id.txt_pricedis);
            Glide.with(OrderDeliverDetailsActivity.this).load(Base_URL + listdatum.getProductImage()).placeholder(R.drawable.slider).into(imgView);
            txtTitle.setText("" + listdatum.getProductName());
            txtItems.setText(" X " + listdatum.getProductQty() + " Items");
            txt_price.setText(sessionManager.getStringData(CURRUNCY) + listdatum.getProductPrice() + "");
            txt_pricedis.setPaintFlags(txt_pricedis.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            txt_pricedis.setText(sessionManager.getStringData(CURRUNCY) + "" + listdatum.getDiscount());
            lnrView.addView(view);
        }
    }

}
