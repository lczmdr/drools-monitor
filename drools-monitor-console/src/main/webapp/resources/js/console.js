$.console = $.console || {
     domain : 'http://127.0.0.1:8080/',
     appName : 'drools-monitor-console/',
     restService : 'rest/kservice/'
//    domain : 'http://127.0.0.1',
//    appName : '',
//    restService : '/resources/json/'
};

function MonitorinAgent(id, address, port, connected, scanInterval, recoveryInterval) {
    this.id = id;
    this.address = address;
    this.port = port;
    this.connected = connected;
    this.scanInterval = scanInterval;
    this.recoveryInterval = recoveryInterval;
}

$(document).ready(function() {
    renderMonitoringAgents();
});

function renderMonitoringAgents(){
    var monitoringAgentList = $('#monitoringAgentList');

    serviceCall('agents', function(response) {
        var agentsData = $.parseJSON(JSON.stringify(response, undefined, 2));
        var agents = new Array();
        $.each(agentsData, function(array, item) {
                $.each(item, function(monitoringAgentId, monitoringAgentData) {
                        var agent = new MonitorinAgent(monitoringAgentData.id.$, 
                                                       monitoringAgentData.address.$,
                                                       monitoringAgentData.port.$,
                                                       monitoringAgentData.connected.$,
                                                       monitoringAgentData.scanInterval.$,
                                                       monitoringAgentData.recoveryInterval.$);
                        agents.push(agent);
                    }
                );
            }
        );
        $.each(agents, function(index, agent) {
            var menuElement = $(document.createElement('li')).attr('id', 'menuElement');
            if (index==0) menuElement.addClass('active');
            var menuElementLink = $(document.createElement('a')).attr('href', '#').text(agent.id);
            menuElementLink.click({id : agent.id}, function(e) {
                activateCurrentNavListElement(menuElement);
                renderKnowledgeBases(e.data);
            });
            menuElement.append(menuElementLink);
            monitoringAgentList.append(menuElement);
        });
        if (agents.length > 0) {
            renderKnowledgeBases(agents[0]);
            $('#content').show();
            $('#layout').show();
        }
        else {
            $('#layout').empty();
            $('#errorMessage').show();
        }
    });
}

function activateCurrentNavListElement(menuElement) {
    $('li[id=menuElement]').removeClass();
    menuElement.addClass('active');
}

function renderKnowledgeBases(monitoringAgent) {
    var knowledgeBaseNav = $('#knowledgeBaseNav');
    knowledgeBaseNav.empty();
    serviceCall(monitoringAgent.id + '/kbases', function(response) {
        var knowledgeBasesData = $.parseJSON(JSON.stringify(response, undefined, 2));
        var selectedKnowledgeBase = null;
        $.each(knowledgeBasesData, function(array, item) {
                $.each(item, function(index, knowledgeBases) {
                    $.each(knowledgeBases, function(index, knowledgeBaseData) {
                        var navElement = $(document.createElement('li'));
                        if (index==0) {
                            navElement.addClass('active');
                            selectedKnowledgeBase = knowledgeBaseData;
                        }
                        var knowledgeBaseId = knowledgeBaseData.knowledgeBaseId.$;
                        var navElementLink = $(document.createElement('a')).attr('href', '#' + knowledgeBaseId).attr('data-toggle', 'tab').text(knowledgeBaseId);
                        navElement.append(navElementLink);
                        knowledgeBaseNav.append(navElement);
                    });
                });
            }
        );
        if (selectedKnowledgeBase != null) renderKnowledgeSessions(monitoringAgent.id, selectedKnowledgeBase.knowledgeBaseId.$);
    });
}

var selectedKnowledgeSession = null;

function renderKnowledgeSessions(monitoringAgentId, knowledgeBaseId) {
    var knowledgeSessionNav = $('#knowledgeSessionNav');
    knowledgeSessionNav.empty();
    serviceCall(monitoringAgentId + '/kbase/' + knowledgeBaseId + '/ksessions', function(response) {
        var knowledgeSessionData = $.parseJSON(JSON.stringify(response, undefined, 2));
        $.each(knowledgeSessionData, function(array, item) {
            $.each(item, function(index, knowledgeSessions) {
                    if ($.isArray(knowledgeSessions)) {
                        $.each(knowledgeSessions, function(index, knowledgeSession) {
                            createKnowledgeSessionNavs(index, knowledgeSession, knowledgeSessionNav);
                        });
                    }
                    else {
                        createKnowledgeSessionNavs(0, knowledgeSessions, knowledgeSessionNav);
                    }
                });
        });
    });
}

function createKnowledgeSessionNavs(index, knowledgeSession, knowledgeSessionNav) {
    var navElement = $(document.createElement('li'));
    if (index==0) {
        navElement.addClass('active');
        selectedKnowledgeBase = knowledgeSession;
    }
    var navElementLink = $(document.createElement('a')).attr('href', '#').text('ksession-' + knowledgeSession.knowledgeSessionId.$);
    navElement.append(navElementLink);
    knowledgeSessionNav.append(navElement);
};

function serviceCall(serviceName, serviceCallback) {
    var serviceEndpoint = getServicesUrl() + serviceName;
    $.get(serviceEndpoint, serviceCallback, 'json');
}

function getServicesUrl() {
    var servicesBaseDomain = $.console.domain;
    var appName = $.console.appName;
    var restService = $.console.restService;
    return servicesBaseDomain + appName + restService;
}