package com.crypto.services.rest;

import com.crypto.dao.TradeConditionDao;
import com.crypto.dao.TradeRuleDao;
import com.crypto.entities.TradeCondition;
import com.crypto.entities.TradeRule;
import com.crypto.services.rest.wrapper.TradeRuleWrapper;
import javassist.NotFoundException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Service that fetches the trade conditions of a trade rule
 * Created by Jan Wicherink on 11-9-2015.
 */
@Stateless
@Path("/")
public class TradeConditionService {

    @EJB
    private TradeRuleDao tradeRuleDao;

    @EJB
    private TradeConditionDao tradeConditionDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getTradeConditions/{tradeRuleId}")
    public TradeRuleWrapper getTradeConditions(@PathParam("tradeRuleId") Integer tradeRuleId) throws NotFoundException {

        final TradeRule tradeRule = tradeRuleDao.get(tradeRuleId);
        final List<TradeCondition> tradeConditions = tradeConditionDao.getAllActiveTradeConditionsOfTradeRule(tradeRule);

        if (tradeConditions != null) {

            String tradeRuleText;
            List<String> tradeRuleConditionsText = new ArrayList<>();

            tradeRuleText = tradeRule.getDescription();

            for (TradeCondition tradeCondition : tradeConditions) {
                tradeRuleConditionsText.add(tradeCondition.getTradeConditionType().getMessageFormat());
            }

            TradeRuleWrapper tradeRuleWrapper = new TradeRuleWrapper(tradeRuleText, tradeRuleConditionsText);

            return tradeRuleWrapper;
        }
        else {
            throw new NotFoundException("Trade conditions not found for trade rule: " + tradeRule.getId());
        }
    }
}
