/*
 * Copyright (c) 2016.
 *  Email : Jarvis_su@126.com
 *
 *
 *
 */

package jarvis.test.data.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * DataCreator
 *
 * @author Jarvis Su
 * @date 3/21/2016
 */
public abstract class DataCreator {

    private SpringDataContainer springDataContainer = constructSpringDataContainer();

    protected abstract SpringDataContainer constructSpringDataContainer();

    public Object getBean(String beanName) {
        return springDataContainer.getBean(beanName);
    }

    @SuppressWarnings("rawtypes")
    public Collection getBeans(String beanName) {
        List<Object[]> list = new ArrayList<>();
        for (Object o : (List) getBean(beanName)) {
            list.add(new Object[]{o});
        }
        return list;
    }

}
