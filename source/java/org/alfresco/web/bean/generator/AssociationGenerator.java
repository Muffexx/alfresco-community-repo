package org.alfresco.web.bean.generator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.alfresco.web.app.servlet.FacesHelper;
import org.alfresco.web.ui.repo.RepoConstants;
import org.alfresco.web.ui.repo.component.property.PropertySheetItem;
import org.alfresco.web.ui.repo.component.property.UIPropertySheet;

/**
 * Generates a component to manage associations.
 *
 * @author gavinc
 */
public class AssociationGenerator extends BaseComponentGenerator
{
   protected String optionsSize = null;
   
   public String getAvailableOptionsSize()
   {
      return this.optionsSize;
   }

   public void setAvailableOptionsSize(String optionsSize)
   {
      this.optionsSize = optionsSize;
   }

   @SuppressWarnings("unchecked")
   public UIComponent generate(FacesContext context, String id)
   {
      UIComponent component = context.getApplication().
            createComponent(RepoConstants.ALFRESCO_FACES_ASSOC_EDITOR);
      FacesHelper.setupComponentId(context, component, id);
      
      // set the size of the list (if provided)
      if (this.optionsSize != null)
      {
         component.getAttributes().put("availableOptionsSize", this.optionsSize);
      }
      
      return component;
   }
   
   @Override
   protected void setupMandatoryValidation(FacesContext context, UIPropertySheet propertySheet, 
         PropertySheetItem item, UIComponent component, boolean realTimeChecking, String idSuffix)
   {
      // Override the setup of the mandatory validation 
      // so we can send the _current_value id suffix.
      // We also enable real time so the page load
      // check disables the ok button if necessary, as the user
      // adds or removes items from the multi value list the 
      // page will be refreshed and therefore re-check the status.
      // Only so this however id the component is not read-only
      
      if (item.isReadOnly() == false)
      {
         super.setupMandatoryValidation(context, propertySheet, item, 
                  component, true, "_current_value");
      }
   }
}
