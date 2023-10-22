package com.vlc2.assets.entity;

public enum AssetStatus {
    DA_CONFIGURARE,
    DA_ASSEGNARE,
    ASSEGNATO,
    RICONSEGNATO,
    DA_FORMATTARE,
    ROTTO,
    MULETTO;


    public static AssetStatus[] getAllStatusValues() {
        return AssetStatus.values();
    }

    public static AssetStatus searchStatus(String text) {
        for (AssetStatus stato : AssetStatus.values()) {
            if (stato.name().equalsIgnoreCase(text)) {
                return stato;
            }
        }
        throw new IllegalArgumentException("Status not valid: " + text);
    }


}
