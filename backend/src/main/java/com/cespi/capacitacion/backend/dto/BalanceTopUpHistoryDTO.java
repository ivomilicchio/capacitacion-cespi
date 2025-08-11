package com.cespi.capacitacion.backend.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BalanceTopUpHistoryDTO {

    private List<BalanceTopUpResponseDTO> balanceTopUps;

    public BalanceTopUpHistoryDTO() {
        this.balanceTopUps = new ArrayList<>();
    }

    public BalanceTopUpHistoryDTO(List<BalanceTopUpResponseDTO> balanceTopUps) {
        this.balanceTopUps = balanceTopUps;
    }

    public List<BalanceTopUpResponseDTO> getBalanceTopUps() {
        return balanceTopUps;
    }

    public void setBalanceTopUps(List<BalanceTopUpResponseDTO> balanceTopUps) {
        this.balanceTopUps = balanceTopUps;
    }

    public void addBalanceTopUp(BalanceTopUpResponseDTO balanceTopUpResponseDTO) {
        this.balanceTopUps.add(balanceTopUpResponseDTO);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BalanceTopUpHistoryDTO)) return false;
        BalanceTopUpHistoryDTO that = (BalanceTopUpHistoryDTO) o;
        return Objects.equals(balanceTopUps, that.balanceTopUps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balanceTopUps);
    }
}
