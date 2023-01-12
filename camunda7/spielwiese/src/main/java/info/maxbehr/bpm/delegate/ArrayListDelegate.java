package info.maxbehr.bpm.delegate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.spin.plugin.variable.SpinValues;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArrayListDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        List<String> arrayList = new ArrayList<>();
        Object arrayList1 = execution.getVariableTyped("arrayListJson");
        arrayList.add("eins");
        arrayList.add("zwei");
        arrayList.add("drei");
        ObjectValue objectValue = Variables.objectValue(arrayList).serializationDataFormat(Variables.SerializationDataFormats.JSON).create();
        execution.setVariable("arrayListJson", SpinValues.jsonValue(objectMapper.writeValueAsString(arrayList)).create());
        execution.setVariable("arrayListJava", arrayList);


    }
}
