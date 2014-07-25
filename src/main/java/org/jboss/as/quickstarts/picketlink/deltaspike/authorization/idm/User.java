/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm;

import org.picketlink.idm.model.AbstractIdentityType;
import org.picketlink.idm.model.Account;
import org.picketlink.idm.model.annotation.AttributeProperty;
import org.picketlink.idm.model.annotation.IdentityStereotype;
import org.picketlink.idm.model.annotation.StereotypeProperty;
import org.picketlink.idm.model.annotation.Unique;
import org.picketlink.idm.query.QueryParameter;

import java.util.Date;

import static org.picketlink.idm.model.annotation.IdentityStereotype.Stereotype.USER;
import static org.picketlink.idm.model.annotation.StereotypeProperty.Property.IDENTITY_USER_NAME;

/**
 * <p>Represents an user.</p>
 *
 * @author Pedro Igor
 */
@IdentityStereotype(USER)
public class User extends AbstractIdentityType implements Account {

  public static final QueryParameter USER_NAME = QUERY_ATTRIBUTE.byName("userName");

  @StereotypeProperty(IDENTITY_USER_NAME)
  @AttributeProperty
  @Unique
  private String userName;

  @AttributeProperty
//  @Size(min = 3, max = 100, message = "{field.size}")
  private String firstName;
  @AttributeProperty
//  @Size(min = 3, max = 100, message = "{field.size}")
  private String lastName;
  //  @Email(message = "{email.NotValid}")
//  @NotBlank(message = "{field.NotBlank}")
  @AttributeProperty
  private String email;
  @AttributeProperty
//  @Column(length = 255)
  private String middleName;
  @AttributeProperty
//  @Size(max = 12)
//  @Column(length = 12)
  private String telephone;
  @AttributeProperty
//  @Column(length = 5000)
//  @Size(max = 5000)
  private String address;
  @AttributeProperty
  private int postIndex;
  @AttributeProperty
  private Date registerDate;
  @AttributeProperty
  private Date lastVisitDate;
  @AttributeProperty
  private boolean isOrganizer;
  @AttributeProperty
  private boolean isAdmin;
  public User() {
    this(null);
  }
  public User(String userName) {
    this.userName = userName;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  public boolean isOrganizer() {
    return isOrganizer;
  }

  public void setOrganizer(boolean isOrganizer) {
    this.isOrganizer = isOrganizer;
  }

  public Date getLastVisitDate() {
    return lastVisitDate;
  }

  public void setLastVisitDate(Date lastVisitDate) {
    this.lastVisitDate = lastVisitDate;
  }

  public Date getRegisterDate() {
    return registerDate;
  }

  public void setRegisterDate(Date registerDate) {
    this.registerDate = registerDate;
  }

  public int getPostIndex() {
    return postIndex;
  }

  public void setPostIndex(int postIndex) {
    this.postIndex = postIndex;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  @Override
  public String toString() {
    String result = getClass().getSimpleName() + "";
    /*if (middleName != null && !middleName.trim().isEmpty())
      result += "middleName: " + middleName;
    if (telephone != null && !telephone.trim().isEmpty())
      result += ", telephone: " + telephone;
    if (address != null && !address.trim().isEmpty())
      result += ", address: " + address;
    result += ", postIndex: " + postIndex;
    result += ", registerDate: " + registerDate;
    result += ", visitDate: " + lastVisitDate; */
    return result;
  }

}