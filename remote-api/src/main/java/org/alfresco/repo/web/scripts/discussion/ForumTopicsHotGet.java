/*
 * #%L
 * Alfresco Remote API
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
package org.alfresco.repo.web.scripts.discussion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.alfresco.query.PagingRequest;
import org.alfresco.query.PagingResults;
import org.alfresco.service.cmr.discussion.PostInfo;
import org.alfresco.service.cmr.discussion.TopicInfo;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.site.SiteInfo;
import org.alfresco.util.Pair;
import org.json.simple.JSONObject;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

/**
 * This class is the controller for the discussions topics fetching forum-posts-hot.get webscript.
 *
 * @author Nick Burch
 * @since 4.0
 */
public class ForumTopicsHotGet extends AbstractDiscussionWebScript {

  protected static final int RECENT_POSTS_PERIOD_DAYS = 30;
  protected static final long ONE_DAY_MS = 24 * 60 * 60 * 1000;

  @Override
  protected Map<String, Object> executeImpl(
    SiteInfo site,
    NodeRef nodeRef,
    TopicInfo topic,
    PostInfo post,
    WebScriptRequest req,
    JSONObject json,
    Status status,
    Cache cache
  ) {
    // They shouldn't be trying to list of an existing Post or Topic
    if (topic != null || post != null) {
      String error = "Can't list Topics inside an existing Topic or Post";
      throw new WebScriptException(Status.STATUS_BAD_REQUEST, error);
    }

    // Grab the date range to search over
    String numDaysS = req.getParameter("numdays");
    int numDays = RECENT_POSTS_PERIOD_DAYS;
    if (numDaysS != null) {
      numDays = Integer.parseInt(numDaysS);
    }

    Date now = new Date();
    Date since = new Date(now.getTime() - numDays * ONE_DAY_MS);

    // Get the topics with recent replies
    PagingResults<Pair<TopicInfo, Integer>> topicsAndCounts = null;
    PagingRequest paging = buildPagingRequest(req);
    if (site != null) {
      topicsAndCounts =
        discussionService.listHotTopics(site.getShortName(), since, paging);
    } else {
      topicsAndCounts = discussionService.listHotTopics(nodeRef, since, paging);
    }

    // For this, we actually only need the topics, not their counts too
    List<TopicInfo> topics = new ArrayList<TopicInfo>();
    for (Pair<TopicInfo, Integer> tc : topicsAndCounts.getPage()) {
      topics.add(tc.getFirst());
    }

    // If they did a site based search, and the component hasn't
    //  been created yet, use the site for the permissions checking
    if (site != null && nodeRef == null) {
      nodeRef = site.getNodeRef();
    }

    // Build the common model parts
    Map<String, Object> model = buildCommonModel(site, topic, post, req);
    model.put("forum", nodeRef);

    // Have the topics rendered
    model.put(
      "data",
      renderTopics(topics, topicsAndCounts.getTotalResultCount(), paging, site)
    );

    // All done
    return model;
  }
}
