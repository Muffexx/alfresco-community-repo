/*
 * #%L
 * Alfresco Repository
 * %%
 * Copyright (C) 2005 - 2021 Alfresco Software Limited
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
package org.alfresco.rest.api.discovery;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


import org.alfresco.rest.api.impl.directurl.RestApiDirectUrlConfig;
import org.alfresco.service.cmr.repository.ContentService;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/**
 * @author Mikołaj Brzeziński
 */
public class DiscoveryApiWebscriptUnitTest
{
    private static final Boolean ENABLED = Boolean.TRUE;
    private static final Boolean DISABLED = Boolean.FALSE;

    private DiscoveryApiWebscript discoveryApiWebscript = new DiscoveryApiWebscript();
    private RestApiDirectUrlConfig restApiDirectUrlConfig = mock(RestApiDirectUrlConfig.class);
    private ContentService contentService = mock(ContentService.class);

    private void mockedAsserts(boolean restEnabled, boolean systemwideEnabled)
    {
        when(restApiDirectUrlConfig.isEnabled()).thenReturn(restEnabled);
        when(contentService.isContentDirectUrlEnabled()).thenReturn(systemwideEnabled);
        assertEquals(systemwideEnabled, contentService.isContentDirectUrlEnabled());
        assertEquals(restEnabled, restApiDirectUrlConfig.isEnabled());
    }

    @Test
    public void testEnabledConfig_RestEnabledAndSystemwideEnabled()
    {
        mockedAsserts(ENABLED,ENABLED);
        assertTrue("Expected Direct Access URLs to be enabled",discoveryApiWebscript.isContentDirectUrlEnabled());
    }

    @Test
    public void testDisabledConfig_RestEnabledAndSystemwideDisabled()
    {
        mockedAsserts(ENABLED,DISABLED);
        assertFalse("Expected Direct Access URLs to be disabled system-wide", discoveryApiWebscript.isContentDirectUrlEnabled());
    }

    @Test
    public void testDisabledConfig_RestDisabledAndSystemwideDisabled()
    {
        mockedAsserts(DISABLED,DISABLED);
        assertFalse("Expected REST API Direct Access URLs to be disabled and Direct Access URLs to be disabled system-wide ",discoveryApiWebscript.isContentDirectUrlEnabled());
    }

    @Test
    public void testDisabledConfig_RestDisabledAndSystemwideEnabled()
    {
        mockedAsserts(DISABLED,ENABLED);
        assertFalse("Expected REST API direct access URLs to be disabled",discoveryApiWebscript.isContentDirectUrlEnabled());
    }

}