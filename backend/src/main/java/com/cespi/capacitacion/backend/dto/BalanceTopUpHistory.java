package com.cespi.capacitacion.backend.dto;

import com.cespi.capacitacion.backend.entity.BalanceTopUp;
import com.cespi.capacitacion.backend.entity.ParkingSession;

import java.util.List;

public class BalanceTopUpHistory {

    private List<BalanceTopUp> balanceTopUpHistory;

    public BalanceTopUpHistory() {

    }

    public BalanceTopUpHistory(List<BalanceTopUp> balanceTopUpHistory) {
        this.balanceTopUpHistory = balanceTopUpHistory;
    }
}
