package com.ldz.converter.container;

import com.ldz.converter.container.inter.IConverter;
import com.ldz.converter.container.annot.BeanConverter;
import com.ldz.converter.container.config.BasePackageNameConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by ldalzotto on 17/04/2017.
 */
@Component
public class ConverterContainer {

    public static final Logger LOGGER = LoggerFactory.getLogger(ConverterContainer.class.getName());

    @Autowired BasePackageNameConfiguration basePackageNameConfiguration;

    @Autowired
    ApplicationContext applicationContext;

    Map<String, Map<Method, Class>> methodToInvokeFromTag = new HashMap<>();

    @PostConstruct
    public void containerInitialisation() {
        ClassPathScanningCandidateComponentProvider classPathScanningCandidateComponentProvider =
                new ClassPathScanningCandidateComponentProvider(false);
        classPathScanningCandidateComponentProvider
                .addIncludeFilter(new AnnotationTypeFilter(BeanConverter.class));

        Set<BeanDefinition> beanDefinitions =
                classPathScanningCandidateComponentProvider
                        .findCandidateComponents(basePackageNameConfiguration.getBasePackage());

        beanDefinitions.forEach(beanDefinition -> {
            try {
                Class<?> aClass = Class.forName(beanDefinition.getBeanClassName());
                BeanConverter beanConverter = aClass.getAnnotation(BeanConverter.class);

                //assert interfaces
                List<String> interfaceNames = Arrays.asList(
                        ((ScannedGenericBeanDefinition) beanDefinition).getMetadata().getInterfaceNames()
                );
                if (!interfaceNames.contains(IConverter.class.getName())) {
                    throw new IllegalAccessException(
                            "The converter class doesn't implement " + IConverter.class.getSimpleName());
                }

                String initClassName = beanConverter.initialBeanClass().getSimpleName();
                String targetClassName = beanConverter.targetBeanClass().getSimpleName();

                StringBuilder tagToCall = new StringBuilder();
                tagToCall.append(initClassName).append("to").append(targetClassName);

                Method methodToCall = aClass.getMethod("apply", beanConverter.initialBeanClass());

                //map of method associated to class
                Map<Method, Class> methodClassMap = new HashMap<>();
                methodClassMap.put(methodToCall, aClass);

                methodToInvokeFromTag.put(tagToCall.toString(), methodClassMap);

                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("Converter " + tagToCall.toString() + " placed into generic container.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public <I, T> T convert(I source, Class<T> tagetClass) {
        try {
            //construction du tag
            StringBuilder tagToCall = new StringBuilder();
            tagToCall.append(source.getClass().getSimpleName()).append("to").append(tagetClass.getSimpleName());

            //calling method
            Map.Entry<Method, Class> methodClassEntry =
                    methodToInvokeFromTag.get(tagToCall.toString()).entrySet().iterator().next();

            //retrieve bean from spring context
            Object converterBean = this.applicationContext.getBean(methodClassEntry.getValue());

            Method method = methodClassEntry.getKey();
            Object returnOfMethod = method.invoke(converterBean, source);

            //casting
            T objectCasted = (T) returnOfMethod;
            return objectCasted;
        } catch (Exception e) {
            LOGGER.error("Could not convert the class " + source.getClass().getSimpleName() + " to " +
                    tagetClass.getSimpleName(), e);
            return null;
        }
    }

}
