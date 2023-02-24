package com.github.ana.ifood.mp;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PratoCarrinho {

    public String usuario;
    public Long prato;
    public Restaurante restaurante;
    public BigDecimal preco;
    public String cliente;


    public static Uni<Object> save(PgPool client, String cliente, Long prato) {

        return client.preparedQuery("INSERT INTO prato_cliente (cliente, prato) VALUES ($1, $2) RETURNING (cliente)").execute(
                        Tuple.of(cliente, prato))


                .map(pgRowSet ->pgRowSet.iterator().next().getString("cliente"));
    }




    public static Uni<List<PratoCarrinho>> findCarrinho(PgPool client, String cliente) {
        return client.preparedQuery("select * from prato_cliente where cliente = $1 ").execute(Tuple.of(cliente))
                .map(pgRowSet -> {
                    List<PratoCarrinho> list = new ArrayList<>(pgRowSet.size());
                    for (Row row : pgRowSet) {
                        list.add(toPratoCarrinho(row));
                    }
                    return list;
                });
    }

    private static PratoCarrinho toPratoCarrinho(Row row) {
        PratoCarrinho pc = new PratoCarrinho();
        pc.cliente = row.getString("cliente");
        pc.prato = row.getLong("prato");
        return pc;
    }

    public static Uni<Boolean> delete(PgPool client, String cliente) {
        return client.preparedQuery("DELETE FROM prato_cliente WHERE cliente = $1").execute(Tuple.of(cliente))
                .map(pgRowSet -> pgRowSet.rowCount() == 1);

    }
}
