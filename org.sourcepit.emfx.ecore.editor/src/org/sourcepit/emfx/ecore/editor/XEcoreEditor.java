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

package org.sourcepit.emfx.ecore.editor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.sourcepit.emfx.ecore.edit.XEcoreItemProviderAdapterFactory;
import org.sourcepit.emfx.edit.provider.XReflectiveItemProviderAdapterFactory;

public class XEcoreEditor extends EcoreEditor {

   @Override
   protected void initializeEditingDomain() {
      super.initializeEditingDomain();

      final Class<? extends ComposedAdapterFactory> clazz = adapterFactory.getClass();

      try {
         final Field field = clazz.getDeclaredField("adapterFactories");
         field.setAccessible(true);
         @SuppressWarnings("unchecked")
         final List<AdapterFactory> factories = new ArrayList<>((List<AdapterFactory>) field.get(adapterFactory));
         for (AdapterFactory factory : factories) {
            adapterFactory.removeAdapterFactory(factory);
         }
      }
      catch (NoSuchFieldException e) {
         // TODO: git_user_name Auto-generated catch block
         e.printStackTrace();
      }
      catch (SecurityException e) {
         // TODO: git_user_name Auto-generated catch block
         e.printStackTrace();
      }
      catch (IllegalArgumentException e) {
         // TODO: git_user_name Auto-generated catch block
         e.printStackTrace();
      }
      catch (IllegalAccessException e) {
         // TODO: git_user_name Auto-generated catch block
         e.printStackTrace();
      }

      adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
      adapterFactory.addAdapterFactory(new XEcoreItemProviderAdapterFactory());
      adapterFactory.addAdapterFactory(new XReflectiveItemProviderAdapterFactory());

   }

}
