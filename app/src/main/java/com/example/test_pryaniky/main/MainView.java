package com.example.test_pryaniky.main;

import com.example.test_pryaniky.domain.model.BlockProperty;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleTagStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface MainView extends MvpView {

    @StateStrategyType(AddToEndSingleTagStrategy.class)
    void setData(List<BlockProperty> listBlockProperty);

    @StateStrategyType(AddToEndSingleTagStrategy.class)
    void showData();

    @StateStrategyType(AddToEndSingleTagStrategy.class)
    void showLoadErrorData();

    void showError(String error);

    void showProgress();

    void hideProgress();
}
