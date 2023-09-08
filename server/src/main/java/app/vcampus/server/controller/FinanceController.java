package app.vcampus.server.controller;

import app.vcampus.server.entity.CardTransaction;
import app.vcampus.server.entity.FinanceCard;
import app.vcampus.server.utility.Database;
import app.vcampus.server.utility.DateUtility;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.router.RouteMapping;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class FinanceController {
    @RouteMapping(uri = "finance/card/getSelf", role = "finance_user")
    public Response getSelfCard(Request request, org.hibernate.Session database) {
        Integer cardNumber = request.getSession().getCardNum();

        FinanceCard card = database.get(FinanceCard.class, cardNumber);

        if (card == null) {
            Transaction tx = database.beginTransaction();
            card = new FinanceCard();
            card.setCardNumber(cardNumber);
            card.setBalance(0);
            database.persist(card);
            tx.commit();
        }

        return Response.Common.ok(Map.of("card", card.toJson()));
    }

    @RouteMapping(uri = "finance/bills/getSelf", role = "finance_user")
    public Response getSelfBills(Request request, org.hibernate.Session database) {
        int cardNumber = request.getSession().getCardNum();

        List<CardTransaction> transactions = Database.getWhereString(CardTransaction.class, "cardNumber", Integer.toString(cardNumber), database);

        return Response.Common.ok(transactions.stream().map(CardTransaction::toJson).collect(Collectors.toList()));
    }
}
