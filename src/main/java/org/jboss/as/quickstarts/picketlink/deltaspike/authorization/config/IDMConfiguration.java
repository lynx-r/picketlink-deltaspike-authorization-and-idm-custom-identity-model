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

import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.Application;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.ApplicationAccess;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.ApplicationRealm;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.Grant;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.Group;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.GroupMembership;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.Realm;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.Role;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.User;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.entity.*;
import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.util.Resources;
import org.picketlink.annotations.PicketLink;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.config.IdentityConfiguration;
import org.picketlink.idm.config.IdentityConfigurationBuilder;
import org.picketlink.idm.internal.DefaultPartitionManager;
import org.picketlink.idm.model.Partition;
import org.picketlink.internal.EEJPAContextInitializer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * This bean produces the configuration for PicketLink IDM
 *
 * @author Shane Bryzak
 */
@ApplicationScoped
public class IDMConfiguration {

  @Inject
  private EEJPAContextInitializer contextInitializer;

  private IdentityConfiguration identityConfig = null;

  protected static final String APPLICATION_TEST_NAME = "Test Application";

  private PartitionManager partitionManager;
  private Realm acmeRealm;
  private Application spApplication;
  private ApplicationRealm spApplicationPartition;

  @Produces
  public IdentityConfiguration createConfig() throws Exception {
    if (identityConfig == null) {
      initConfig();

      createDefaultRealm();
      createSPApplication();
    }
    return identityConfig;
  }

  @Produces
  public void initConfig() throws Exception {
    IdentityConfigurationBuilder builder = new IdentityConfigurationBuilder();

    builder
        .named("default")
        .stores()
        .jpa()
        .supportType(
            User.class,
            Role.class,
            Group.class,
            Realm.class,
            Application.class,
            ApplicationRealm.class)
        .supportGlobalRelationship(
            Grant.class,
            GroupMembership.class,
            ApplicationAccess.class)
        .supportCredentials(true)
        .mappedEntity(
            ApplicationAccessTypeEntity.class,
            ApplicationTypeEntity.class,
            ApplicationRealmTypeEntity.class,
            PartitionTypeEntity.class,
            GrantTypeEntity.class,
            GroupMembershipTypeEntity.class,
            GroupTypeEntity.class,
            RealmTypeEntity.class,
            RoleTypeEntity.class,
            UserTypeEntity.class,
            PasswordCredentialTypeEntity.class,
            RelationshipTypeEntity.class,
            RelationshipIdentityTypeEntity.class)
        .addContextInitializer(contextInitializer);
    identityConfig = builder.build();
    partitionManager = new DefaultPartitionManager(builder.buildAll());
  }

  private void createSPApplication() throws Exception {
    Partition partition = partitionManager.getPartition(ApplicationRealm.class, APPLICATION_TEST_NAME);
    if (partition == null) {
      this.spApplication = new Application(APPLICATION_TEST_NAME);

      IdentityManager identityManager = partitionManager.createIdentityManager(acmeRealm);

      identityManager.add(spApplication);

      this.spApplicationPartition = new ApplicationRealm(APPLICATION_TEST_NAME);

      partitionManager.add(this.spApplicationPartition);
    }
  }

  private void createDefaultRealm() throws NoSuchAlgorithmException {
    Partition partition = getDefaultPartition();
    if (partition == null) {
      acmeRealm = new Realm(Resources.REALM_ACME_NAME);

      acmeRealm.setEnforceSSL(true);

      KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();

      acmeRealm.setPrivateKey(keyPair.getPrivate().getEncoded());
      acmeRealm.setPublickKey(keyPair.getPublic().getEncoded());

      acmeRealm.setNumberFailedLoginAttempts(3);

      partitionManager.add(acmeRealm);
    }
  }

  @Produces
  @PicketLink
  public Partition getDefaultPartition() {
    return partitionManager.getPartition(Realm.class, Resources.REALM_ACME_NAME);
  }
}
