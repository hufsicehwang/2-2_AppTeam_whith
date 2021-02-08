package com.map.a2_2_teamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.map.a2_2_teamproject.ApiInterface.ApiClient;
import com.map.a2_2_teamproject.ApiInterface.ApiInterface;
import com.map.a2_2_teamproject.adapter.LocationAdapter;
import com.map.a2_2_teamproject.model.category_search.CategoryResult;
import com.map.a2_2_teamproject.model.category_search.Document;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class searchActivity extends AppCompatActivity {

    EditText mSearchEdit;
    RecyclerView recyclerView;
    ArrayList<Document> documentArrayList = new ArrayList<>(); //지역명 검색 결과 리스트


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        mSearchEdit = findViewById(R.id.map_et_search);
        recyclerView = findViewById(R.id.map_recyclerview);
        LocationAdapter locationAdapter = new LocationAdapter(documentArrayList, getApplicationContext(), mSearchEdit, recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false); //레이아웃매니저 생성
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL)); //아래구분선 세팅
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(locationAdapter);


      mSearchEdit.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            // 입력하기 전에
            recyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            if (charSequence.length() >= 1) {
                // if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

                documentArrayList.clear();
                locationAdapter.clear();
                locationAdapter.notifyDataSetChanged();
                ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                Call<CategoryResult> call = apiInterface.getSearchLocation( charSequence.toString(), 15); //통신하기위한 기본 세팅
                call.enqueue(new Callback<CategoryResult>() {
                    @Override
                    public void onResponse(@NotNull Call<CategoryResult> call, @NotNull Response<CategoryResult> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            for (Document document : response.body().getDocuments()) {
                                locationAdapter.addItem(document);
                            }
                            locationAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<CategoryResult> call, @NotNull Throwable t) {

                    }
                });
                //}
                //mLastClickTime = SystemClock.elapsedRealtime();
            } else {
                if (charSequence.length() <= 0) {
                    recyclerView.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // 입력이 끝났을 때
        }
    });

        mSearchEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {
            } else {
                recyclerView.setVisibility(View.GONE);
            }
        }
    });


    }

}


































