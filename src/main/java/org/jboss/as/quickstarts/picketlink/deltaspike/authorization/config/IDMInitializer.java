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
package org.jboss.as.quickstarts.picketlink.deltaspike.authorization.config;

import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.Group;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.GroupMembership;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.Realm;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.User;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.util.Resources;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.query.IdentityQuery;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.List;

/**
 * This startup bean creates a number of default users, groups and roles when the application is started.
 *
 * @author Shane Bryzak
 */
@Singleton
@Startup
public class IDMInitializer {

  @Inject
  private PartitionManager partitionManager;

  private String adminLoginName = "admin";
  private String adminEmail = "admin@admin.admin";
  private String password = "admin";
  private String adminFirstName = "Admin";
  private String adminLastName = "Admin";

  @PostConstruct
  public void create() throws Exception {
    Realm acmeRealm = partitionManager.getPartition(Realm.class, Resources.REALM_ACME_NAME);
    IdentityManager identityManager = partitionManager.createIdentityManager(acmeRealm);
    IdentityQuery<User> query = identityManager.createIdentityQuery(User.class);

    // let's check if the user is stored by querying by name
    query.setParameter(User.USER_NAME, adminLoginName);

    List<User> users = query.getResultList();

    if (users.size() == 0) {
      // if admin doesn't exist create he
      User admin = new User(adminLoginName);
      admin.setAdmin(true);
      admin.setEmail(adminEmail);
      admin.setFirstName(adminFirstName);
      admin.setLastName(adminLastName);

      identityManager.add(admin);
      identityManager.updateCredential(admin, new Password(password));

      // create admins group
      Group adminsGroup = addGroup(identityManager, Resources.ADMINS_GROUP_NAME);

      RelationshipManager relationshipManager = partitionManager.createRelationshipManager();

      // add admin as member of admins group
      relationshipManager.add(new GroupMembership(admin, adminsGroup));
    }
  }

  public Group addGroup(IdentityManager identityManager, String groupName) {
    IdentityQuery<Group> query = identityManager.createIdentityQuery(Group.class);

    // query all childs of sales unit
    query.setParameter(Group.NAME, groupName);

    List<Group> groups = query.getResultList();

    if (groups.size() == 0) {
      // if doesn't exist group {{ groupName }} create it
      Group group = new Group(groupName);
      identityManager.add(group);
      return group;
    }
    return groups.get(0);
  }
}
