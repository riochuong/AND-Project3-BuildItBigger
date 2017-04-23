/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.jokegce.chuondao.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.jokegce.JokeGenerator;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "jokeGeneratorApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.chuondao.example.com",
                ownerName = "backend.myapplication.chuondao.example.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    private static JokeGenerator jokeGenerator = JokeGenerator.getInstance();

    @ApiMethod(name = "getRandomJoke")
    public JokeBean getRandomJoke(){
        JokeBean bean = new JokeBean();
        String joke = jokeGenerator.getRandomJoke();
        bean.setJoke(joke);
        return bean;
    }

}
