/*
 * #%L
 * Alfresco Repository
 * %%
 * Copyright (C) 2005 - 2022 Alfresco Software Limited
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

package org.alfresco.repo.workflow.activiti;

import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.jobexecutor.JobHandler;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.JobEntity;

import org.alfresco.service.cmr.repository.NodeService;

/**
 * An {@link JobHandler} which executes activiti timer-jobs
 * authenticated against Alfresco. It runs the timer execution
 * as the task's assignee (if any) when the timer is applied to a
 * task. If not, system user is used to execute timer.
 * It wraps another JobHandler to which the actual execution is delegated to.
 *
 * @author Frederik Heremans
 * @since 3.4.e
 */
public class AuthenticatedTimerJobHandler implements JobHandler
{
    private JobHandler wrappedHandler;
    private NodeService unprotectedNodeService;

    /**
     * @param jobHandler  the {@link JobHandler} to wrap.
     * @param nodeService the UNPROTECTED {@link NodeService} to use for fetching initiator username
     *                    when only tenant is known. We can't use initiator ScriptNode for this, because this uses the
     *                    protected {@link NodeService} which requires an authenticated user in that tenant (see {@link #getInitiator(ActivitiScriptNode)}).
     */
    public AuthenticatedTimerJobHandler(JobHandler jobHandler, NodeService nodeService)
    {
        if (jobHandler == null)
        {
            throw new IllegalArgumentException("JobHandler to delegate to is required");
        }
        if (nodeService == null)
        {
            throw new IllegalArgumentException("NodeService is required");
        }
        this.unprotectedNodeService = nodeService;
        this.wrappedHandler = jobHandler;
    }

    @Override public void execute(final JobEntity job, final String configuration, final ExecutionEntity execution,
                final CommandContext commandContext)
    {
        wrappedHandler.execute(job, configuration, execution, commandContext);
    }

    @Override public String getType()
    {
        return wrappedHandler.getType();
    }
}
