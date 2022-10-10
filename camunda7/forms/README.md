# Forms Deployment in Camunda 7.16
In Version 7.16 werden die Camunda Forms mit Spring Boot nicht mehr automatisch deployed.
Kann über die camunda properties umgangen werden:

```yaml
camunda:
  bpm:
    deployment-resource-pattern: classpath*:**/*.bpmn, classpath*:**/*.form
```
