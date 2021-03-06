package com.github.h2020_5gtango.vnv.lcm.model

import groovy.transform.EqualsAndHashCode
import io.swagger.annotations.ApiModelProperty

import javax.validation.constraints.NotNull

@EqualsAndHashCode
class NetworkService {

    @ApiModelProperty(required = true)
    @NotNull
    String networkServiceId

    String name
    String vendor
    String version

    String status
}
