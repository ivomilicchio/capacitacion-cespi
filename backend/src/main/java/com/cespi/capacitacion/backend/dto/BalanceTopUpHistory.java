package com.cespi.capacitacion.backend.dto;

import com.cespi.capacitacion.backend.entity.BalanceTopUp;

import java.util.ArrayList;
import java.util.List;

public class BalanceTopUpHistory {

    private List<BalanceTopUpResponse> balanceTopUps;

    public BalanceTopUpHistory() {
        this.balanceTopUps = new ArrayList<>();
    }

    public BalanceTopUpHistory(List<BalanceTopUpResponse> balanceTopUps) {
        this.balanceTopUps = balanceTopUps;
    }

    public List<BalanceTopUpResponse> getBalanceTopUps() {
        return balanceTopUps;
    }

    public void setBalanceTopUps(List<BalanceTopUpResponse> balanceTopUps) {
        this.balanceTopUps = balanceTopUps;
    }

    public void addBalanceTopUp(BalanceTopUpResponse balanceTopUpResponse) {
        this.balanceTopUps.add(balanceTopUpResponse);
    }
}
