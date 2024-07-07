package com.example.shop_thoi_trang_mobile.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop_thoi_trang_mobile.BuildConfig;
import com.example.shop_thoi_trang_mobile.OnCartItemChangeListener;
import com.example.shop_thoi_trang_mobile.R;
import com.example.shop_thoi_trang_mobile.adapter.CartAdapter;
import com.example.shop_thoi_trang_mobile.helper.CurrencyConverter;
import com.example.shop_thoi_trang_mobile.model.CartItem;
import com.example.shop_thoi_trang_mobile.model.CartItemObjRequest;
import com.example.shop_thoi_trang_mobile.model.CartManager;
import com.example.shop_thoi_trang_mobile.model.OrderRequest;
import com.example.shop_thoi_trang_mobile.model.OrderResponse;
import com.example.shop_thoi_trang_mobile.networking.OrderService;
import com.example.shop_thoi_trang_mobile.networking.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;


import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements OnCartItemChangeListener {
    private  String CLIENT_ID;
    private RecyclerView cartRecylerView;
    private CartAdapter cartAdapter;
    private CartManager cartManager;
    private LinearLayout ll_empty_cart;
    private TextView tv_delivery_address, subtotal, shipping_fee, total_price;
    private RadioGroup payment_method;
    private Button btn_place_order;
    private Double total_amount = 0.0;
    private PayPalConfiguration config;
    private OrderService orderService;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        System.setProperty("javax.net.debug", "ssl,handshake");
        // paypal config
        this.CLIENT_ID = BuildConfig.API_KEY;
        config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(CLIENT_ID).merchantName("Shop Young Fashion");

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        // Fetch view
        cartRecylerView = findViewById(R.id.rv_cart_items);
        cartManager = CartManager.getInstance();
        ll_empty_cart = findViewById(R.id.empty_cart);
        subtotal = findViewById(R.id.tv_subtotal);
        shipping_fee = findViewById(R.id.tv_delivery);
        total_price = findViewById(R.id.tv_total);
        tv_delivery_address = findViewById(R.id.tv_delivery_address);
        payment_method = findViewById(R.id.payment_method);
        btn_place_order = findViewById(R.id.btn_place_order);

        // fetch cartItems
        List<CartItem> cartItemList = cartManager.getCartItemList();


        cartAdapter = new CartAdapter(this, cartItemList, this);
        cartRecylerView.setAdapter(cartAdapter);
        cartRecylerView.setLayoutManager(new LinearLayoutManager(this));

        // display empty cart appearance
        updateUICart();

        // fetch userId
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0);

        // initialize order service
        orderService = RetrofitClient.getRetrofitInstance().create(OrderService.class);

        // Item touch helper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false; // We are not moving items up or down
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                confirmDelete(position);
            }

            @Override
            public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
                return 0.3f; // Set the swipe threshold to 30% of the item's width (adjust as needed)
            }
        });

        itemTouchHelper.attachToRecyclerView(cartRecylerView);

        // Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_cart);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;
                if (item.getItemId() == R.id.nav_home) {
                    // Chuyển sang activity Home (ví dụ)
                    intent = new Intent(CartActivity.this, HomeActivity.class);
                } else if (item.getItemId() == R.id.nav_cart) {
                    // Chuyển sang activity Category (ví dụ)
                    intent = new Intent(CartActivity.this, CartActivity.class);
                } else if (item.getItemId() == R.id.nav_noti) {
                    // Chuyển sang activity Cart (ví dụ)
                    intent = new Intent(CartActivity.this, HomeActivity.class);
                } else if (item.getItemId() == R.id.nav_profile) {
                    // Chuyển sang activity Profile (ví dụ)
                    intent = new Intent(CartActivity.this, UserProfileActivity.class);
                }
                if (intent != null) {
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        // hn
        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // place order
                if (cartItemList.isEmpty()) {
                    showDialog("Empty cart", "Your cart is empty");
                } else {
                    placeOrderAlertDialog();
                }
            }
        });
    }

    private void placeOrderAlertDialog() {
        if (payment_method.getCheckedRadioButtonId() == R.id.radio_paypal) {
            processPaypalPayment();
        } else if (payment_method.getCheckedRadioButtonId() == R.id.radio_cod) {
            processCODPayment();
        } else {
            // show dialog requires user to choose payment method
            showDialog("Payment method", "Please choose payment method");
        }
    }

    private void processPaypalPayment() {
        double usdAmount = CurrencyConverter.convertVndToUsd(total_amount);
        PayPalPayment payment = new PayPalPayment(new BigDecimal(
                String.valueOf(usdAmount)
        ), "USD", "Shop Young Fashion - Payment",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, 0);
    }

    private void processCODPayment() {
        ArrayList<CartItemObjRequest> cartItemObjRequests = this.convertCartItemObject(cartManager.getCartItemList());
        OrderRequest orderRequest = new OrderRequest(userId, total_amount, "COD", cartItemObjRequests);
        fetchOrder(orderRequest);
        showDialog("Order success", "Your order has been placed successfully");
    }

    // handle paypal payment result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    String paymentDetails = confirm.toJSONObject().toString(4);
                    if (paymentDetails != null) {
                        handlePaypalResult(paymentDetails);
                    }
                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }

    private void showDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void confirmDelete(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    cartAdapter.removeItem(position);
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> {
                    cartAdapter.notifyItemChanged(position); // Refresh item view
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    private void handlePaypalResult(String paymentDetails) throws JSONException {
        JSONObject jsonDetails = new JSONObject(paymentDetails);
        String approved = jsonDetails.getJSONObject("response").getString("state");
        if (approved.equals("approved")) {
            // payment success on paypal method
            ArrayList<CartItemObjRequest> cartItemObjRequests = this.convertCartItemObject(cartManager.getCartItemList());
            OrderRequest orderRequest = new OrderRequest(userId, total_amount, "paypal", cartItemObjRequests);

            fetchOrder(orderRequest);
            showDialog("Payment success", "Your payment has been processed successfully");
        } else {
            // payment failed
            showDialog("Payment failed", "Your payment has been failed");
        }

    }

    private void updateTotalAmount() {
        // calculate total amount
        total_amount = 0.0;
        for (CartItem item :
                cartManager.getCartItemList()) {
            total_amount += item.getPrice() * item.getQuantity();
        }
        // update text view
        String formatNumber = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(total_amount);
        subtotal.setText("Subtotal: " + formatNumber);
        shipping_fee.setText("Shipping fee: " + 0);
        total_price.setText("Total: " + formatNumber);
    }

    private void updateUICart() {
        if (cartManager.getCartItemList().isEmpty()) {
            ll_empty_cart.setVisibility(LinearLayout.VISIBLE);
            cartRecylerView.setVisibility(LinearLayout.GONE);
        } else {
            ll_empty_cart.setVisibility(LinearLayout.GONE);
            cartRecylerView.setVisibility(LinearLayout.VISIBLE);
            updateTotalAmount();
        }
    }

    // Convert all cart items to CartItemObjRequest for API request
    private ArrayList<CartItemObjRequest> convertCartItemObject(List<CartItem> cartItems) {
        ArrayList<CartItemObjRequest> cartItemObjRequests = new ArrayList<>();
        for (CartItem item :
                cartItems) {
            cartItemObjRequests.add(new CartItemObjRequest(item.getId(), item.getQuantity(), item.getPrice()));
        }
        return cartItemObjRequests;
    }

    private void fetchOrder( OrderRequest orderRequest) {
        Call<OrderResponse> call = orderService.createOrder(orderRequest);
        call.enqueue(new Callback<OrderResponse>() {
                         @Override
                         public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                             if (response.isSuccessful()) {
                                 clearCart();
                             } else {
                                 // payment failed
                                 showDialog("Payment failed", "Your payment has been failed");
                             }
                         }

                         @Override
                         public void onFailure(Call<OrderResponse> call, Throwable t) {
                             showDialog("Payment failed", "Your payment has been failed");
                         }
                     }
        );
    }

    private void clearCart() {
        cartManager.clearCart();
        cartAdapter.notifyDataSetChanged();
        updateTotalAmount();
        updateUICart();
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    public void onCartItemChanged() {
        updateTotalAmount();
        updateUICart();
        cartAdapter.notifyDataSetChanged();
    }

}
