package com.fiap.restaurantes.entity.enums;

public enum MesaStatus {
    OCUPADA(0), LIVRE(1);

    private int value;

    MesaStatus(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    public static MesaStatus fromValue(int value){
        for (MesaStatus status : MesaStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Valor numérico inválido: " + value);
    }
}
