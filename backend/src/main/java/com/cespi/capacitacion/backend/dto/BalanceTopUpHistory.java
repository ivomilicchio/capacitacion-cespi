package com.cespi.capacitacion.backend.dto;

import com.cespi.capacitacion.backend.entity.BalanceTopUp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BalanceTopUpHistory)) return false;
        BalanceTopUpHistory that = (BalanceTopUpHistory) o;
        return Objects.equals(balanceTopUps, that.balanceTopUps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balanceTopUps);
    }
}
