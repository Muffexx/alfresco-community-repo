/*
 * #%L
 * Alfresco Repository
 * %%
 * Copyright (C) 2005 - 2016 Alfresco Software Limited
 * %%
 * This file is part of the Alfresco software.
 * If the software was purchased under a paid Alfresco license, the terms of
 * the paid license agreement will prevail.  Otherwise, the software is
 * provided under the following open source license terms:
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
package org.alfresco.repo.domain.node;

/**
 * Carry bulk acl update info.
 *
 * @author andyh
 * @since 3.4
 */
public class PrimaryChildrenAclUpdateEntity {
    Long txnId;
    Long primaryParentNodeId;
    Long optionalOldSharedAclIdInAdditionToNull;
    Long newSharedAclId;

    public PrimaryChildrenAclUpdateEntity() {}

    public Long getTxnId() {
        return txnId;
    }

    public void setTxnId(Long txnId) {
        this.txnId = txnId;
    }

    public Long getPrimaryParentNodeId() {
        return primaryParentNodeId;
    }

    public void setPrimaryParentNodeId(Long primaryParentNodeId) {
        this.primaryParentNodeId = primaryParentNodeId;
    }

    public Long getOptionalOldSharedAclIdInAdditionToNull() {
        return optionalOldSharedAclIdInAdditionToNull;
    }

    public void setOptionalOldSharedAclIdInAdditionToNull(
            Long optionalOldSharedAclIdInAdditionToNull) {
        this.optionalOldSharedAclIdInAdditionToNull = optionalOldSharedAclIdInAdditionToNull;
    }

    public Long getNewSharedAclId() {
        return newSharedAclId;
    }

    public void setNewSharedAclId(Long newSharedAclId) {
        this.newSharedAclId = newSharedAclId;
    }

    public boolean getIsPrimary() {
        return true;
    }
}
