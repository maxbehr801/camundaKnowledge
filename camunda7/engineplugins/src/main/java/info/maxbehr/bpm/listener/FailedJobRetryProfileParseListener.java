package info.maxbehr.bpm.listener;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.impl.bpmn.parser.DefaultFailedJobParseListener;
import org.camunda.bpm.engine.impl.bpmn.parser.FailedJobRetryConfiguration;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.util.ParseUtil;
import org.camunda.bpm.engine.impl.util.xml.Element;

import java.util.List;
import java.util.Map;

public class FailedJobRetryProfileParseListener extends DefaultFailedJobParseListener {

    private Map<String, String> retryProfiles;

    public FailedJobRetryProfileParseListener(Map<String, String> retryProfiles) {
        super();
        this.retryProfiles = retryProfiles;
    }

    @Override
    public void parseServiceTask(Element serviceTaskElement, ScopeImpl scope, ActivityImpl activity) {
        // each service task is asynchronous
        activity.setAsyncBefore(true);
        parseActivity(serviceTaskElement, activity);
    }

    @Override
    protected void setFailedJobRetryTimeCycleValue(Element element, ActivityImpl activity) {
        Element profileElement = getProfileElement(element);
        if (profileElement != null) {
            String retryProfileExpression = null;
            if (retryProfiles != null) {
                String retryProfileName = profileElement.attribute("value");
                retryProfileExpression = retryProfiles.get(retryProfileName);
            } else {
                throw new ProcessEngineException("Something went wrong with the configuration.");
            }

            FailedJobRetryConfiguration configuration = ParseUtil.parseRetryIntervals(retryProfileExpression);
            activity.getProperties().set(FAILED_JOB_CONFIGURATION, configuration);
        } else {
            super.setFailedJobRetryTimeCycleValue(element, activity);
        }
    }

    protected Element getProfileElement(Element element) {
        Element extensionElement = element.element("extensionElements");
        if (extensionElement != null) {
            Element propertiesElement = extensionElement.element("properties");
            if (propertiesElement != null) {
                // get list of <camunda:property ...> elements
                List<Element> propertyList = propertiesElement.elements("property");
                for (Element property : propertyList) {
                    String name = property.attribute("name");
                    if ("retryProfile".equals(name)) {
                        return property;
                    }
                }
            }
        }
        return null;
    }
}
