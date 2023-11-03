package br.com.erpsystem.sistema.models.enums;

public enum StatusSistema {
    NORMAL(0),
    EXCLUIDO(999);

    private final int statusId;

    StatusSistema(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }
}
