> A template for new C8 connectors.
>
> To use this template update the following resources to match the name of your connector:
>
> * [README](./README.md) (title, description)
> * [Element Template](./element-templates/template-connector.json)
> * [POM](./pom.xml) (artifact name, id, description)
> * [Connector Function](./src/main/java/io/camunda/connector/MyConnectorFunction.java) (rename, implement, update `OutboundConnector` annotation)
> * [Service Provider Interface (SPI)](./src/main/resources/META-INF/services/io.camunda.connector.api.ConnectorFunction#L1) (rename)
>
> ...and delete this hint.
# Outline

> * [template-connector.json](element-templates/template-connector.json) Modeling Building Block
> * [MyConnectorFunction.java](src/main/java/io/camunda/connector/MyConnectorFunction.java) exactly one OutboundConnectorFunction Implementation
> * [MyConnectorRequest](src/main/java/io/camunda/connector/MyConnectorRequest.java) At least one input data object
> * [MyConnectorResponse](src/main/java/io/camunda/connector/MyConnectorResult.java) At least one output data object
> * [SPI Exposition](src/main/resources/META-INF/services/io.camunda.connector.api.outbound.OutboundConnectorFunction) for a detectable Connector function, you are required to expose your function class name int the OutboundConnectorFunction SPI implementation

# Connector Template

Camunda Connector Template

## Build

```bash
mvn clean package
```

## API

### Input

```json
{
  "token": ".....",
  "message": "....."
}
```

### Output

```json
{
  "result": {
    "myProperty": "....."
  }
}
```

## Test locally

Run unit tests

```bash
mvn clean verify
```

Use the [Camunda Job Worker Connector Run-Time](https://github.com/camunda/connector-framework/tree/main/runtime-job-worker) to run your function as a local Job Worker.

## Element Template

The element templates can be found in the [element-templates/template-connector.json](element-templates/template-connector.json) file.
