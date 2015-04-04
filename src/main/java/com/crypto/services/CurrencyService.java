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
package com.crypto.services;

import com.crypto.com.crypto.dao.CurrencyDao;
import com.crypto.com.crypto.dao.CurrencyDaoImpl;
import com.crypto.entities.Currency;

import javax.inject.Inject;
import javax.ws.rs.*;

/**
 * A simple REST service which is able to say hello to someone using HelloService Please take a look at the web.xml where JAX-RS
 * is enabled And notice the @PathParam which expects the URL to contain /json/David or /xml/Mary
 *
 * @author bsutter@redhat.com
 */

@Path("/")
public class CurrencyService {

    @Inject
    private CurrencyDao currencyDao;

    @POST
    @Path("/currency/{code}")
    @Produces("application/json")
    public String getCurrency(@PathParam("code") String code) {

        final Currency currency = currencyDao.getCurrency(code);

        if (currency != null) {
            System.out.println("code: " + code);
            return "{\"result\":\"" + currency.getDescription() + "\"}";
        }
        else {
            System.out.println("Currency not found!");
            return "{\"result\":\"NOT FOUND\"}";
        }
    }
}
