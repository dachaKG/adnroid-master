/*
 * Copyright 2010-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.amazonaws.mobile.api.idzt9jftjm4c;

import java.util.*;

import com.amazonaws.mobile.api.idzt9jftjm4c.model.Empty;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.InspectorModel;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.RunModel;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.TargetDTO;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.TargetModel;
import com.amazonaws.mobile.api.idzt9jftjm4c.model.TemplateModel;


@com.amazonaws.mobileconnectors.apigateway.annotation.Service(endpoint = "https://zt9jftjm4c.execute-api.us-east-2.amazonaws.com/InspectorApi")
public interface InspectorAPIClient {


    /**
     * A generic invoker to invoke any API Gateway endpoint.
     * @param request
     * @return ApiResponse
     */
    com.amazonaws.mobileconnectors.apigateway.ApiResponse execute(com.amazonaws.mobileconnectors.apigateway.ApiRequest request);

    /**
     *
     *
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/inspector", method = "POST")
    Empty inspectorPost();

    /**
     *
     *
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/inspector", method = "OPTIONS")
    Empty inspectorOptions();

    /**
     *
     *
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/run", method = "OPTIONS")
    Empty runOptions();

    /**
     *
     *
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/run/{proxy+}", method = "OPTIONS")
    Empty runProxyOptions();

    /**
     *
     *
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/stream", method = "OPTIONS")
    Empty streamOptions();

    /**
     *
     *
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/stream/{proxy+}", method = "OPTIONS")
    Empty streamProxyOptions();

    /**
     *
     *
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/target", method = "POST")
    InspectorModel targetPost(TargetDTO targetDTO);

    /**
     *
     *
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/target", method = "OPTIONS")
    Empty targetOptions();

    /**
     *
     *
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/targets", method = "POST")
    List<TargetModel> targetsPost(TargetDTO targetDTO);

    /**
     *
     *
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/targets", method = "OPTIONS")
    Empty targetsOptions();

    /**
     *
     *
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/{proxy+}", method = "OPTIONS")
    Empty proxyOptions();


    /**
     *
     *
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/templates", method = "POST")
    List<TemplateModel> templatesPost(TargetDTO targetDTO);

    /**
     *
     *
     * @return Empty
     */
    @com.amazonaws.mobileconnectors.apigateway.annotation.Operation(path = "/runs", method = "POST")
    List<RunModel> runsPost(TargetDTO targetDTO);


}

