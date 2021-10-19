/*
 * #%L
 * Alfresco Data model classes
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
package org.alfresco.service.cmr.dictionary;

import org.alfresco.api.AlfrescoPublicApi;
import org.alfresco.error.AlfrescoRuntimeException;

/**
 * Base Exception of Data Dictionary Exceptions.
 *
 * @author David Caruana
 */
@AlfrescoPublicApi
public class DictionaryException extends AlfrescoRuntimeException {

  private static final long serialVersionUID = 3257008761007847733L;

  public DictionaryException(String msgId) {
    super(msgId);
  }

  public DictionaryException(String msgId, Throwable cause) {
    super(msgId, cause);
  }

  public DictionaryException(String msgId, Object... args) {
    super(msgId, args);
  }

  public DictionaryException(String msgId, Throwable cause, Object... args) {
    super(msgId, args, cause);
  }

  /**
   * Exception generated by attempting to create a duplicate definition of the
   * data dictionary, such as type, aspect, constraint and etc.
   *
   * @author Jamal Kaabi-Mofrad
   */
  @AlfrescoPublicApi
  public static class DuplicateDefinitionException extends DictionaryException {

    private static final long serialVersionUID = -2623179649246011966L;

    public DuplicateDefinitionException(String msgId) {
      super(msgId);
    }

    public DuplicateDefinitionException(String msgId, Throwable cause) {
      super(msgId, cause);
    }

    public DuplicateDefinitionException(String msgId, Object... args) {
      super(msgId, args);
    }

    public DuplicateDefinitionException(
      String msgId,
      Throwable cause,
      Object... args
    ) {
      super(msgId, cause, args);
    }
  }
}
