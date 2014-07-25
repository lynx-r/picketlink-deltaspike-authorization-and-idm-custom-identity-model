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

import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.Group;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.GroupMembership;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.Realm;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.util.Resources;
import org.picketlink.Identity;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.model.Account;
import org.picketlink.idm.query.IdentityQuery;
import org.picketlink.idm.query.RelationshipQuery;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * This is a utility bean that may be used by the view layer to determine whether the
 * current user has specific privileges.
 *
 * @author Shane Bryzak
 */
@Named
@Stateless
public class AuthorizationChecker {

  @Inject
  private Identity identity;

  @Inject
  private PartitionManager partitionManager;

  public boolean hasGroup(String groupName) {
    IdentityManager identityManager = partitionManager.createIdentityManager(partitionManager.getPartition(Realm.class,
        Resources.REALM_ACME_NAME));
    IdentityQuery<Group> queryGroup = identityManager.createIdentityQuery(Group.class);

    // query all childs of sales unit
    queryGroup.setParameter(Group.NAME, groupName);

    List<Group> groups = queryGroup.getResultList();

    if (groups.size() == 1) {
      Group group = groups.get(0);
      Account user = identity.getAccount();

      RelationshipManager relationshipManager = partitionManager.createRelationshipManager();
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
