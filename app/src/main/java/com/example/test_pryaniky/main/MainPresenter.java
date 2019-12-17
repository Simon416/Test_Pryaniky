package com.example.test_pryaniky.main;

import com.example.test_pryaniky.data.BlockType;
import com.example.test_pryaniky.data.DataServer;
import com.example.test_pryaniky.domain.model.BlockProperty;
import com.example.test_pryaniky.domain.model.Block;
import com.example.test_pryaniky.domain.model.Data;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private boolean firstLoadedSuccess = false;
    private CompositeDisposable disposable = new CompositeDisposable();
    private DataServer dataServer;

    MainPresenter(DataServer dataServer) {
        this.dataServer = dataServer;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    void loadData() {
        getViewState().showData();
        disposable.add(dataServer.getDataFromServer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> getViewState().showProgress())
                .doFinally(() -> getViewState().hideProgress())
                .subscribe(
                        this::handleData,
                        this::handleError
                )
        );
    }

    private void handleError(Throwable throwable) {
        getViewState().showError(throwable.toString());
        if (!firstLoadedSuccess) {
            getViewState().showLoadErrorData();
        }
    }

    private void handleData(Data data) {
        List<BlockProperty> listBlockProperty = new ArrayList<>();
        for (BlockType blockType : data.getListVisibleBlocksType()) {
            for (Block block : data.getListBlocks()) {
                if (block.getType() == blockType) {
                    listBlockProperty.add(block.getProperty());
                }
            }
        }
        getViewState().setData(listBlockProperty);
        firstLoadedSuccess = true;
    }
}
