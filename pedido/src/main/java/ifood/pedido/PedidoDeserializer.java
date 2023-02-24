package ifood.pedido;

import ifood.pedido.DTO.PedidoRealizadoDTO;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class PedidoDeserializer extends ObjectMapperDeserializer<PedidoRealizadoDTO> {


    public PedidoDeserializer() {
        super(PedidoRealizadoDTO.class);
    }
}
