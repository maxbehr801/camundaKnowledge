# Process Engine Plugins
Zum Erstellen eines Plugins braucht man:
1. ein AbstractProcessEnginePlugin
2. z.B. einen AbstractBpmnParseListener (hier wird das BPMN-File manipuliert)

Damit das Plugin im Spring Boot Starter aktiviert wird ist es lediglich notwendig die Implementierung des
ProcessEnginePlugins als Bean zur Verfügung zu stellen.