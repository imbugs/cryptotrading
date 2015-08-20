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
package com.crypto.services.rest;

import com.crypto.dao.ParameterDao;
import com.crypto.entities.Parameter;
import com.google.gson.Gson;
import org.jboss.resteasy.spi.NotFoundException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Rest service returning parameters
 */
@Path("/")
@Stateless
@Produces(MediaType.APPLICATION_JSON)
public class ParameterService {

    @EJB
    private ParameterDao parameterDao;

    @POST
    @Path("/parameter/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getParameter(@PathParam("name") String name) {

        final Parameter parameter = parameterDao.get(name);

        if (parameter != null) {

            final Gson gson = new Gson();
            final String jsonString = gson.toJson(parameter);

            return jsonString;
        } else {
            throw new NotFoundException("Parameter not found: " + parameter);
        }
    }
}
