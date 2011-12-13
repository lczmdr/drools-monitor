JBoss Drools monitoring using JMX
=================================

The goal of this project is to create an embbedable framework to monitor JBoss Drools knowledge bases and sessions that can be integrated into a webapp or a standalone application.
In this repository you can also find a minimal Eclipse RCP application using the drools-monitoring project.

Current features
----------------

* XML and API configuration.
* Automatic knowledge base and sessions discovery.
* Knowledge Base and sessions metrics scanner with a configurable scan time.
* Connection recovery in case of connection lost or JVM restart.
* Custom metrics, recovery and discoverer metrics.

What doesn't supports yet (but it's supposed to do soon):
* Metrics persistence (Currently working on it).
* JMX connection timeout (JMX specification doesn't supports timeout so it needs to be implemented in another way. There is current progress on it).

How to use it
-------------

Just writing the next lines of code:

	MonitoringConfigurationReader configurationReader = DroolsMonitoringFactory.newMonitoringConfigurationReader("/configuration.xml");
    MonitoringConfiguration configuration = configurationReader.read();
    DroolsMonitoring monitor = DroolsMonitoringFactory.newDroolsMonitoring(configuration);
    monitor.start();

But to configure it you will need to create a configuration file with the next format:

	<?xml version="1.0" encoding="UTF-8"?>
	<configuration>
  	  <connections>
        <agent id="jvm1" address="localhost" port="9003" scanInterval="3000" recoveryInterval="10000" />
        <agent id="jvm2" address="192.168.0.11" port="9004" scanInterval="1000" recoveryInterval="5000" />
	  </connections>
	</configuration>

Contributing
------------
Want to contribute? Great, contributions are always welcome.