package br.com.erpsystem.almoxarifado.models.enums;

public enum TipoMovimentacaoEstoque {
    ENTRADA("ENTRADA"),
    RETIRADA("RETIRADA"),
    AJUSTE("AJUSTE");

    private final String tipoMovimentacao;

    TipoMovimentacaoEstoque(String tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public String getTipoMovimentacao() {
        return tipoMovimentacao;
    }
}
