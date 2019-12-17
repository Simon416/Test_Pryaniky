package com.example.test_pryaniky.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.test_pryaniky.App;
import com.example.test_pryaniky.R;
import com.example.test_pryaniky.domain.model.BlockProperty;

import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout errorLayout;

    @InjectPresenter
    MainPresenter mainPresenter;

    @ProvidePresenter
    MainPresenter providePresenter() {
        return new MainPresenter(((App) getApplicationContext()).getIDataServer());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void setData(List<BlockProperty> listBlockProperty) {
        recyclerView.setAdapter(new BlockAdapter(listBlockProperty, this::showBlockName));
    }

    @Override
    public void showData() {
        errorLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadErrorData() {
        errorLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void showBlockName(String blockName) {
        Toast.makeText(this, blockName, Toast.LENGTH_SHORT).show();
    }

    private void init() {
        Button btnRepeatView = findViewById(R.id.btnRepeatView);
        btnRepeatView.setOnClickListener(v -> mainPresenter.loadData());
        errorLayout = findViewById(R.id.errorLayout);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshView);
        swipeRefreshLayout.setOnRefreshListener(() -> mainPresenter.loadData());
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}
