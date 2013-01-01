package com.lucazamador.drools.monitor.console.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lucazamador.drools.monitor.console.model.MonitoringAgentDataList;
import com.lucazamador.drools.monitor.console.model.kbase.KnowledgeBaseDataList;
import com.lucazamador.drools.monitor.console.model.kbase.metric.KnowledgeBaseMetricData;
import com.lucazamador.drools.monitor.console.model.ksession.KnowledgeSessionDataList;
import com.lucazamador.drools.monitor.console.model.ksession.metric.KnowledgeSessionMetricData;
import com.lucazamador.drools.monitor.console.model.ksession.metric.KnowledgeSessionMetricDataList;
import com.lucazamador.drools.monitor.core.agent.MonitoringAgent;
import com.lucazamador.drools.monitor.model.ksession.KnowledgeSessionMetric;

@Path("/kservice")
@Component
public class KnowledgeRestService {

    @Autowired
    private DroolsMonitoringService droolsMonitoringService;

    @GET
    @Path("/agents")
    @Produces("application/json")
    @BadgerFish
    public Response getMonitoringAgents() {
        MonitoringAgentDataList monitoringAgents = droolsMonitoringService.getMonitoringAgents();
        return Response.status(200).entity(monitoringAgents).build();
    }

    @GET
    @Path("/{monitoringAgentId}/kbases")
    @Produces("application/json")
    @BadgerFish
    public Response getKnowledgeBaseInfo(@PathParam("monitoringAgentId") String monitoringAgentId) {
        Response response = null;
        if ((response = checkMonitoringAgentAvailability(monitoringAgentId)) != null) {
            return response;
        }
        KnowledgeBaseDataList knowledgeBases = droolsMonitoringService.getKnowledgeBaseInformation(monitoringAgentId);
        if (knowledgeBases == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(knowledgeBases).build();
    }

    @GET
    @Path("/{monitoringAgentId}/kbases/previous")
    @Produces("application/json")
    @BadgerFish
    public Response getKnowledgeBasePreviousInfo(@PathParam("monitoringAgentId") String monitoringAgentId) {

        KnowledgeBaseDataList knowledgeBases = droolsMonitoringService.getKnowledgeBaseInformation(monitoringAgentId);
        if (knowledgeBases == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(knowledgeBases).build();
    }

    @GET
    @Path("/{monitoringAgentId}/kbase/{knowledgeBaseId}/metrics")
    @Produces("application/json")
    @BadgerFish
    public Response getKnowledgeBaseMetrics(@PathParam("monitoringAgentId") String monitoringAgentId,
            @PathParam("knowledgeBaseId") String knowledgeBaseId) {
        Response response = null;
        if ((response = checkMonitoringAgentAvailability(monitoringAgentId)) != null) {
            return response;
        }
        KnowledgeBaseMetricData knowledgeBaseMetricData = droolsMonitoringService.getKnowledgeBaseMetric(
                monitoringAgentId, knowledgeBaseId);
        if (knowledgeBaseMetricData == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(knowledgeBaseMetricData).build();
    }

    @GET
    @Path("/{monitoringAgentId}/ksessions")
    @Produces("application/json")
    @BadgerFish
    public Response getKnowledgeSessionInfo(@PathParam("monitoringAgentId") String monitoringAgentId) {
        Response response = null;
        if ((response = checkMonitoringAgentAvailability(monitoringAgentId)) != null) {
            return response;
        }
        KnowledgeSessionDataList knowledgeSessions = droolsMonitoringService
                .getKnowledgeSessionInformation(monitoringAgentId);
        if (knowledgeSessions == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(knowledgeSessions).build();
    }

    @GET
    @Path("/{monitoringAgentId}/ksessions/previous")
    @Produces("application/json")
    @BadgerFish
    public Response getKnowledgeSessionPreviousInfo(@PathParam("monitoringAgentId") String monitoringAgentId) {
        KnowledgeSessionDataList knowledgeSessions = droolsMonitoringService
                .getKnowledgeSessionInformation(monitoringAgentId);
        if (knowledgeSessions == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(knowledgeSessions).build();
    }

    @GET
    @Path("/{monitoringAgentId}/kbase/{knowledgeBaseId}/ksessions")
    @Produces("application/json")
    @BadgerFish
    public Response getKnowledgeSessions(@PathParam("monitoringAgentId") String monitoringAgentId,
            @PathParam("knowledgeBaseId") String knowledgeBaseId) {
        Response response = null;
        if ((response = checkMonitoringAgentAvailability(monitoringAgentId)) != null) {
            return response;
        }
        KnowledgeSessionDataList knowledgeSessions = droolsMonitoringService.getKnowledgeSessions(monitoringAgentId,
                knowledgeBaseId);
        if (knowledgeSessions == null || knowledgeSessions.isEmpty()) {
            String errorMessage = "knowledge sessions information of " + knowledgeBaseId
                    + " is not available at this moment";
            return Response.status(404).entity(errorMessage).build();
        }
        return Response.status(200).entity(knowledgeSessions).build();
    }

    @GET
    @Path("/{monitoringAgentId}/kbase/{knowledgeBaseId}/ksession/{knowledgeSessionId}/metrics/{size}")
    @Produces("application/json")
    @BadgerFish
    public Response getKnowledgeSessionMetrics(@PathParam("monitoringAgentId") String monitoringAgentId,
            @PathParam("knowledgeBaseId") String knowledgeBaseId,
            @PathParam("knowledgeSessionId") int knowledgeSessionId, @PathParam("size") int size) {
        Response response = null;
        if ((response = checkMonitoringAgentAvailability(monitoringAgentId)) != null) {
            return response;
        }
        MonitoringAgent monitoringAgent = droolsMonitoringService.getMonitoringAgent(monitoringAgentId);
        List<KnowledgeSessionMetric> knowledgeSessionMetrics = monitoringAgent.getKnowledgeSessionMetrics(
                knowledgeBaseId, knowledgeSessionId, size);
        if (knowledgeSessionMetrics == null) {
            return Response.status(404).entity("knowledge sessions metrics are not available at this moment").build();
        }
        List<KnowledgeSessionMetricData> metrics = new ArrayList<KnowledgeSessionMetricData>();
        for (KnowledgeSessionMetric knowledgeSessionMetric : knowledgeSessionMetrics) {
            KnowledgeSessionMetricData metric = new KnowledgeSessionMetricData(knowledgeSessionMetric);
            metrics.add(metric);
        }
        return Response.status(200).entity(new KnowledgeSessionMetricDataList(metrics)).build();
    }

    private Response checkMonitoringAgentAvailability(String monitoringAgentId) {
        MonitoringAgent monitoringAgent = droolsMonitoringService.getMonitoringAgent(monitoringAgentId);
        if (monitoringAgent == null) {
            return Response.status(404).entity("whops, can't find a reference to " + monitoringAgentId).build();
        }
        if (!monitoringAgent.isConnected()) {
            return Response.status(404).entity("monitoring agent is disconnected").build();
        }
        return null;
    }

}
