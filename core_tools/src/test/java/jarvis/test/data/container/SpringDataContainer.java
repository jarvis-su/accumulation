/*
 * Copyright (c) 2016.
 *  Email : Jarvis_su@126.com
 *
 *
 *
 */

package jarvis.test.data.container;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * SpringDataContainer
 *
 * @author Jarvis Su
 * @date 3/21/2016
 */
public class SpringDataContainer {

    private static ApplicationContext singletonApplicationContext;

    private ApplicationContext applicationContext;

    public SpringDataContainer(String[] configLocations) {
        this(configLocations, true);
    }

    public SpringDataContainer(String[] configLocations, boolean isSingleton) {
        if (isSingleton) {
            if (singletonApplicationContext == null) {
                singletonApplicationContext = new ClassPathXmlApplicationContext(configLocations);
            }
            applicationContext = singletonApplicationContext;
        } else {
            applicationContext = new ClassPathXmlApplicationContext(configLocations);
        }
    }

    public Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

}
