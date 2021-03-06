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
package org.jboss.as.quickstarts.picketlink.deltaspike.authorization.util;

import org.picketlink.annotations.PicketLink;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;

/**
 * This class uses CDI to alias Java EE resources, such as the persistence context, to CDI beans
 */
public class Resources {
  public static final String REALM_ACME_NAME = "Acme";
  public static final String ADMINS_GROUP_NAME = "admins";
  public static final String ORGANIZERS_GROUP_NAME = "organizers";
  public static final String CUSTOMERS_GROUP_NAME = "customers";

  @PersistenceContext(unitName = "authPU")
  private EntityManager em;

  @Produces
  @PicketLink
  public EntityManager getPicketLinkEntityManager() {
    return em;
  }

  @Produces
  public Logger produceLog(InjectionPoint injectionPoint) {
    return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
  }

  @Named
  @Produces
  @RequestScoped
  public FacesContext getFacesContext() {
    return FacesContext.getCurrentInstance();
  }

}
