package info.maxbehr.bpm.listener;

import info.maxbehr.bpm.executionListener.ProgressLoggingExecutionListener;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;

import java.util.List;

import static org.camunda.bpm.engine.delegate.ExecutionListener.EVENTNAME_END;

public class ProgressLoggingSupportParseListener extends AbstractBpmnParseListener {

    @Override
    public void parseServiceTask(Element serviceTaskElement, ScopeImpl scope, ActivityImpl activity) {
        // get the <extensionElements...> element from the service task
        Element extensionElements = serviceTaskElement.element("extensionElements");
        if (extensionElements != null) {
            // get the <camunda:properties...> element from the service task
            Element propertiesElement = extensionElements.element("properties");
            if (propertiesElement != null) {
                // get list of <camunda:property... > elements from the service task
                List<Element> propertyList = propertiesElement.elements("property");
                for (Element property: propertyList) {
                    // get the name and the value of the extension property element
                    String name = property.attribute("name");
                    String value = property.attribute("value");

                    // check if name attribute has the expected value
                    if ("progress".equals(name)) {
                        // add execution listener to the given service task element
                        // to execute it when the end event of the service task is fired
                        ProgressLoggingExecutionListener executionListener = new ProgressLoggingExecutionListener(value);
                        activity.addListener(EVENTNAME_END, executionListener);
                    }
                }
            }
        }
    }
}
