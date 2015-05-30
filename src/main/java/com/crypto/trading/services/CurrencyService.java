/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.crypto.trading.services;

import com.crypto.dao.CurrencyDao;
import com.crypto.entities.Currency;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;

import com.google.gson.Gson;

/**
 * Rest service returning currency
 */
@Path("/")
@Stateless
public class CurrencyService {

    @EJB
    private CurrencyDao currencyDao;

    @POST
    @Path("/currency/{code}")
    @Produces("application/json")
    public String getCurrency(@PathParam("code") String code) {

        final Currency currency = currencyDao.get(code);

        if (currency != null) {

            final Gson gson = new Gson();
            final String jsonString = gson.toJson(currency);

            return jsonString;
        } else {
            throw new NotFoundException("Currency not found: " + code);
        }
    }
}
