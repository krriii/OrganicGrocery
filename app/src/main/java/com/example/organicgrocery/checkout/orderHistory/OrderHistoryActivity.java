package com.example.organicgrocery.checkout.orderHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.organicgrocery.R;
import com.example.organicgrocery.api.response.Bag;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    RecyclerView orderDetailsRv;
    ImageView backIvo;
    List<Bag> data = new ArrayList<Bag>();
    historyDetailsAdapter detailsAdapter;
    Bag bag;

    public static String ORDER_DETAILS_KEY="odk";

    Bag orderHistoryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_order_history);
        orderDetailsRv=findViewById(R.id.orderdetailsRV);
        backIvo = findViewById(R.id.backIvO);

        backIvo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().getSerializableExtra(ORDER_DETAILS_KEY) == null)
            finish();

        bag=(Bag) getIntent().getSerializableExtra(ORDER_DETAILS_KEY);
        data.add(bag);
        setOrderDetailsRv(data);
    }

    private void setOrderDetailsRv(List<Bag> bagList){
        data=bagList;
        orderDetailsRv.setHasFixedSize(true);
        orderDetailsRv.setLayoutManager(new LinearLayoutManager(this));
        detailsAdapter=new historyDetailsAdapter(bagList,this);
        orderDetailsRv.setAdapter(detailsAdapter);
    }
}



