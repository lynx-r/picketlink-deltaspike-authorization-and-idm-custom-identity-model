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
package org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.entity;

import org.jboss.as.quickstarts.picketlink.deltaspike.authorization.idm.User;
import org.picketlink.idm.jpa.annotations.AttributeValue;
import org.picketlink.idm.jpa.annotations.OwnerReference;
import org.picketlink.idm.jpa.annotations.entity.IdentityManaged;
import org.picketlink.idm.model.annotation.AttributeProperty;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import java.util.Date;

/**
* <p>Entity with the mapping for {@link org.picketlink.idm.jpa.model.custom.simple.User}.</p>
*
* @author Pedro Igor
*/
@IdentityManaged(User.class)
@Entity
public class UserTypeEntity extends AbstractIdentityTypeEntity {
  @AttributeValue
  private String userName;

  @OwnerReference
  @ManyToOne(fetch = FetchType.LAZY)
  private RealmTypeEntity realm;

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
  //  @Size(min = 3, max = 100, message = "{field.size}")
  @AttributeProperty
  @Column(length = 255)
  private String middleName;
  @AttributeProperty
  @Size(max = 12)
  @Column(length = 12)
  private String telephone;
  @AttributeProperty
  @Column(length = 5000)
  @Size(max = 5000)
  private String address;
  @AttributeProperty
  private int postIndex;
  @AttributeProperty
  private Date lastVisitDate;
  @AttributeProperty
  private boolean isOrganizer;
  @AttributeProperty
  private boolean isAdmin;

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getPostIndex() {
    return postIndex;
  }

  public void setPostIndex(int postIndex) {
    this.postIndex = postIndex;
  }

  public Date getLastVisitDate() {
    return lastVisitDate;
  }

  public void setLastVisitDate(Date lastVisitDate) {
    this.lastVisitDate = lastVisitDate;
  }

  public boolean isOrganizer() {
    return isOrganizer;
  }

  public void setOrganizer(boolean isOrganizer) {
    this.isOrganizer = isOrganizer;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public RealmTypeEntity getRealm() {
    return this.realm;
  }

  public void setRealm(RealmTypeEntity realm) {
    this.realm = realm;
  }
}
