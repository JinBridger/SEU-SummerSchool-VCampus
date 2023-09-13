package app.vcampus.server.controller;

import app.vcampus.server.entity.CardTransaction;
import app.vcampus.server.entity.FinanceCard;
import app.vcampus.server.entity.IEntity;
import app.vcampus.server.enums.TransactionType;
import app.vcampus.server.utility.Database;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.router.RouteMapping;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class FinanceController {
    /**
     *  solve client to get the user's financecard which contains cardNumber and balance
     *   The constraint is the card != null
     * @param request  from client with role and uri
     * @param database datebase
     * @return  it returns an “OK” response with a map containing a JSON string representing the finance card information
     */
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

    /**
     * solve client to get the user's financecard by cardNumber
     * The constr
     * @param request  from client with role and uri
     * @param database datebase
     * @return  it returns an “OK” response with a map containing a JSON string representing the finance card information
     */
    @RouteMapping(uri = "finance/card/getByCardNumber", role = "finance_staff")
    public Response getByCardNumber(Request request, org.hibernate.Session database) {
        Integer cardNumber = Integer.parseInt(request.getParams().get("cardNumber"));

        FinanceCard card = database.get(FinanceCard.class, cardNumber);

        if (card == null) {
            return Response.Common.error("卡号不存在");
        }

        return Response.Common.ok(Map.of("card", card.toJson()));
    }

    /**
     * update the Financecard
     * @param request  from client with role and uri
     * @param database database
     * @return  it returns an “OK” response with a map containing a JSON string representing the finance card information
     */
    @RouteMapping(uri = "finance/card/update", role = "finance_staff")
    public Response updateCard(Request request, org.hibernate.Session database) {
        FinanceCard newCard = IEntity.fromJson(request.getParams().get("card"), FinanceCard.class);

        if (newCard == null) {
            return Response.Common.error("卡号不存在");
        }

        Transaction tx = database.beginTransaction();
        database.merge(newCard);
        tx.commit();

        return Response.Common.ok(Map.of("card", newCard.toJson()));
    }

    /**
     *  recharge the card with the request's cardNumber and amount
     * @param request  from client with role and uri
     * @param database database
     * @return  it returns an “OK” response with a map containing a JSON string representing the finance card information
     */
    @RouteMapping(uri = "finance/card/recharge", role = "finance_staff")
    public Response rechargeCard(Request request, org.hibernate.Session database) {
        Integer cardNumber = Integer.parseInt(request.getParams().get("cardNumber"));
        Integer amount = Integer.parseInt(request.getParams().get("amount"));

        FinanceCard card = database.get(FinanceCard.class, cardNumber);

        if (card == null) {
            return Response.Common.error("卡号不存在");
        }

        Transaction tx = database.beginTransaction();
        card.setBalance(card.getBalance() + amount);
        database.persist(card);

        CardTransaction transaction = new CardTransaction();
        transaction.setCardNumber(cardNumber);
        transaction.setAmount(amount);
        transaction.setTime(new Date());
        transaction.setType(TransactionType.deposit);
        transaction.setDescription("充值");
        database.persist(transaction);
        tx.commit();

        return Response.Common.ok(Map.of("card", card.toJson()));
    }

    /**
     *
     * @param request
     * @param database
     * @return
     */
    @RouteMapping(uri = "finance/bills/getSelf", role = "finance_user")
    public Response getSelfBills(Request request, org.hibernate.Session database) {
        int cardNumber = request.getSession().getCardNum();

        List<CardTransaction> transactions = Database.getWhereString(CardTransaction.class, "cardNumber", Integer.toString(cardNumber), database);

        return Response.Common.ok(transactions.stream().map(CardTransaction::toJson).collect(Collectors.toList()));
    }
}

