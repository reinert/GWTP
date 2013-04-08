/**
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gwtplatform.dispatch.shared.rest;

/**
 * Defines {@link RestAction}s to be used with {@link com.gwtplatform.dispatch.client.rest.RestDispatchAsync}. All
 * interfaces extending {@link RestService} will get their implementation generated, making it possible to inject them
 * with GIN.
 * <p/>
 * Note that all return types must either be {@link com.gwtplatform.dispatch.shared.Action} or {@link RestAction}.
 * If the return type is {@link com.gwtplatform.dispatch.shared.Action}, the implementation will implement
 * {@link RestAction}, making the action compatible with {@link com.gwtplatform.dispatch.client.rest.RestDispatchAsync}.
 */
public interface RestService {
}
