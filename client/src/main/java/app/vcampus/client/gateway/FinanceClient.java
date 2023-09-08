package app.vcampus.client.gateway;

import app.vcampus.client.net.NettyHandler;
import app.vcampus.server.entity.CardTransaction;
import app.vcampus.server.entity.FinanceCard;
import app.vcampus.server.entity.IEntity;
import app.vcampus.server.entity.User;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class FinanceClient {
    public static FinanceCard getMyCard(NettyHandler handler) {
        Request request = new Request();
        request.setUri("finance/card/getSelf");

        try {
            Response response = BaseClient.sendRequest(handler, request);
            if (response.getStatus().equals("success")) {
                String data = ((Map<String, String>) response.getData()).get("card");
                return IEntity.fromJson(data, FinanceCard.class);
            } else {
                throw new RuntimeException("Failed to get card");
            }
        } catch (Exception e) {
            log.warn("Fail to get card", e);
            return null;
        }
    }

    public static List<CardTransaction> getMyBills(NettyHandler handler) {
        Request request = new Request();
        request.setUri("finance/bills/getSelf");

        try {
            Response response = BaseClient.sendRequest(handler, request);
            if (response.getStatus().equals("success")) {
                List<String> raw_data = (List<String>) response.getData();
                List<CardTransaction> data = raw_data.stream().map(x -> IEntity.fromJson(x, CardTransaction.class)).collect(Collectors.toList());
                data.sort((a, b) -> b.getTime().compareTo(a.getTime()));
                return data;
            } else {
                throw new RuntimeException("Failed to get bills");
            }
        } catch (Exception e) {
            log.warn("Fail to get bills", e);
            return null;
        }
    }
}
