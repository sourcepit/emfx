/*
 * Copyright 2016 Bernd Vogt and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sourcepit.emfx.edit.provider;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ReflectiveItemProvider;

public class XReflectiveItemProvider extends ReflectiveItemProvider {

   public XReflectiveItemProvider(AdapterFactory adapterFactory) {
      super(adapterFactory);
   }

   @Override
   protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
      super.collectNewChildDescriptors(newChildDescriptors, object);
   }

   @Override
   protected void gatherAllMetaData(EObject eObject) {
      final Resource eResource = eObject.eResource();
      if (eResource != null) {
         final ResourceSet resourceSet = eResource.getResourceSet();
         if (resourceSet != null) {
            gatherAllMetaData(resourceSet);
         }
      }
      super.gatherAllMetaData(eObject);
   }

   private void gatherAllMetaData(final ResourceSet resourceSet) {
      // prevent concurrent modification exception
      EcoreUtil.resolveAll(resourceSet);

      for (Resource resource : resourceSet.getResources()) {
         final TreeIterator<EObject> allContents = resource.getAllContents();
         while (allContents.hasNext()) {
            Object object = allContents.next();
            if (object instanceof EModelElement) {
               gatherMetaData((EModelElement) object);
            }
         }
      }
   }
}
