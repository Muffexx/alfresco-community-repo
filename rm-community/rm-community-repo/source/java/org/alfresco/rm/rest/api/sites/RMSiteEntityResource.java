/*
 * #%L
 * Alfresco Records Management Module
 * %%
 * Copyright (C) 2005 - 2016 Alfresco Software Limited
 * %%
 * This file is part of the Alfresco software.
 * -
 * If the software was purchased under a paid Alfresco license, the terms of
 * the paid license agreement will prevail.  Otherwise, the software is
 * provided under the following open source license terms:
 * -
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * -
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * -
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

package org.alfresco.rm.rest.api.sites;

import java.util.ArrayList;
import java.util.List;

import org.alfresco.rest.framework.resource.EntityResource;
import org.alfresco.rest.framework.resource.actions.interfaces.EntityResourceAction;
import org.alfresco.rest.framework.resource.parameters.Parameters;
import org.alfresco.rm.rest.api.RMSites;
import org.alfresco.rm.rest.api.model.RMSite;

/**
 * RM Site operations
 *
 * @author Silviu Dinuta
 * @since 2.6
 *
 */
@EntityResource(name = "sites", title = "IG Sites")
public class RMSiteEntityResource implements EntityResourceAction.Delete, EntityResourceAction.Create<RMSite>
{
    private static final String RM_SITE_ID = "rm";
    private RMSites sites;

    public void setSites(RMSites sites) {
        this.sites = sites;
    }

    @Override
    public List<RMSite> create(List<RMSite> entity, Parameters parameters) {
        List<RMSite> result = new ArrayList<>(1);
        result.add(sites.createRMSite(entity.get(0), parameters));
        return result;
    }

    @Override
    public void delete(String id, Parameters parameters) {
        sites.deleteSite(RM_SITE_ID, parameters);
    }
}
