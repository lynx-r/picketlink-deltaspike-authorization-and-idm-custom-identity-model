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
package org.jboss.as.quickstarts.picketlink.deltaspike.authorization.security;

import org.apache.deltaspike.security.api.authorization.Secures;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.annotations.Admins;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.annotations.Customers;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.annotations.Organizers;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.Group;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.GroupMembership;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.util.Resources;
import org.picketlink.Identity;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.model.Account;
import org.picketlink.idm.query.IdentityQuery;
import org.picketlink.idm.query.RelationshipQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * Defines the authorization logic for the @Employee and @Admin security binding types
 *
 * @author Shane Bryzak
 */
@ApplicationScoped
public class CustomAuthorizer {
  @Inject
  private PartitionManager partitionManager;

  /**
   * This method is used to check if classes and methods annotated with {@link Admins} can perform
   * the operation or not
   *
   * @param identity        The Identity bean, representing the currently authenticated user
   * @param identityManager The IdentityManager provides methods for checking a user's roles
   * @return true if the user can execute the method or class
   * @throws Exception
   */
  @Secures
  @Admins
  public boolean doAdminsCheck(Identity identity, IdentityManager identityManager, RelationshipManager relationshipManager) throws Exception {
    return hasGroup(identity, identityManager, relationshipManager, Resources.ADMINS_GROUP_NAME);
  }

  @Secures
  @Organizers
  public boolean doOrganizersCheck(Identity identity, IdentityManager identityManager, RelationshipManager relationshipManager) throws Exception {
    return hasGroup(identity, identityManager, relationshipManager, Resources.ORGANIZERS_GROUP_NAME);
  }

  @Secures
  @Customers
  public boolean doCustomersCheck(Identity identity, IdentityManager identityManager, RelationshipManager relationshipManager) throws Exception {
    return hasGroup(identity, identityManager, relationshipManager, Resources.CUSTOMERS_GROUP_NAME);
  }

  private boolean hasGroup(Identity identity, IdentityManager identityManager, RelationshipManager relationshipManager,
                           String groupName) {
    IdentityQuery<Group> queryGroup = identityManager.createIdentityQuery(Group.class);

    // query all childs of sales unit
    queryGroup.setParameter(Group.NAME, groupName);

    List<Group> groups = queryGroup.getResultList();

    if (groups.size() == 1) {
      Group group = groups.get(0);
      Account user = identity.getAccount();

      RelationshipQuery<GroupMembership> query = relationshipManager.createRelationshipQuery(GroupMembership.class);
      query.setParameter(GroupMembership.GROUP, group);
      query.setParameter(GroupMembership.MEMBER, user);

      // user is assigned with two groups
      List<GroupMembership> resultList = query.getResultList();

      return resultList.size() > 0;
    }
    return false;
  }
}
