/*
 * Copyright (C) 2005-2007 Alfresco Software Limited.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.

 * As a special exception to the terms and conditions of version 2.0 of 
 * the GPL, you may redistribute this Program in connection with Free/Libre 
 * and Open Source Software ("FLOSS") applications as described in Alfresco's 
 * FLOSS exception.  You should have recieved a copy of the text describing 
 * the FLOSS exception, and it is also available here: 
 * http://www.alfresco.com/legal/licensing"
 */
package org.alfresco.repo.web.scripts.dictionary;

import org.alfresco.web.scripts.DeclarativeWebScript;
import org.alfresco.web.scripts.Status;
import org.alfresco.web.scripts.WebScriptException;
import org.alfresco.web.scripts.WebScriptRequest;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.cmr.dictionary.AssociationDefinition;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.dictionary.ClassDefinition;
import org.alfresco.service.cmr.dictionary.PropertyDefinition;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Webscript to get the Classdefinitions for a classname eg. =>cm_author
 * @author Saravanan Sellathurai
 */

public class GetClassDetails extends DeclarativeWebScript
{
	
	private DictionaryService dictionaryservice;
	private DictionaryHelper dictionaryhelper;
	
	private static final String MODEL_PROP_KEY_CLASS_DETAILS = "classdefs";
	private static final String MODEL_PROP_KEY_PROPERTY_DETAILS = "propertydefs";
	private static final String MODEL_PROP_KEY_ASSOCIATION_DETAILS = "assocdefs";
	private static final String DICTIONARY_CLASS_NAME = "className";
    
	/**
     * Set the dictionaryService property.
     * 
     * @param dictionaryService The dictionary service instance to set
     */
    public void setDictionaryService(DictionaryService dictionaryService)
    {
        this.dictionaryservice = dictionaryService; 
    }
    
    /**
     * Set the dictionaryhelper class
     * 
     * @param dictionaryHelper The dictionary helper instance to set
     */
    public void setDictionaryHelper(DictionaryHelper dictionaryhelper)
    {
        this.dictionaryhelper = dictionaryhelper; 
    }
    
    /**
     * @Override  method from DeclarativeWebScript 
     */
    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status)
    {
        String classname = req.getServiceMatch().getTemplateVars().get(DICTIONARY_CLASS_NAME);
        
        Map<String, Object> model = new HashMap<String, Object>();
        QName qname = null;
        Map<QName, ClassDefinition> classdef = new HashMap<QName, ClassDefinition>();
        Map<QName, Collection<PropertyDefinition>> propdef = new HashMap<QName, Collection<PropertyDefinition>>();
        Map<QName, Collection<AssociationDefinition>> assocdef = new HashMap<QName, Collection<AssociationDefinition>>();
        boolean classnameGiven = (classname != null) && (classname.length() > 0);
        boolean hasData = false;
        
        if(classnameGiven && this.dictionaryhelper.isValidClassname(classname))
        {
           	qname = QName.createQName(this.dictionaryhelper.getFullNamespaceURI(classname));
           	hasData = true;
           	classdef.put(qname, this.dictionaryservice.getClass(qname));
        	propdef.put(qname, this.dictionaryservice.getClass(qname).getProperties().values());
    		assocdef.put(qname, this.dictionaryservice.getClass(qname).getAssociations().values());
        }
        
        if(hasData)
        {
        	model.put(MODEL_PROP_KEY_CLASS_DETAILS, classdef.values());
        	model.put(MODEL_PROP_KEY_PROPERTY_DETAILS, propdef.values());
        	model.put(MODEL_PROP_KEY_ASSOCIATION_DETAILS, assocdef.values());
        	return model;
        }
        else
        {
            throw new WebScriptException(Status.STATUS_NOT_FOUND, "The exact parameter has not been provided in the URL");
        } 
    }
    
}